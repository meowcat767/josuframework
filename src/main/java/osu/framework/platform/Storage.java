package osu.framework.platform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Storage {

    protected final Path basePath;

    protected Storage(String path) {
        this(path, null);
    }

    protected Storage(String path, String subfolder) {

        this.basePath = initialiseBasePath(
                Paths.get(path),
                subfolder
        );

        if (basePath == null) {
            throw new IllegalStateException(
                    "basePath not initialized"
            );
        }
    }

    private static Path initialiseBasePath(
            Path path,
            String subfolder
    ) {

        Path result = path;

        if (subfolder != null && !subfolder.isBlank()) {

            String clean =
                    stripInvalidFilenameChars(subfolder);

            result = result.resolve(clean);
        }

        return result;
    }

    private static String stripInvalidFilenameChars(
            String value
    ) {

        return value.replaceAll(
                "[\\\\/:*?\"<>|]",
                ""
        );
    }

    public Path getBasePath() {
        return basePath;
    }

    /**
     * Get a usable filesystem path.
     */
    public abstract Path getFullPath(
            String path,
            boolean createIfNotExisting
    );

    public Path getFullPath(String path) {
        return getFullPath(path, false);
    }

    public Path getPath(String path) {
        return getFullPath(path, true);
    }

    public abstract boolean exists(String path);

    public abstract boolean existsDirectory(
            String path
    );

    public abstract void deleteDirectory(
            String path
    );

    public abstract void delete(String path);

    public abstract List<String> getDirectories(
            String path
    );

    public abstract List<String> getFiles(
            String path,
            String pattern
    );

    public List<String> getFiles(String path) {
        return getFiles(path, "*");
    }

    /**
     * Retrieve a storage rooted at a subdirectory.
     */
    public Storage getStorageForDirectory(
            String path
    ) {

        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException(
                    "path cannot be empty"
            );
        }

        Path fullPath =
                getFullPath(path, true);

        try {

            return getClass()
                    .getConstructor(String.class)
                    .newInstance(fullPath.toString());

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to create storage",
                    e
            );
        }
    }

    public abstract void move(
            String from,
            String to
    );

    /**
     * Create a temporary-safe output stream.
     */
    public OutputStream createFileSafely(
            String path
    ) throws IOException {

        Path original =
                getFullPath(path, true);

        Path temp =
                original.resolveSibling(
                        "_" +
                                original.getFileName() +
                                "_" +
                                UUID.randomUUID()
                );

        return new SafeWriteStream(
                temp,
                original,
                this
        );
    }

    public abstract InputStream getInputStream(
            String path
    ) throws IOException;

    public abstract OutputStream getOutputStream(
            String path,
            OpenOption... options
    ) throws IOException;

    public abstract boolean openFileExternally(
            String filename
    );

    public boolean presentExternally() {
        return openFileExternally("");
    }

    public abstract boolean presentFileExternally(
            String filename
    );

    /**
     * Safe temporary write stream.
     */
    private static class SafeWriteStream
            extends OutputStream {

        private final Path temporaryPath;

        private final Path finalPath;

        private final Storage storage;

        private final OutputStream delegate;

        private boolean closed;

        public SafeWriteStream(
                Path temporaryPath,
                Path finalPath,
                Storage storage
        ) throws IOException {

            this.temporaryPath = temporaryPath;
            this.finalPath = finalPath;
            this.storage = storage;

            Files.createDirectories(
                    temporaryPath.getParent()
            );

            this.delegate =
                    Files.newOutputStream(
                            temporaryPath,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING
                    );
        }

        @Override
        public void write(int b)
                throws IOException {

            delegate.write(b);
        }

        @Override
        public void write(
                byte[] b,
                int off,
                int len
        ) throws IOException {

            delegate.write(b, off, len);
        }

        @Override
        public void flush()
                throws IOException {

            delegate.flush();
        }

        @Override
        public void close()
                throws IOException {

            if (closed) {
                return;
            }

            delegate.close();

            Files.deleteIfExists(finalPath);

            Files.move(
                    temporaryPath,
                    finalPath,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
            );

            closed = true;
        }
    }
}