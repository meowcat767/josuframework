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
}
