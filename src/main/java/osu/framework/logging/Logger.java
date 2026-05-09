package osu.framework.logging;

import osu.framework.development.DebugUtils;
import osu.framework.platform.Storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {

    private static final Object STATIC_LOCK =
            new Object();

    public static boolean ENABLED = true;

    public static LogLevel LEVEL =
            DebugUtils.isDebugBuild()
                    ? LogLevel.DEBUG
                    : LogLevel.VERBOSE;

    public static String GAME_IDENTIFIER = "game";

    public static String VERSION_IDENTIFIER = "unknown";

    private static Storage storage;

    private static final long SESSION_TIMESTAMP =
            Instant.now().getEpochSecond();

    private static final Map<String, Logger> LOGGERS =
            new ConcurrentHashMap<>();

    private static final List<String> FILTERS =
            Collections.synchronizedList(
                    new ArrayList<>()
            );

    private final String name;

    private final String filename;

    private final LoggingTarget target;

    public Logger(LoggingTarget target) {
        this.target = target;
        this.name = target.name().toLowerCase();
        this.filename =
                SESSION_TIMESTAMP +
                        "." +
                        name +
                        ".log";
    }

    public static Logger getLogger(
            LoggingTarget target
    ) {
        return LOGGERS.computeIfAbsent(
                target.name().toLowerCase(),
                k -> new Logger(target)
        );
    }

    public static void log(
            String message
    ) {
        log(
                message,
                LoggingTarget.RUNTIME,
                LogLevel.VERBOSE
        );
    }

    public static void log(
            String message,
            LoggingTarget target,
            LogLevel level
    ) {

        if (!ENABLED || level.ordinal() < LEVEL.ordinal()) {
            return;
        }

        getLogger(target).add(message, level);
    }

    public static void error(
            Throwable throwable,
            String description
    ) {

        String msg =
                description +
                        "\n" +
                        throwable;

        log(
                msg,
                LoggingTarget.RUNTIME,
                LogLevel.ERROR
        );
    }

    public void add(
            String message,
            LogLevel level
    ) {

        message = applyFilters(message);

        String line =
                "[" +
                        LocalDateTime.now(ZoneOffset.UTC)
                                .format(
                                        DateTimeFormatter.ofPattern(
                                                "yyyy-MM-dd HH:mm:ss"
                                        )
                                ) +
                        "] [" +
                        level.name().toLowerCase() +
                        "] " +
                        message;

        if (DebugUtils.isDebugBuild()) {
            System.out.println(
                    "[" + name + "] " + line
            );
        }

        if (target == LoggingTarget.INFORMATION) {
            return;
        }

        writeLine(line);
    }

    private void writeLine(String line) {

        if (storage == null) {
            return;
        }

        synchronized (STATIC_LOCK) {

            try (
                    BufferedWriter writer =
                            Files.newBufferedWriter(
                                    storage.getPath(filename),
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.APPEND
                            )
            ) {

                writer.write(line);
                writer.newLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addFilteredText(
            String text
    ) {

        if (text == null || text.isEmpty()) {
            return;
        }

        FILTERS.add(text);
    }

    public static String applyFilters(
            String message
    ) {

        synchronized (FILTERS) {

            for (String filter : FILTERS) {

                message =
                        message.replace(
                                filter,
                                "*".repeat(filter.length())
                        );
            }
        }

        return message;
    }

    public static void setStorage(
            Storage storage
    ) {
        Logger.storage = storage;
    }
}