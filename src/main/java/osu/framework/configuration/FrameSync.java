package osu.framework.configuration;

/**
 * Frame synchronization modes.
 */
public enum FrameSync {
    /**
     * Synchronize with vertical blank (VSync).
     */
    VSync,

    /**
     * Limit to 2x refresh rate.
     */
    Limit2x,

    /**
     * Limit to 4x refresh rate.
     */
    Limit4x,

    /**
     * Limit to 8x refresh rate.
     */
    Limit8x,

    /**
     * No frame rate limiting.
     */
    Unlimited
}
