package osu.framework.audio.track;

import osu.framework.audio.IAdjustableAudioComponent;
import osu.framework.audio.IHasAmplitudes;

/**
 * An audio track.
 */
public interface ITrack extends IAdjustableAudioComponent, IHasAmplitudes {
    /**
     * States if this track should repeat.
     */
    boolean isLooping();

    void setLooping(boolean looping);

    /**
     * Is this track capable of producing audio?
     */
    boolean isDummyDevice();

    /**
     * Point in time in milliseconds to restart the track to on loop or restart.
     */
    double getRestartPoint();

    void setRestartPoint(double restartPoint);

    /**
     * Length of the track in milliseconds.
     */
    double getLength();

    void setLength(double length);

    /**
     * The bitrate of this track.
     */
    Integer getBitrate();

    /**
     * Whether this track is reversed.
     */
    boolean isReversed();

    /**
     * Whether this track has finished playing back.
     */
    boolean hasCompleted();

    /**
     * Restarts this track from the restart point while retaining adjustments.
     */
    void restart();

    /**
     * Starts playback.
     */
    void start();

    /**
     * Stops playback.
     */
    void stop();

    /**
     * Whether the track is currently running.
     */
    boolean isRunning();
}
