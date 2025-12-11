package osu.framework.bindables;

import java.util.Objects;

/**
 * An event representing a value change in a bindable.
 * 
 * @param <T> The type of value that changed.
 */
public class ValueChangedEvent<T> {
    private final T oldValue;
    private final T newValue;

    /**
     * Creates a new value changed event.
     * 
     * @param oldValue The previous value.
     * @param newValue The new value.
     */
    public ValueChangedEvent(T oldValue, T newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Gets the previous value.
     */
    public T getOldValue() {
        return oldValue;
    }

    /**
     * Gets the new value.
     */
    public T getNewValue() {
        return newValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ValueChangedEvent))
            return false;
        ValueChangedEvent<?> other = (ValueChangedEvent<?>) obj;
        return Objects.equals(oldValue, other.oldValue) && Objects.equals(newValue, other.newValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldValue, newValue);
    }

    @Override
    public String toString() {
        return "ValueChangedEvent{oldValue=" + oldValue + ", newValue=" + newValue + "}";
    }
}
