package osu.framework.bindables;

import java.util.function.Consumer;

/**
 * An interface for objects that can be disabled.
 */
public interface ICanBeDisabled {
    /**
     * Gets whether this object is disabled.
     * When disabled, attempting to change the value will result in an exception.
     */
    boolean isDisabled();

    /**
     * Sets whether this object is disabled.
     * 
     * @param disabled Whether to disable this object.
     */
    void setDisabled(boolean disabled);

    /**
     * Binds a callback to disabled state changes.
     * 
     * @param onChange           The callback to invoke when the disabled state
     *                           changes.
     * @param runOnceImmediately Whether to run the callback immediately with the
     *                           current state.
     */
    void bindDisabledChanged(Consumer<Boolean> onChange, boolean runOnceImmediately);
}
