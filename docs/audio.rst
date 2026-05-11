josuFramework - Audio
=====================

The audio system in josuFramework is designed to be flexible, thread-safe, and highly adjustable. Most audio components inherit from ``AdjustableAudioComponent``, which provides built-in support for volume, balance, frequency, and tempo adjustments.

Audio Components
----------------

All audio objects in the framework are based on ``AudioComponent``. This base class handles:

- **Audio Thread Deferring**: Operations are enqueued to be executed on the dedicated audio thread.
- **Disposal**: Proper cleanup of native audio resources.
- **Update Logic**: Periodic updates for state management and child components.

Adjustable Audio Components
---------------------------

``AdjustableAudioComponent`` adds the ability to adjust various audio properties. These properties are managed using ``BindableNumber<Double>`` objects, allowing for easy UI binding and automatic updates.

Adjustable Properties
~~~~~~~~~~~~~~~~~~~~~

The following properties can be adjusted:

- **Volume**: The loudness of the component (0.0 to 1.0).
- **Balance**: The stereo panning (-1.0 for left, 1.0 for right, 0.0 for center).
- **Frequency**: The playback rate, which also affects the pitch.
- **Tempo**: The playback rate, without affecting the pitch.

Aggregation
~~~~~~~~~~~

Each property is an *aggregate* of multiple sources. This means you can have a "master volume" and a "local volume" both affecting the same component.

- **Local Adjustments**: Accessed via ``getVolume()``, ``getBalance()``, etc.
- **Global/Parent Adjustments**: Linked using ``bindAdjustments(IAggregateAudioAdjustment)``.
- **Manual Adjustments**: Added using ``addAdjustment(AdjustableProperty type, IBindable<Double> adjustBindable)``.

The final value used for playback is the result of multiplying all sources (for Volume, Frequency, and Tempo) or summing them (for Balance).

Tracks and Samples
------------------

The framework distinguishes between two main types of audio:

Tracks
~~~~~~

Tracks (``ITrack``) are intended for long-running audio, like background music.

.. code-block:: java

    ITrack track = trackStore.get("music.mp3");
    track.start();
    track.setLooping(true);
    track.getVolume().setValue(0.5);

Samples
~~~~~~~

Samples (``ISample``) are intended for short sound effects. Playing a sample returns a ``SampleChannel``, which allows for independent adjustment of that specific playback instance.

.. code-block:: java

    ISample sample = sampleStore.get("hit.wav");
    ISampleChannel channel = sample.play();
    channel.getVolume().setValue(0.8);

Samples also have a ``playbackConcurrency`` setting to limit how many instances of the same sample can play simultaneously.

Audio Mixing
------------

The ``AudioMixer`` (IAudioMixer) combines audio from multiple ``IAudioChannel`` instances into a single output stream.

Each channel provides PCM sample data via:

.. code-block:: java
   for (IAudioChannel channel : channels) {
    channel.read(tempBuffer, 0, length);

    for (int i = 0; i < length; i++) {
        output[i] += tempBuffer[i];
    }
}

Adjustments suck as volume, balance, frequency and temp are propagated through the mixer hierarchy using AdjustableAudioComponent.

Mixer adjustments

Adjustments applied to a mixer affect all channels attached to it.

.. code-block:: java

    AudioMixer sfxMixer = new AudioMixer();
    sfxMixer.getVolume().setValue(0.7);

    ISampleChannel channel = sample.play();
    sfxMixer.add(channel);

The final playback volume of the channel will include both:

- the channel's own volume
- the mixer's aggregate volume

Clipping
~~~~~~~~

Because audio samples are summed together, mixed output may exceed the valid sample range.

Implementations should clamp output values:

.. code-block:: java

    sample = Math.max(-1.0f, Math.min(1.0f, sample));

Threading
~~~~~~~~~

Mixers are expected to operate on the dedicated audio thread. Channel modifications should avoid concurrent modification during rendering.

The current implementation uses a simplified update model and may evolve toward a lock-free or command-queue-based audio pipeline in future versions.

