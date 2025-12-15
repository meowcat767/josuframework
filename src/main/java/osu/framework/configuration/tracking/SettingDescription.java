package osu.framework.configuration.tracking;

/**
 * Describes a setting for display/notification purposes.
 */
public class SettingDescription {
    private final String name;
    private final String value;
    private final String shortcut;

    /**
     * Creates a new setting description.
     * 
     * @param name     The name of the setting.
     * @param value    The current value as a string.
     * @param shortcut Optional keyboard shortcut.
     */
    public SettingDescription(String name, String value, String shortcut) {
        this.name = name;
        this.value = value;
        this.shortcut = shortcut;
    }

    /**
     * Creates a new setting description without a shortcut.
     */
    public SettingDescription(String name, String value) {
        this(name, value, null);
    }

    /**
     * Gets the setting name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the setting value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the keyboard shortcut, if any.
     */
    public String getShortcut() {
        return shortcut;
    }

    @Override
    public String toString() {
        if (shortcut != null) {
            return name + ": " + value + " (" + shortcut + ")";
        }
        return name + ": " + value;
    }
}
