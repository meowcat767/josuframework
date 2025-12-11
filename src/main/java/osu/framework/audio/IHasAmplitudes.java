package osu.framework.audio;

import osu.framework.audio.track.ChannelAmplitudes;

/**
 * An audio component which provides amplitude data.
 */
public interface IHasAmplitudes {
    /**
     * Gets the current amplitude data for this audio component.
     * 
     * @return The current channel amplitudes.
     */
    ChannelAmplitudes getCurrentAmplitudes();
}
