package osu.framework.audio.track;

import osu.framework.audio.AdjustableAudioComponent;

/**
 * Base implementation of an audio track.
 */
public abstract class Track extends AdjustableAudioComponent implements ITrack {
    protected boolean looping = false;
    protected double restartPoint = 0;
    protected double length = 0;
    protected boolean running = false;

    @Override
    public boolean isLooping() {
        return looping;
    }

    @Override
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    @Override
    public double getRestartPoint() {
        return restartPoint;
    }

    @Override
    public void setRestartPoint(double restartPoint) {
        this.restartPoint = restartPoint;
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public boolean isDummyDevice() {
        return false;
    }

    @Override
    public Integer getBitrate() {
        return null;
    }

    @Override
    public boolean isReversed() {
        return false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void restart() {
        stop();
        // Seek to restart point (subclass responsibility)
        start();
    }

    @Override
    public ChannelAmplitudes getCurrentAmplitudes() {
        return new ChannelAmplitudes();
    }
}
