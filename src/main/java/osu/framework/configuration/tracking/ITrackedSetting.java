package osu.framework.configuration.tracking;

/**
 * Interface for tracked settings.
 */
public interface ITrackedSetting {
    /**
     * Gets the current description of this setting.
     * 
     * @return The setting description.
     */
    SettingDescription getDescription();
}
