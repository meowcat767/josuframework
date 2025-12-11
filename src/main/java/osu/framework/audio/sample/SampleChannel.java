package osu.framework.audio.sample;

import osu.framework.audio.AdjustableAudioComponent;

/**
 * Base implementation of a sample playback channel.
 */
public abstract class SampleChannel extends AdjustableAudioComponent implements ISampleChannel {
    protected boolean playing = false;
    protected boolean played = false;

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public boolean hasPlayed() {
        return played;
    }

    @Override
    public void play() {
        played = true;
        playing = true;
    }

    @Override
    public void stop() {
        playing = false;
    }
}
