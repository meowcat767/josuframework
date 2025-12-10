package osu.framework.bindables;

public class ValueChangedEvent<T> {
    public final T oldValue;
    public final T newValue;

    public ValueChangedEvent(T oldValue, T newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
