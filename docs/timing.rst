Timing and Clocks
=================

josuFramework provides a unified way to handle time through clocks and global time tracking.

Time
----

The ``Time`` class provides static access to the current frame's timing information. This is updated automatically by the ``Game`` loop.

- **Time.Current**: The total time elapsed since the game started, in milliseconds.
- **Time.Elapsed**: The time elapsed since the last frame, in milliseconds.

.. code-block:: java

    @Override
    public void Update() {
        super.Update();
        
        // Move an object based on elapsed time for frame-rate independence
        float movement = (float)(speed * Time.Elapsed);
        this.x += movement;
    }

IClock
------

The ``IClock`` interface defines a basic contract for any component that provides a time source.

.. code-block:: java

    public interface IClock {
        double getCurrentTime();
    }

Specialized clocks (like those used in audio tracks or animations) implement this interface to provide their own time reference, allowing components to be synchronized to different time sources.
