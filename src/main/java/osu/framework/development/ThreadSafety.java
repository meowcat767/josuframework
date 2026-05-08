package osu.framework.development;

import org.junit.jupiter.api.parallel.ExecutionMode;

public final class ThreadSafety {
    private ThreadSafety() {}

    /**
     * Whether the current code is executing on the input thread
     */
    public static final ThreadLocal<Boolean> IS_INPUT_THREAD = ThreadLocal.withInitial(() -> false);

    /**
     * Whether the current code is executing on the update thread
     */
    public static final ThreadLocal<Boolean> IS_UPDATE_THREAD = ThreadLocal.withInitial(() -> false);

    /**
     * Whether the current code is executing on the draw thread
     */
    public static final ThreadLocal<Boolean> IS_DRAW_THREAD = ThreadLocal.withInitial(() -> false);

    /**
     * Whether the current code is executing on the audio thread
     */
    public static final ThreadLocal<Boolean> IS_AUDIO_THREAD = ThreadLocal.withInitial(() -> false);

    /**
     * The current execution mode
     */
    static ExecutionMode executionMode;

    static void ensureInputThread() {
        assert IS_INPUT_THREAD.get();
    }

    static void ensureUpdateThread() {
        assert IS_UPDATE_THREAD.get();
    }

    static void ensureDrawThread() {
        assert IS_DRAW_THREAD.get();
    }

    static void ensureAudioThread() {
        assert IS_AUDIO_THREAD.get();
    }

    /**
     * Resets all thread-local state for the current thread.
     */

    static void resetAllForCurrentThread() {
        IS_INPUT_THREAD.set(false);
        IS_UPDATE_THREAD.set(false);
        IS_DRAW_THREAD.set(false);
        IS_AUDIO_THREAD.set(false);
    }
}
