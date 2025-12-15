package osu.framework.configuration;

import osu.framework.bindables.Bindable;
import osu.framework.bindables.IBindable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Configuration manager that persists to INI files.
 * 
 * @param <TLookup> The enum type used for configuration keys.
 */
public abstract class IniConfigManager<TLookup extends Enum<TLookup>> extends ConfigManager<TLookup> {
    /**
     * The backing file used to store the config. Null means no persistent storage.
     */
    protected String getFilename() {
        return "game.ini";
    }

    private final Path storagePath;

    /**
     * Creates a new INI-based config manager.
     * 
     * @param storagePath      The directory where the config file will be stored.
     * @param defaultOverrides Optional default value overrides.
     */
    public IniConfigManager(Path storagePath, Map<TLookup, Object> defaultOverrides) {
        super(defaultOverrides);
        this.storagePath = storagePath;

        initialiseDefaults();
        load();
    }

    /**
     * Creates a new INI-based config manager without default overrides.
     */
    public IniConfigManager(Path storagePath) {
        this(storagePath, null);
    }

    /**
     * Creates a new INI-based config manager with a string path.
     */
    public IniConfigManager(String storagePath) {
        this(Paths.get(storagePath));
    }

    @Override
    protected void performLoad() {
        String filename = getFilename();
        if (filename == null || filename.isEmpty()) {
            return;
        }

        Path configFile = storagePath.resolve(filename);
        if (!Files.exists(configFile)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(configFile)) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                int equalsIndex = line.indexOf('=');
                if (equalsIndex < 0) {
                    continue;
                }

                String key = line.substring(0, equalsIndex).trim();
                String value = line.substring(equalsIndex + 1).trim();

                // Try to parse the key as an enum
                try {
                    TLookup lookup = parseEnum(key);
                    if (lookup == null) {
                        continue;
                    }

                    IBindable<?> bindable = configStore.get(lookup);
                    if (bindable != null) {
                        parseAndSetValue(bindable, value);
                    } else if (addMissingEntries) {
                        setDefault(lookup, value);
                    }
                } catch (Exception e) {
                    System.err.println("Unable to parse config key " + key + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
        }
    }

    @Override
    protected boolean performSave() {
        String filename = getFilename();
        if (filename == null || filename.isEmpty()) {
            return false;
        }

        try {
            // Ensure directory exists
            Files.createDirectories(storagePath);

            Path configFile = storagePath.resolve(filename);

            try (BufferedWriter writer = Files.newBufferedWriter(configFile)) {
                for (Map.Entry<TLookup, IBindable<?>> entry : configStore.entrySet()) {
                    String key = entry.getKey().name();
                    String value = entry.getValue().toString();

                    // Remove newlines and carriage returns
                    value = value.replace("\n", "").replace("\r", "");

                    writer.write(key + " = " + value);
                    writer.newLine();
                }
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error saving config file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Parses a string as an enum value.
     */
    @SuppressWarnings("unchecked")
    private TLookup parseEnum(String key) {
        try {
            // Get the enum class from a sample value
            if (configStore.isEmpty()) {
                return null;
            }

            TLookup sample = configStore.keySet().iterator().next();
            Class<TLookup> enumClass = (Class<TLookup>) sample.getClass();

            return Enum.valueOf(enumClass, key);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Parses a value and sets it on a bindable.
     */
    @SuppressWarnings("unchecked")
    private void parseAndSetValue(IBindable<?> bindable, String value) {
        if (!(bindable instanceof Bindable)) {
            return;
        }

        Bindable<?> b = (Bindable<?>) bindable;

        // Try to parse based on current value type
        Object currentValue = b.getValue();
        if (currentValue instanceof Integer) {
            ((Bindable<Integer>) b).setValue(Integer.parseInt(value));
        } else if (currentValue instanceof Long) {
            ((Bindable<Long>) b).setValue(Long.parseLong(value));
        } else if (currentValue instanceof Double) {
            ((Bindable<Double>) b).setValue(Double.parseDouble(value));
        } else if (currentValue instanceof Float) {
            ((Bindable<Float>) b).setValue(Float.parseFloat(value));
        } else if (currentValue instanceof Boolean) {
            ((Bindable<Boolean>) b).setValue(Boolean.parseBoolean(value));
        } else if (currentValue instanceof String) {
            ((Bindable<String>) b).setValue(value);
        } else if (currentValue instanceof Enum) {
            parseEnumValue((Bindable<Enum<?>>) b, value);
        } else {
            // Default: set as string
            ((Bindable<Object>) b).setValue(value);
        }
    }

    /**
     * Parses an enum value.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void parseEnumValue(Bindable<Enum<?>> bindable, String value) {
        Enum<?> currentValue = bindable.getValue();
        if (currentValue != null) {
            Class enumClass = currentValue.getClass();
            Enum<?> parsedValue = Enum.valueOf(enumClass, value);
            bindable.setValue(parsedValue);
        }
    }
}
