package osu.framework.configuration.tracking;

import java.util.function.Function;

/**
 * A tracked setting that monitors changes and generates descriptions.
 * 
 * @param <TValue> The type of value being tracked.
 */
public class TrackedSetting<TValue> implements ITrackedSetting {
    private final Enum<?> setting;
    private final Function<TValue, SettingDescription> generateDescription;
    private TValue currentValue;

    /**
     * Creates a new tracked setting.
     * 
     * @param setting             The config setting to track.
     * @param generateDescription Function to generate description from value.
     */
    public TrackedSetting(Enum<?> setting, Function<TValue, SettingDescription> generateDescription) {
        this.setting = setting;
        this.generateDescription = generateDescription;
    }

    /**
     * Gets the setting key.
     */
    public Enum<?> getSetting() {
        return setting;
    }

    /**
     * Updates the current value.
     * 
     * @param value The new value.
     */
    public void updateValue(TValue value) {
        this.currentValue = value;
    }

    @Override
    public SettingDescription getDescription() {
        return generateDescription.apply(currentValue);
    }
}
