package osu.framework.audio;

/**
 * A resource store which supports audio adjustments.
 */
public interface IAdjustableResourceStore {
    /**
     * Gets the audio adjustments for this resource store.
     */
    IAdjustableAudioComponent getAdjustments();
}
