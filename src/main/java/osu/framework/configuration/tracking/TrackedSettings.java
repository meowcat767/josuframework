package osu.framework.configuration.tracking;

import osu.framework.configuration.ConfigManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of tracked settings.
 */
public class TrackedSettings {
    private final List<ITrackedSetting> settings = new ArrayList<>();

    /**
     * Adds a tracked setting.
     * 
     * @param setting The setting to track.
     */
    public void addSetting(ITrackedSetting setting) {
        settings.add(setting);
    }

    /**
     * Gets all tracked settings.
     */
    public List<ITrackedSetting> getSettings() {
        return new ArrayList<>(settings);
    }

    /**
     * Loads settings from a config manager.
     * 
     * @param configManager The config manager to load from.
     */
    public <TLookup extends Enum<TLookup>> void loadFrom(ConfigManager<TLookup> configManager) {
        for (ITrackedSetting setting : settings) {
            if (setting instanceof TrackedSetting) {
                TrackedSetting<?> tracked = (TrackedSetting<?>) setting;
                loadTrackedSetting(tracked, configManager);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <TLookup extends Enum<TLookup>, TValue> void loadTrackedSetting(
            TrackedSetting<TValue> tracked, ConfigManager<TLookup> configManager) {
        TLookup lookup = (TLookup) tracked.getSetting();
        TValue value = configManager.get(lookup);
        tracked.updateValue(value);
    }
}
