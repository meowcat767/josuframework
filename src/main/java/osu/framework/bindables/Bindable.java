package osu.framework.bindables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Bindable<T> {
    private T value;
    private T defaultValue;
    private boolean disabled;

    private final List<Consumer<ValueChangedEvent<T>>> valueChangedListeners = new ArrayList<>();
    private final List<Consumer<Boolean>> disabledChangedListeners = new ArrayList<>();
    private final List<Bindable<T>> bindings = new ArrayList<>();

    public Bindable(T defaultValue) {
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    public Bindable() {
        this(null);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (disabled) {
            throw new IllegalStateException("Cannot set value of disabled bindable");
        }
        if (Objects.equals(this.value, value)) {
            return;
        }
        setValueInternal(this.value, value, this);
    }

    private void setValueInternal(T previousValue, T newValue, Bindable<T> source) {
        this.value = newValue;
        triggerValueChange(previousValue, newValue, source);
    }

    public T getDefault() {
        return defaultValue;
    }

    public void setDefault(T defaultValue) {
        if (disabled) {
            throw new IllegalStateException("Cannot set default value of disabled bindable");
        }
        if (Objects.equals(this.defaultValue, defaultValue)) {
            return;
        }
        this.defaultValue = defaultValue;
        // Trigger default change if needed, skipping for now as it's less critical
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        if (this.disabled == disabled) {
            return;
        }
        this.disabled = disabled;
        triggerDisabledChange(disabled, this);
    }

    public void bindTo(Bindable<T> them) {
        if (bindings.contains(them)) {
            throw new IllegalArgumentException("Already bound");
        }

        this.value = them.getValue();
        this.defaultValue = them.getDefault();
        this.disabled = them.isDisabled();

        this.bindings.add(them);
        them.addBinding(this);
    }

    protected void addBinding(Bindable<T> other) {
        this.bindings.add(other);
    }

    public void bindValueChanged(Consumer<ValueChangedEvent<T>> onChange, boolean runOnceImmediately) {
        valueChangedListeners.add(onChange);
        if (runOnceImmediately) {
            onChange.accept(new ValueChangedEvent<>(value, value));
        }
    }

    public void bindDisabledChanged(Consumer<Boolean> onChange, boolean runOnceImmediately) {
        disabledChangedListeners.add(onChange);
        if (runOnceImmediately) {
            onChange.accept(disabled);
        }
    }

    private void triggerValueChange(T previousValue, T newValue, Bindable<T> source) {
        for (Bindable<T> b : bindings) {
            if (b != source) {
                b.setValueInternal(previousValue, newValue, this);
            }
        }

        ValueChangedEvent<T> event = new ValueChangedEvent<>(previousValue, newValue);
        for (Consumer<ValueChangedEvent<T>> listener : valueChangedListeners) {
            listener.accept(event);
        }
    }

    private void triggerDisabledChange(boolean newDisabled, Bindable<T> source) {
        for (Bindable<T> b : bindings) {
            if (b != source) {
                b.setDisabledInternal(newDisabled, this);
            }
        }

        for (Consumer<Boolean> listener : disabledChangedListeners) {
            listener.accept(newDisabled);
        }
    }

    private void setDisabledInternal(boolean newDisabled, Bindable<T> source) {
        this.disabled = newDisabled;
        triggerDisabledChange(newDisabled, source);
    }
}
