josuFramework - Logging
=======================

The logging system in josuFramework provides a centralized way to record diagnostic information about the application's runtime behavior. It supports multiple log levels, targets, and filtering.

Core Components
---------------

Logger
~~~~~~

The ``Logger`` class is the primary entry point for all logging operations. Most interactions occur through its static methods.

.. code-block:: java

    // Basic logging
    Logger.log("Application started");

    // Logging with specific target and level
    Logger.log("Network request failed", LoggingTarget.NETWORK, LogLevel.IMPORTANT);

    // Logging an error
    try {
        doSomething();
    } catch (Exception e) {
        Logger.error(e, "An unexpected error occurred");
    }

Log Levels
~~~~~~~~~~

The ``LogLevel`` enum defines the severity of a log entry:

- **DEBUG**: Fine-grained informational events that are most useful to debug an application.
- **VERBOSE**: Informational messages that highlight the progress of the application at coarse-grained level.
- **IMPORTANT**: Potentially harmful situations or important lifecycle events.
- **ERROR**: Error events that might still allow the application to continue running.

Global Configuration
~~~~~~~~~~~~~~~~~~~~

The logging behavior can be controlled via static fields in the ``Logger`` class:

- ``Logger.ENABLED``: A boolean to globally enable or disable logging.
- ``Logger.LEVEL``: The minimum ``LogLevel`` required for a message to be logged. By default, this is ``DEBUG`` in debug builds and ``VERBOSE`` otherwise.
- ``Logger.GAME_IDENTIFIER``: Used in log filenames to identify the application.

Logging Targets
---------------

Logs are categorized into different ``LoggingTarget`` values, which typically correspond to separate log files.

- ``RUNTIME``: General application logic and lifecycle (default).
- ``NETWORK``: Network-related activities.
- ``INPUT``: Input handling events.
- ``PERFORMANCE``: Performance metrics and profiling data.
- ``DATABASE``: Database operations.
- ``INFORMATION``: High-level system information.

Log Storage
-----------

Logs are written to files using the framework's ``Storage`` system. Each session generates log files named with a timestamp and the target name (e.g., ``1625097600.runtime.log``).

Filtering
---------

The ``Logger`` supports message filtering, which can be used to redact sensitive information or clean up logs.

.. code-block:: java

    // Add a filter that will be applied to all future log messages
    Logger.addFilteredText("password123");
