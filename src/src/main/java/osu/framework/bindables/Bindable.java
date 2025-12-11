package osu.framework.bindables;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A generic implementation of {@link IBindable}.
 * 
 * @param <T> The type of value stored in this bindable.
 */
public class Bindable<T> implements IBindable<T> {
    private T value;
    private T defaultValue;
    private boolean disabled;

    private final List<Consumer<ValueChangedEvent<T>>> valueChangedListeners = new ArrayList<>();
    private final List<Consumer<Boolean>> disabledChangedListeners = new ArrayList<>();
    private final List<WeakReference<Bindable<T>>> bindings = Collections.synchronizedList(new ArrayList<>());

    private WeakReference<Bindable<T>> weakReferenceInstance;

    /**
     * Creates a new bindable with a default value.
     * 
     * @param defaultValue The initial and default value for this bindable.
     */
    public Bindable(T defaultValue) {
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new bindable with null as the default value.
     */
    public Bindable() {
        this(null);
    }

    @Override
    public T getValue() {
        return value;
    }

    /**
     * Sets the current value of this bindable.
     * 
     * @param value The new value.
     * @throws IllegalStateException if the bindable is disabled.
     */
    public void setValue(T value) {
        if (disabled) {
            throw new IllegalStateException("Cannot set value to \"" + value + "\" as bindable is disabled.");
        }

        if (Objects.equals(this.value, value)) {
            return;
        }

        T previousValue = this.value;
        this.value = value;
        triggerValueChange(previousValue, this, true);
    }

    @Override
    public T getDefault() {
        return defaultValue;
    }

    /**
     * Sets the default value of this bindable.
     * 
     * @param defaultValue The new default value.
     * @throws IllegalStateException if the bindable is disabled.
     */
    public void setDefaultValue(T defaultValue) {
        if (disabled) {
            throw new IllegalStateException(
                    "Cannot set default value to \"" + defaultValue + "\" as bindable is disabled.");
        }

        if (Objects.equals(this.defaultValue, defaultValue)) {
            return;
        }

        this.defaultValue = defaultValue;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        if (this.disabled == disabled) {
            return;
        }

        this.disabled = disabled;
        triggerDisabledChange(this, true);
    }

    @Override
    public boolean isDefault() {
        return Objects.equals(value, defaultValue);
    }

    @Override
    public void setDefault() {
        setValue(defaultValue);
    }

    @Override
    public void bindTo(IBindable<T> them) {
        if (!(them instanceof Bindable)) {
            throw new IllegalArgumentException("Can only bind to Bindable instances");
        }

        Bindable<T> other = (Bindable<T>) them;

        // Check if already bound
        synchronized (bindings) {
            for (WeakReference<Bindable<T>> ref : bindings) {
                Bindable<T> bound = ref.get();
                if (bound == other) {
                    throw new IllegalArgumentException("An already bound bindable cannot be bound again.");
                }
            }
        }

        // Copy values from them to us
        other.copyTo(this);

        // Add weak references
        addWeakReference(other.getWeakReference());
        other.addWeakReference(getWeakReference());
    }

    /**
     * Copies all values and value limitations of this bindable to another.
     * 
     * @param them The target to copy to.
     */
    protected void copyTo(Bindable<T> them) {
        them.value = this.value;
        them.defaultValue = this.defaultValue;
        them.disabled = this.disabled;
    }

    @Override
    public void bindValueChanged(Consumer<ValueChangedEvent<T>> onChange, boolean runOnceImmediately) {
        valueChangedListeners.add(onChange);
        if (runOnceImmediately) {
            onChange.accept(new ValueChangedEvent<>(value, value));
        }
    }

    @Override
    public void bindDisabledChanged(Consumer<Boolean> onChange, boolean runOnceImmediately) {
        disabledChangedListeners.add(onChange);
        if (runOnceImmediately) {
            onChange.accept(disabled);
        }
    }

    protected void triggerValueChange(T previousValue, Bindable<T> source, boolean propagateToBindings) {
        T beforePropagation = value;

        if (propagateToBindings) {
            synchronized (bindings) {
                // Clean up dead references and propagate to live ones
                bindings.removeIf(ref -> {
                    Bindable<T> bound = ref.get();
                    if (bound == null)
                        return true; // Remove dead reference
                    if (bound == source)
                        return false; // Skip source

                    bound.value = value;
                    bound.triggerValueChange(previousValue, this, true);
                    return false;
                });
            }
        }

        if (Objects.equals(beforePropagation, value)) {
            ValueChangedEvent<T> event = new ValueChangedEvent<>(previousValue, value);
            for (Consumer<ValueChangedEvent<T>> listener : new ArrayList<>(valueChangedListeners)) {
                listener.accept(event);
            }
        }
    }

    protected void triggerDisabledChange(Bindable<T> source, boolean propagateToBindings) {
        boolean beforePropagation = disabled;

        if (propagateToBindings) {
            synchronized (bindings) {
                bindings.removeIf(ref -> {
                    Bindable<T> bound = ref.get();
                    if (bound == null)
                        return true;
                    if (bound == source)
                        return false;

                    bound.disabled = disabled;
                    bound.triggerDisabledChange(this, true);
                    return false;
                });
            }
        }

        if (beforePropagation == disabled) {
            for (Consumer<Boolean> listener : new ArrayList<>(disabledChangedListeners)) {
                listener.accept(disabled);
            }
        }
    }

    /**
     * Unbinds all event listeners.
     */
    public void unbindEvents() {
        valueChangedListeners.clear();
        disabledChangedListeners.clear();
    }

    /**
     * Unbinds all bound bindables.
     */
    public void unbindBindings() {
        synchronized (bindings) {
            List<Bindable<T>> toUnbind = new ArrayList<>();
            for (WeakReference<Bindable<T>> ref : bindings) {
                Bindable<T> bound = ref.get();
                if (bound != null) {
                    toUnbind.add(bound);
                }
            }

            for (Bindable<T> bound : toUnbind) {
                unbindFrom(bound);
            }
        }
    }

    @Override
    public void unbindAll() {
        unbindEvents();
        unbindBindings();
    }

    @Override
    public void unbindFrom(IUnbindable them) {
        if (!(them instanceof Bindable)) {
            throw new IllegalArgumentException("Can only unbind from Bindable instances");
        }

        Bindable<T> other = (Bindable<T>) them;
        removeWeakReference(other.getWeakReference());
        other.removeWeakReference(getWeakReference());
    }

    @Override
    public IBindable<T> getBoundCopy() {
        Bindable<T> copy = createInstance();
        copy.bindTo(this);
        return copy;
    }

    @Override
    public IBindable<T> getUnboundCopy() {
        Bindable<T> copy = createInstance();
        copyTo(copy);
        return copy;
    }

    /**
     * Creates a new instance of this bindable for copying.
     * 
     * @return A new bindable instance.
     */
    protected Bindable<T> createInstance() {
        return new Bindable<>();
    }

    private WeakReference<Bindable<T>> getWeakReference() {
        if (weakReferenceInstance == null) {
            weakReferenceInstance = new WeakReference<>(this);
        }
        return weakReferenceInstance;
    }

    private void addWeakReference(WeakReference<Bindable<T>> weakReference) {
        synchronized (bindings) {
            bindings.add(weakReference);
        }
    }

    private void removeWeakReference(WeakReference<Bindable<T>> weakReference) {
        synchronized (bindings) {
            bindings.remove(weakReference);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
