package osu.framework.lists;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class WeakList<T> implements IWeakList<T>, Iterable<T> {

    private final List<WeakReference<T>> list = new ArrayList<>();

    @Override
    public void add(T item) {
        add(new WeakReference<>(Objects.requireNonNull(item)));
    }

    @Override
    public void add(WeakReference<T> weakReference) {
        list.add(Objects.requireNonNull(weakReference));
    }

    @Override
    public boolean remove(T item) {
        return list.removeIf(wr -> {
            T target = wr.get();
            return target == null || target.equals(item);
        });
    }

    @Override
    public boolean remove(WeakReference<T> weakReference) {
        return list.remove(weakReference);
    }

    @Override
    public void removeAt(int index) {
        list.remove(index);
    }

    @Override
    public boolean contains(T item) {
        for (WeakReference<T> wr : list) {
            if (Objects.equals(wr.get(), item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(WeakReference<T> weakReference) {
        return list.contains(weakReference);
    }

    @Override
    public void clear() {
        list.clear();
    }

    public int size() {
        return (int) stream().count();
    }

    public Stream<T> stream() {
        return list.stream()
                .map(WeakReference::get)
                .filter(Objects::nonNull);
    }

    @Override
    public Iterator<T> iterator() {
        return stream().iterator();
    }
}
