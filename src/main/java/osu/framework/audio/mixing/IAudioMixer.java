package osu.framework.audio.mixing;

import osu.framework.audio.IAdjustableAudioComponent;

/**
 * An interface for audio mixers.
 */
public interface IAudioMixer extends IAdjustableAudioComponent {
    /**
     * Adds a channel to this mixer.
     * 
     * @param channel The channel to add.
     */
    void add(IAudioChannel channel);

    /**
     * Removes a channel from this mixer.
     * 
     * @param channel The channel to remove.
     */
    void remove(IAudioChannel channel);
}
