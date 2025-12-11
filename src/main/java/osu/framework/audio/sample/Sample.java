package osu.framework.audio.sample;

import osu.framework.audio.AdjustableAudioComponent;
import osu.framework.bindables.Bindable;
import osu.framework.bindables.BindableInt;

/**
 * Base implementation of an audio sample.
 */
public abstract class Sample extends AdjustableAudioComponent implements ISample {
    protected final String name;
    protected double length = 0;
    protected final Bindable<Integer> playbackConcurrency = new BindableInt(2);

    public Sample(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public Bindable<Integer> getPlaybackConcurrency() {
        return playbackConcurrency;
    }

    @Override
    public abstract SampleChannel play();

    @Override
    public abstract SampleChannel getChannel();
}
