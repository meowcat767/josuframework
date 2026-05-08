package osu.framework.development;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test
import osu.framework.timing.IClock;

public final class DebugUtils {

    /**
     * Represents a clock that can run faster than realtime during tests.
     */
    static IClock realtimeClock;

    private static Boolean isNUnitRunning;
    private static Boolean isDebugBuild;
    private static Class<?> junitTestClass;

    /**
     * Whether the framework is currently logging performance issues.
     */
    public static boolean logPerformanceIssues;

    private DebugUtils() {
        // Utility class
    }

    public static boolean isJUnitRunning() {
        if (isNUnitRunning == null) {
            isNUnitRunning = detectJUnit();
        }

        return isNUnitRunning;
    }

    private static boolean detectJUnit() {
        // JVM arguments often contain junit runner info
        String runtimeArgs = ManagementFactory
                .getRuntimeMXBean()
                .getInputArguments()
                .toString()
                .toLowerCase();

        if (runtimeArgs.contains("junit")) {
            return true;
        }

        // Stacktrace inspection fallback
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::getClassName)
                .anyMatch(name ->
                        name.contains("org.junit")
                                || name.contains("junit")
                                || name.contains("gradle")
                                || name.contains("surefire")
                );
    }

    public static Class<?> getJUnitTestClass() {
        if (junitTestClass == null) {
            if (!isJUnitRunning()) {
                throw new IllegalStateException("Not running under JUnit");
            }

            junitTestClass = findCurrentTestClass();
        }

        return junitTestClass;
    }

    private static Class<?> findCurrentTestClass() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            try {
                Class<?> cls = Class.forName(element.getClassName());

                for (Method method : cls.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(org.junit.jupiter.api.Test.class)) {
                        return cls;
                    }
                }
            } catch (Throwable ignored) {
            }
        }

        throw new IllegalStateException("Could not determine current test class");
    }

    public static boolean isDebugBuild() {
        if (isDebugBuild == null) {
            isDebugBuild = detectDebugBuild();
        }

        return isDebugBuild;
    }

    private static boolean detectDebugBuild() {
        /*
         * Java doesn't really have a direct equivalent to
         * C#'s DebuggableAttribute / JIT tracking.
         *
         * Common approaches:
         * - JVM debug agent attached
         * - assertions enabled
         * - build flags/system properties
         */

        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;

        return assertionsEnabled
                || ManagementFactory.getRuntimeMXBean()
                .getInputArguments()
                .toString()
                .contains("-agentlib:jdwp");
    }
}