package osu.framework.audio;

/**
 * Handles relative frequency adjustments for Bass audio.
 * TODO: Implement with actual Java audio backend.
 */
public class BassRelativeFrequencyHandler {
    private double relativeFrequency = 1.0;

    /**
     * Gets the current relative frequency.
     */
    public double getRelativeFrequency() {
        return relativeFrequency;
    }

    /**
     * Sets the relative frequency.
     * 
     * @param frequency The frequency multiplier (1.0 = normal speed).
     */
    public void setRelativeFrequency(double frequency) {
        this.relativeFrequency = frequency;
    }
}
