Game and Environment
====================

The ``Game`` class is the entry point and root component for any josuFramework application.

Game
----

A ``Game`` acts as the top-level container for all drawables and manages the core loop, including updating the ``Time`` class and running the ``Scheduler``.

Core Responsibilities:
~~~~~~~~~~~~~~~~~~~~~~

- **Root Container**: Inherits from ``Container``, hosting the entire scene graph.
- **Main Loop**: The ``Run()`` method starts the execution, simulating the update and draw cycles.
- **Scheduling**: Provides a ``scheduler`` for marshalling tasks to the update thread.
- **Dependency Injection**: Handles the initial injection of dependencies for the game instance.

Example Usage:
~~~~~~~~~~~~~~

.. code-block:: java

    public class MyGame extends Game {
        @BackgroundDependencyLoader
        private void load() {
            add(new Box() {{
                size = new Vector2(100);
                colour = Color4.SkyBlue;
            }});
        }
    }

FrameworkEnvironment
--------------------

The ``FrameworkEnvironment`` class contains global flags and configuration settings that affect the framework's behavior at a low level.

Key Properties:
~~~~~~~~~~~~~~~

- **startupExecutionNode**: Defines the execution model (e.g., ``MULTI_THREADED``).
- **NoTestTimeout**: If enabled, prevents tests from timing out (useful for debugging).
- **ForceTestGC**: Forces garbage collection between tests.
- **FailFlakyTests**: Determines if flaky tests should cause a build failure.
- **FrameStatisticViaTouch**: Enables frame statistics overlay via touch input.

These settings are typically configured before the ``Game`` is started or during initialization.
