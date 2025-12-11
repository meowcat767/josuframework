package osu.framework.audio.sample;

/**
 * A virtual (no-op) sample channel implementation.
 */
public class SampleChannelVirtual extends SampleChannel {
    @Override
    public void play() {
        super.play();
        // No actual playback
        playing = false; // Immediately stop since it's virtual
    }

    @Override
    public boolean isLoaded() {
        return true;
    }
}
