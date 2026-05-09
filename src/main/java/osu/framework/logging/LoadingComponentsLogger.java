package osu.framework.logging;

import osu.framework.development.DebugUtils;
import osu.framework.graphics.Drawable;
import osu.framework.graphics.containers.CompositeDrawable;
import osu.framework.lists.WeakList;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Comparator;

final class LoadingComponentsLogger {

    private static final WeakList<Drawable> loadingComponents =
            new WeakList<>();

    private LoadingComponentsLogger() {
    }

    public static void add(Drawable component) {
        if (!DebugUtils.isDebugBuild()) {
            return;
        }

        synchronized (loadingComponents) {
            loadingComponents.add(component);
        }
    }

    public static void remove(Drawable component) {
        if (!DebugUtils.isDebugBuild()) {
            return;
        }

        synchronized (loadingComponents) {
            loadingComponents.remove(component);
        }
    }

    public static void logAndFlush() {
        if (!DebugUtils.isDebugBuild()) {
            return;
        }

        synchronized (loadingComponents) {

            Logger.log(
                    "⏳ Currently loading components (" +
                            loadingComponents.size() +
                            ")"
            );

            loadingComponents.stream()
                    .sorted(
                            Comparator
                                    .comparing(
                                            c -> {
                                                Thread t = c.getLoadThread();
                                                return t != null
                                                        ? t.getName()
                                                        : "";
                                            }
                                    )
                                    .thenComparing(
                                            Drawable::getLoadState
                                    )
                    )
                    .forEach(c -> {
                        Logger.log(c.toString());

                        Logger.log(
                                "- thread: " +
                                        (
                                                c.getLoadThread() != null
                                                        ? c.getLoadThread().getName()
                                                        : "none"
                                        )
                        );

                        Logger.log(
                                "- state:  " +
                                        c.getLoadState()
                        );
                    });

            loadingComponents.clear();

            Logger.log("🧵 Task schedulers");

            Logger.log(
                    CompositeDrawable.SCHEDULER_STANDARD
                            .getStatusString()
            );

            Logger.log(
                    CompositeDrawable.SCHEDULER_LONG_LOAD
                            .getStatusString()
            );
        }

        logThreadPoolInfo();
    }

    private static void logThreadPoolInfo() {

        ThreadMXBean bean =
                ManagementFactory.getThreadMXBean();

        Logger.log("🎱 Thread system");

        Logger.log(
                "thread count:    " +
                        bean.getThreadCount()
        );

        Logger.log(
                "peak threads:    " +
                        bean.getPeakThreadCount()
        );

        Logger.log(
                "daemon threads:  " +
                        bean.getDaemonThreadCount()
        );

        Logger.log(
                "total started:   " +
                        bean.getTotalStartedThreadCount()
        );

        Logger.log(
                "processors:      " +
                        Runtime.getRuntime()
                                .availableProcessors()
        );
    }
}