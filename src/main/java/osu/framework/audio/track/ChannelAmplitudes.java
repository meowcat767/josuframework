package osu.framework.audio.track;

/**
 * Represents amplitude data for audio channels.
 */
public class ChannelAmplitudes {
    /**
     * The left channel amplitude.
     */
    public float leftChannel;

    /**
     * The right channel amplitude.
     */
    public float rightChannel;

    /**
     * The maximum amplitude across all channels.
     */
    public float maximum;

    /**
     * The average amplitude across all channels.
     */
    public float average;

    /**
     * Frequency data (if available).
     */
    public float[] frequencyAmplitudes;

    public ChannelAmplitudes() {
        this.leftChannel = 0f;
        this.rightChannel = 0f;
        this.maximum = 0f;
        this.average = 0f;
        this.frequencyAmplitudes = new float[0];
    }

    public ChannelAmplitudes(float leftChannel, float rightChannel) {
        this.leftChannel = leftChannel;
        this.rightChannel = rightChannel;
        this.maximum = Math.max(leftChannel, rightChannel);
        this.average = (leftChannel + rightChannel) / 2f;
        this.frequencyAmplitudes = new float[0];
    }
}
