package osu.framework.audio;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A base class for audio components which offers audio thread deferring,
 * disposal and basic update logic.
 */
public abstract class AudioComponent implements IUpdateable {
    /**
     * Audio operations will be run on a separate dedicated thread,
     * so we need to schedule any audio API calls using this queue.
     */
    protected final ConcurrentLinkedQueue<Runnable> pendingActions = new ConcurrentLinkedQueue<>();

    private volatile boolean acceptingActions = true;
    private volatile boolean isDisposed = false;

    /**
     * Whether an audio thread specific action can be performed inline.
     * For now, we'll assume we're always on the audio thread in Java.
     * TODO: Implement proper thread checking when audio threading is added.
     */
    protected boolean canPerformInline() {
        return true; // Simplified for now
    }

    /**
     * Enqueues an action to be performed on the audio thread.
     * 
     * @param action The action to perform.
     */
    protected void enqueueAction(Runnable action) {
        if (canPerformInline()) {
            action.run();
            return;
        }

        if (!acceptingActions) {
            // We don't want consumers to block on operations after we are disposed
            return;
        }

        pendingActions.add(action);
    }

    /**
     * Run each loop of the audio thread's execution after queued actions are
     * completed
     * to allow components to perform any additional operations.
     */
    protected void updateState() {
        // Override in subclasses
    }

    /**
     * Run each loop of the audio thread's execution, after {@link #updateState()}
     * as a way to update any child components.
     */
    protected void updateChildren() {
        // Override in subclasses
    }

    /**
     * Updates this audio component. Always runs on the audio thread.
     */
    @Override
    public void update() {
        if (isDisposed) {
            throw new IllegalStateException("Cannot update disposed audio component");
        }

        // Process pending actions
        Runnable action;
        while ((action = pendingActions.poll()) != null) {
            try {
                action.run();
            } catch (Exception e) {
                // Log or handle exception
                e.printStackTrace();
            }
        }

        if (!isDisposed) {
            updateState();
        }

        updateChildren();
    }

    /**
     * Whether this component has completed playback and is in a stopped state.
     */
    public boolean hasCompleted() {
        return !isAlive();
    }

    /**
     * When false, this component has completed all processing and is ready to be
     * removed from its parent.
     */
    public boolean isAlive() {
        return !isDisposed;
    }

    /**
     * Whether this component has finished loading its resources.
     */
    public boolean isLoaded() {
        return true;
    }

    /**
     * Whether this component has been disposed.
     */
    public boolean isDisposed() {
        return isDisposed;
    }

    /**
     * Disposes this audio component.
     */
    public void dispose() {
        acceptingActions = false;
        pendingActions.add(() -> dispose(true));
    }

    /**
     * Disposes this audio component.
     * 
     * @param disposing Whether to dispose managed resources.
     */
    protected void dispose(boolean disposing) {
        isDisposed = true;
    }
}
