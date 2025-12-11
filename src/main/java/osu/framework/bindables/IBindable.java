package osu.framework.bindables;

import java.util.function.Consumer;

/**
 * A read-only interface for bindables.
 * 
 * @param <T> The type of value stored in the bindable.
 */
public interface IBindable<T> {
    /**
     * Gets the current value.
     */
    T getValue();

    /**
     * Gets the default value.
     */
    T getDefault();

    /**
     * Checks if this bindable is disabled.
     */
    boolean isDisabled();

    /**
     * Binds a callback to value changes.
     * 
     * @param onChange           The callback to invoke when the value changes.
     * @param runOnceImmediately Whether to run the callback immediately with the
     *                           current value.
     */
    void bindValueChanged(Consumer<ValueChangedEvent<T>> onChange, boolean runOnceImmediately);

    /**
     * Binds a callback to disabled state changes.
     * 
     * @param onChange           The callback to invoke when the disabled state
     *                           changes.
     * @param runOnceImmediately Whether to run the callback immediately with the
     *                           current state.
     */
    void bindDisabledChanged(Consumer<Boolean> onChange, boolean runOnceImmediately);

    /**
     * Unbinds all bindings and callbacks.
     */
    void unbindAll();

    /**
     * Creates an unbound copy of this bindable.
     */
    IBindable<T> getUnboundCopy();
}
