package osu.framework.allocation;

import java.util.function.Consumer;

/**
 * Instances of this class capture an action for later cleanup.
 * When a method returns an instance of this class, the appropriate usage is:
 * 
 * <pre>
 * try (InvokeOnDisposal disposable = someMethod()) {
 *     // ...
 * }
 * </pre>
 * 
 * The try-with-resources block will automatically dispose the returned
 * instance, doing the necessary cleanup work.
 */
public class InvokeOnDisposal implements AutoCloseable {
    private final Runnable action;

    /**
     * Constructs a new instance, capturing the given action to be run during
     * disposal.
     * 
     * @param action The action to invoke during disposal.
     */
    public InvokeOnDisposal(Runnable action) {
        if (action == null) {
            throw new IllegalArgumentException("action cannot be null");
        }
        this.action = action;
    }

    /**
     * Disposes this instance, calling the initially captured action.
     */
    @Override
    public void close() {
        // No isDisposed check here so we can reuse these instances multiple times to
        // save on allocations
        action.run();
    }
}
