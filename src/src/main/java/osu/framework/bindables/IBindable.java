package osu.framework.bindables;

import java.util.function.Consumer;

/**
 * An interface which can be bound to other {@link IBindable}s in order to watch
 * for value and disabled changes.
 * 
 * @param <T> The type of value encapsulated by this bindable.
 */
public interface IBindable<T> extends ICanBeDisabled, IHasDefaultValue, IUnbindable {
    /**
     * Gets the current value of this bindable.
     */
    T getValue();

    /**
     * Gets the default value of this bindable.
     */
    T getDefault();

    /**
     * Binds this bindable to another such that bi-directional updates are
     * propagated.
     * This will adopt any values and value limitations of the bindable bound to.
     * 
     * @param them The foreign bindable. This should always be the most permanent
     *             end of the bind (ie. a ConfigManager).
     */
    void bindTo(IBindable<T> them);

    /**
     * Binds a callback to value changes with the option of running the bound
     * callback once immediately.
     * 
     * @param onChange           The action to perform when the value changes.
     * @param runOnceImmediately Whether the action provided should be run once
     *                           immediately.
     */
    void bindValueChanged(Consumer<ValueChangedEvent<T>> onChange, boolean runOnceImmediately);

    /**
     * Retrieves a new bindable instance weakly bound to the configuration backing.
     * If you are further binding to events of a bindable retrieved using this
     * method, ensure to hold a local reference.
     * 
     * @return A weakly bound copy of the specified bindable.
     */
    IBindable<T> getBoundCopy();

    /**
     * Creates an unbound copy of this bindable.
     * 
     * @return An unbound copy with the same value and settings.
     */
    IBindable<T> getUnboundCopy();
}
