package osu.framework.audio.sample;

import osu.framework.audio.IAdjustableAudioComponent;

/**
 * An interface for a sample playback channel.
 */
public interface ISampleChannel extends IAdjustableAudioComponent {
    /**
     * Whether this channel is currently playing.
     */
    boolean isPlaying();

    /**
     * Whether this channel has been played at least once.
     */
    boolean hasPlayed();

    /**
     * Starts playback of this channel.
     */
    void play();

    /**
     * Stops playback of this channel.
     */
    void stop();
}
