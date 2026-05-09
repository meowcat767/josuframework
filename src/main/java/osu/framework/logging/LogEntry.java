package osu.framework.logging;

public class LogEntry {
    public LogLevel level;
    public LoggingTarget target;
    public String loggerName;
    public String message;
    public Throwable exception;
}
