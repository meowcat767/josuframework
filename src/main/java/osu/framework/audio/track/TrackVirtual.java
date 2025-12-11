package osu.framework.audio.track;

/**
 * A virtual (no-op) track implementation for testing or when audio is disabled.
 */
public class TrackVirtual extends Track {
    private double currentTime = 0;

    public TrackVirtual() {
        this.length = 1000; // Default 1 second
    }

    @Override
    public boolean hasCompleted() {
        return !running && currentTime >= length;
    }

    @Override
    public void start() {
        super.start();
        currentTime = restartPoint;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void updateState() {
        super.updateState();

        if (running) {
            // Simulate time progression (would need actual delta time in real
            // implementation)
            currentTime += 16; // Assume ~60fps updates

            if (currentTime >= length) {
                if (looping) {
                    currentTime = restartPoint;
                } else {
                    stop();
                }
            }
        }
    }

    @Override
    public boolean isLoaded() {
        return true;
    }
}
