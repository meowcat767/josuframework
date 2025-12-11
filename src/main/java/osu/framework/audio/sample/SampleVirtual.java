package osu.framework.audio.sample;

/**
 * A virtual (no-op) sample implementation for testing or when audio is
 * disabled.
 */
public class SampleVirtual extends Sample {
    public SampleVirtual(String name) {
        super(name);
        this.length = 0;
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public SampleChannel play() {
        return new SampleChannelVirtual();
    }

    @Override
    public SampleChannel getChannel() {
        return new SampleChannelVirtual();
    }
}
