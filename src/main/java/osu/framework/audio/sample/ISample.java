package osu.framework.audio.sample;

import osu.framework.audio.IAdjustableAudioComponent;
import osu.framework.bindables.Bindable;

/**
 * An interface for an audio sample.
 */
public interface ISample extends IAdjustableAudioComponent {
    /**
     * A name identifying this sample internally.
     */
    String getName();

    /**
     * The length in milliseconds of this sample.
     * The value may not be available yet if {@link #isLoaded()} is false.
     */
    double getLength();

    /**
     * Whether this sample is fully loaded.
     */
    boolean isLoaded();

    /**
     * The number of times this sample (as identified by name) can be played back
     * concurrently.
     * This affects all {@link ISample} instances identified by the same sample
     * name.
     */
    Bindable<Integer> getPlaybackConcurrency();

    /**
     * Creates a new unique playback channel for this sample and immediately plays
     * it.
     * Multiple channels can be played simultaneously, but can only be heard up to
     * {@link #getPlaybackConcurrency()} times.
     * 
     * @return The unique {@link SampleChannel} for the playback.
     */
    SampleChannel play();

    /**
     * Retrieves a unique playback channel for this sample.
     * Multiple channels can be retrieved and played simultaneously, but can only be
     * heard up to {@link #getPlaybackConcurrency()} times.
     * 
     * @return The unique {@link SampleChannel} for the playback.
     */
    SampleChannel getChannel();
}
