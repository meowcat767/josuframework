package osu.framework.audio.mixing;

import osu.framework.audio.IAdjustableAudioComponent;

/**
 * An interface for audio channels in a mixer.
 */
public interface IAudioChannel extends IAdjustableAudioComponent {

    /**
     * Gets the mixer this channel belongs to.
     */
    IAudioMixer getMixer();

    /**
     * Sets the mixer this channel belongs to.
     */
    void setMixer(IAudioMixer mixer);

    /**
     * Reads audio samples into the provided buffer.
     *
     * @param buffer The destination buffer.
     * @param offset The buffer offset.
     * @param length The number of samples to read.
     * @return The number of samples actually read.
     */
    int read(float[] buffer, int offset, int length);
}