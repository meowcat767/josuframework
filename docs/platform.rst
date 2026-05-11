josuFramework - Platform
=========================

The platform package provides abstractions for interacting with the underlying operating system and hardware in a cross-platform manner.

Storage
-------

The ``Storage`` class provides a sandboxed file system access, ensuring that the application only interacts with files within a specific base directory. This is useful for storing configuration files, logs, and game data.

Key Features:
~~~~~~~~~~~~~

- **Sandboxing**: All paths are relative to a base path, preventing access to files outside the intended directory.
- **Sub-folders**: You can easily create a sub-storage for a specific directory using ``getStorageForDirectory(String path)``.
- **Safe Writing**: ``createFileSafely(String path)`` returns an ``OutputStream`` that writes to a temporary file and only replaces the target file upon successful closure, preventing data corruption.

Common Methods:
~~~~~~~~~~~~~~~

- ``exists(String path)``: Checks if a file exists at the given path.
- ``getFiles(String path)``: Returns a list of files in the specified directory.
- ``getInputStream(String path)`` / ``getOutputStream(String path)``: Basic file I/O.
- ``delete(String path)``: Deletes a file.
- ``openFileExternally(String filename)``: Opens the file using the default OS handler.

.. code-block:: java

    Storage storage = new Storage("my-game-data");
    Storage logs = storage.getStorageForDirectory("logs");

    try (OutputStream stream = storage.createFileSafely("config.json")) {
        // Write data to stream
    }

Clipboard
---------

The ``Clipboard`` class provides a way to interact with the system clipboard for copying and pasting text and images.

- ``getText()`` / ``setText(String text)``: Manage text in the clipboard.
- ``getImage()`` / ``setImage(BufferedImage image)``: Manage images in the clipboard.

CursorState
-----------

The ``CursorState`` enum defines how the mouse cursor should behave within the game window.

- ``DEFAULT``: The cursor is visible and moves freely.
- ``HIDDEN``: The cursor is hidden when over the window.
- ``CONFINED``: The cursor is restricted to the window boundaries when the window is focused.
- ``HIDDEN_AND_CONFINED``: Both hidden and restricted.

GameHost
--------

The ``GameHost`` (represented by ``DesktopGameHost`` in desktop environments) is the core class responsible for managing the application's lifecycle, windowing, and input.

*Note: The platform implementation is currently under development.*
