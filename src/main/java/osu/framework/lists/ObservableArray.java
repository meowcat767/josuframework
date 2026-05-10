package osu.framework.lists;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableArray<T>
        extends AbstractList<T>
        implements INotifyArrayChanged {

    private final T[] wrappedArray;

    private final List<Runnable> listeners =
            new CopyOnWriteArrayList<>();

    public ObservableArray(T[] arrayToWrap) {
        this.wrappedArray =
                Objects.requireNonNull(arrayToWrap);
    }

    @Override
    public T get(int index) {
        return wrappedArray[index];
    }

    @Override
    public T set(int index, T value) {

        T previousValue = wrappedArray[index];

        if (Objects.equals(previousValue, value)) {
            return previousValue;
        }

        // unsubscribe old nested notifier
        if (previousValue instanceof INotifyArrayChanged previousNotifier) {
            previousNotifier.removeArrayElementChangedListener(
                    this::onArrayElementChanged
            );
        }

        wrappedArray[index] = value;

        // subscribe new nested notifier
        if (value instanceof INotifyArrayChanged notifier) {
            notifier.addArrayElementChangedListener(
                    this::onArrayElementChanged
            );
        }

        onArrayElementChanged();

        return previousValue;
    }

    @Override
    public int size() {
        return wrappedArray.length;
    }

    protected void onArrayElementChanged() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    @Override
    public void addArrayElementChangedListener(
            Runnable listener
    ) {
        listeners.add(listener);
    }

    @Override
    public void removeArrayElementChangedListener(
            Runnable listener
    ) {
        listeners.remove(listener);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ObservableArray<?> other)) {
            return false;
        }

        // matches C# reference equality semantics
        return wrappedArray == other.wrappedArray;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(wrappedArray);
    }
}