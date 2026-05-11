josuFramework - Configuration
==============================

The configuration system in josuFramework provides a robust way to manage, persist, and track changes to application settings. It is built upon the ``Bindable`` system, ensuring that changes to configuration are automatically propagated to all interested components.

ConfigManager<TLookup>
----------------------

``ConfigManager<TLookup>`` is the base class for all configuration managers. It uses an ``Enum`` as a lookup key for settings.

Core Features:
~~~~~~~~~~~~~~

- **Bindable Integration**: Each configuration item is backed by a ``Bindable<T>``.
- **Default Values**: Provides a way to define default values and constraints (min/max) for settings.
- **Persistence**: Supports loading and saving settings (implementation-specific).
- **Type Safety**: Uses generics and enums to ensure type-safe access to settings.

Example usage:

.. code-block:: java

    public enum MySettings {
        Volume,
        Username,
        Fullscreen
    }

    public class MyConfigManager extends ConfigManager<MySettings> {
        @Override
        protected void initialiseDefaults() {
            setDefault(MySettings.Volume, 1.0, 0.0, 1.0);
            setDefault(MySettings.Username, "Guest");
            setDefault(MySettings.Fullscreen, true);
        }
    }

Accessing Settings
~~~~~~~~~~~~~~~~~~

You can retrieve a ``Bindable`` for a setting using ``getBindable(TLookup)``.

.. code-block:: java

    Bindable<Double> volume = config.getBindable(MySettings.Volume);
    volume.bindValueChanged(e -> System.out.println("Volume: " + e.getNewValue()));

Alternatively, you can use ``bindWith(TLookup, Bindable)`` to bind an existing bindable to a configuration setting.

IniConfigManager
----------------

``IniConfigManager`` is a concrete implementation of ``ConfigManager`` that stores settings in a plain-text ``.ini`` file. It handles serialization and deserialization of common types (String, Integer, Double, Boolean, and Enums).

.. code-block:: java

    IniConfigManager<MySettings> config = new IniConfigManager<>("settings.ini");
    config.load();
    // ...
    config.save();

Change Tracking
---------------

The configuration system supports tracking changes to settings for purposes such as displaying notifications or logging.

- ``ITrackableConfigManager``: Interface for config managers that support tracking.
- ``TrackedSettings``: A collection of settings being tracked.
- ``TrackedSetting<T>``: Represents an individual setting being tracked.
- ``SettingDescription``: Provides a human-readable name and value for a setting.

Example of setting up tracking:

.. code-block:: java

    public class MyConfigManager extends ConfigManager<MySettings> implements ITrackableConfigManager {
        // ...
        @Override
        public TrackedSettings createTrackedSettings() {
            TrackedSettings settings = new TrackedSettings();
            settings.addSetting(new TrackedSetting<>(MySettings.Volume, 
                v -> new SettingDescription("Volume", String.format("%.0f%%", (Double)v * 100))));
            return settings;
        }
    }

When a setting changes, you can use these descriptions to inform the user.
