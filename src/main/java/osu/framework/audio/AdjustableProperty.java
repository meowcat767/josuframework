package osu.framework.audio;

/**
 * Properties of an audio component which can be adjusted.
 */
public enum AdjustableProperty {
    /**
     * The volume of the audio component (0..1).
     */
    Volume,

    /**
     * The playback balance (-1..1 where 0 is centered).
     */
    Balance,

    /**
     * Rate at which the component is played back (affects pitch).
     * 1 is 100% playback speed, or default frequency.
     */
    Frequency,

    /**
     * Rate at which the component is played back (does not affect pitch).
     * 1 is 100% playback speed.
     */
    Tempo
}
