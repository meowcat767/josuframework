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

The ``AudioMixer`` (``IAudioMixer``) allows grouping multiple audio channels together. Adjustments applied to a mixer are automatically propagated to all channels added to it.

.. code-block:: java

    AudioMixer sfxMixer = new AudioMixer();
    sfxMixer.getVolume().setValue(0.7);

    ISampleChannel channel = sample.play();
    sfxMixer.add(channel); // The channel's volume will now be affected by the mixer's volume.

Implementation Details
----------------------

While the API is mostly stable, the underlying implementation currently uses a simplified model. Proper threading and native audio backend (e.g., BASS) integration are planned for future updates.
