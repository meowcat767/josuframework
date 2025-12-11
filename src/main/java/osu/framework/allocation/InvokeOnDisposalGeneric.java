package osu.framework.allocation;

import java.util.function.Consumer;

/**
 * Generic version of InvokeOnDisposal that passes a sender to the action.
 * 
 * @param <T> The type of the sender.
 */
public class InvokeOnDisposalGeneric<T> implements AutoCloseable {
    private final T sender;
    private final Consumer<T> action;

    /**
     * Constructs a new instance, capturing the given action to be run during
     * disposal.
     * 
     * @param sender The sender which should appear in the action callback.
     * @param action The action to invoke during disposal.
     */
    public InvokeOnDisposalGeneric(T sender, Consumer<T> action) {
        if (action == null) {
            throw new IllegalArgumentException("action cannot be null");
        }
        this.sender = sender;
        this.action = action;
    }

    /**
     * Disposes this instance, calling the initially captured action.
     */
    @Override
    public void close() {
        // No isDisposed check here so we can reuse these instances multiple times to
        // save on allocations
        action.accept(sender);
    }
}
