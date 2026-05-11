josuFramework - Threading
==========================

Threading in josuFramework is designed to be mostly single-threaded from the perspective of the update logic, while offloading expensive operations to other threads where possible. The framework uses a scheduling system to ensure that tasks are executed on the correct thread at the right time.

Scheduler
---------

The ``Scheduler`` class is the primary way to queue tasks for execution on the main thread (usually the Update thread). It supports both immediate execution (in the next update cycle) and delayed execution.

Methods:
~~~~~~~~

- **add(Runnable task)**: Queues a task for execution in the next ``update()`` cycle.
- **addDelayed(Runnable task, double delay)**: Queues a task to be executed after a specified delay (in milliseconds).
- **update()**: Processes all queued tasks. This is typically called by the framework's main loop.

.. code-block:: java

    // Scheduling a task for the next frame
    scheduler.add(() -> {
        System.out.println("Executing on the next update cycle");
    });

    // Scheduling a task with a 1000ms delay
    scheduler.addDelayed(() -> {
        System.out.println("Executing after 1 second");
    }, 1000);

Thread Safety
-------------

The framework maintains several thread-local markers to identify which core thread is currently executing. This is useful for asserting that certain operations are happening on the expected thread.

Core Threads:
~~~~~~~~~~~~~

- **Update Thread**: Handles game logic, transforms, and scene graph updates.
- **Draw Thread**: Handles rendering of the scene graph.
- **Audio Thread**: Handles audio processing and playback.
- **Input Thread**: Handles window events and user input.

You can check the current thread using the ``ThreadSafety`` class:

.. code-block:: java

    if (ThreadSafety.IS_UPDATE_THREAD.get()) {
        // We are on the update thread
    }

Usage in Components
-------------------

Many components in the framework, such as ``Game`` and ``CompositeDrawable``, have their own ``Scheduler`` instances. When you need to perform an operation that affects the scene graph from a background thread (like a network callback), you should use the scheduler to marshal the call back to the update thread.

.. code-block:: java

    // From a background thread
    game.scheduler.add(() -> {
        // This code now runs safely on the update thread
        myBox.fadeColour(Color4.Green, 500);
    });
