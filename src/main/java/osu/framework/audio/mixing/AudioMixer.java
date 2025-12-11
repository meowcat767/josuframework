package osu.framework.audio.mixing;

import osu.framework.audio.AdjustableAudioComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * An audio mixer that combines multiple audio channels.
 * TODO: Implement actual mixing logic with Java audio backend.
 */
public class AudioMixer extends AdjustableAudioComponent implements IAudioMixer {
    private final List<IAudioChannel> channels = new ArrayList<>();

    @Override
    public void add(IAudioChannel channel) {
        if (!channels.contains(channel)) {
            channels.add(channel);
            channel.bindAdjustments(this);
        }
    }

    @Override
    public void remove(IAudioChannel channel) {
        if (channels.remove(channel)) {
            channel.unbindAdjustments(this);
        }
    }

    @Override
    protected void updateChildren() {
        super.updateChildren();

        // Update all channels
        for (IAudioChannel channel : new ArrayList<>(channels)) {
            if (channel instanceof AdjustableAudioComponent) {
                ((AdjustableAudioComponent) channel).update();
            }
        }
    }
}
