package osu.framework.configuration.tracking;

import osu.framework.configuration.ConfigManager;

/**
 * Interface for configuration managers that support change tracking.
 */
public interface ITrackableConfigManager {
    /**
     * Creates tracked settings for this config manager.
     * 
     * @return A TrackedSettings instance, or null if tracking is not supported.
     */
    TrackedSettings createTrackedSettings();

    /**
     * Loads tracked settings into the provided container.
     * 
     * @param settings The settings container to load into.
     */
    void loadInto(TrackedSettings settings);
}
