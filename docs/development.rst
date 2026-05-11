josuFramework - Development
==============================

josuFramework provides several utility classes to assist during development and testing, primarily located in the ``osu.framework.development`` package.

DebugUtils
----------

``DebugUtils`` provides methods to detect the environment the framework is running in.

Detecting JUnit
~~~~~~~~~~~~~~~

You can check if the code is currently running as part of a JUnit test.

.. code-block:: java

    if (DebugUtils.isJUnitRunning()) {
        // Test-specific logic
    }

Detecting Debug Builds
~~~~~~~~~~~~~~~~~~~~~~

You can check if the current build is a "debug" build. In Java, this is typically detected by checking if assertions are enabled or if a JDWP agent (debugger) is attached.

.. code-block:: java

    if (DebugUtils.isDebugBuild()) {
        // Debug-only logging or validation
    }

ThreadSafety
------------

The ``ThreadSafety`` class provides markers and validation for ensuring code runs on the correct thread. josuFramework uses several dedicated threads:

- **Update Thread**: For game logic and component updates.
- **Draw Thread**: For rendering operations.
- **Input Thread**: For processing user input.
- **Audio Thread**: For audio processing.

Checking the Current Thread
~~~~~~~~~~~~~~~~~~~~~~~~~~~

You can check if the current execution is on a specific thread using ``ThreadLocal`` markers.

.. code-block:: java

    if (ThreadSafety.IS_UPDATE_THREAD.get()) {
        // We are on the update thread
    }

Thread Enforcement (Assertions)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The framework provides internal methods to ensure thread safety using Java assertions. These will throw an ``AssertionError`` if called from the wrong thread when assertions are enabled (``-ea``).

.. code-block:: java

    // Internal framework usage
    ThreadSafety.ensureUpdateThread();
    ThreadSafety.ensureDrawThread();
    ThreadSafety.ensureInputThread();
    ThreadSafety.ensureAudioThread();
