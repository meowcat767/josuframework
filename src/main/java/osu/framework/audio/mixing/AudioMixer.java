package osu.framework.audio.mixing;

import osu.framework.audio.AudioComponent;
import osu.framework.audio.mixing.IAudioChannel;

import java.util.ArrayList;
import java.util.List;

public abstract class AudioMixer
        extends AudioComponent
        implements IAudioMixer {

    private final List<IAudioChannel> channels =
            new ArrayList<>();

    private final AudioMixer fallbackMixer;

    protected AudioMixer(AudioMixer fallbackMixer) {
        this.fallbackMixer = fallbackMixer;
    }

    public void add(IAudioChannel channel) {

        if (channel.getMixer() == this)
            return;

        if (channel.getMixer() != null)
            channel.getMixer().remove(channel, false);

        addInternal(channel);

        channels.add(channel);

        channel.setMixer(this);
    }

    public void remove(IAudioChannel channel) {
        remove(channel, true);
    }

    protected void remove(
            IAudioChannel channel,
            boolean returnToDefault
    ) {
        if (!channels.contains(channel))
            return;

        removeInternal(channel);

        channels.remove(channel);

        channel.setMixer(null);

        if (returnToDefault && fallbackMixer != null)
            fallbackMixer.add(channel);
    }

    protected abstract void addInternal(
            IAudioChannel channel
    );

    protected abstract void removeInternal(
            IAudioChannel channel
    );
}