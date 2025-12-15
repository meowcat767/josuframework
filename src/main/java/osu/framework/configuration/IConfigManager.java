package osu.framework.configuration;

/**
 * Interface for configuration managers.
 */
public interface IConfigManager {
    /**
     * Loads configuration from storage.
     */
    void load();

    /**
     * Saves configuration to storage.
     * 
     * @return True if save was successful.
     */
    boolean save();
}
