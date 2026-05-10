package osu.framework.lists;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LockedWeakList<T>
        implements IWeakList<T>, Iterable<T> {

    private final WeakList<T> list = new WeakList<>();

    @Override
    public void add(T item) {
        synchronized (list) {
            list.add(item);
        }
    }

    @Override
    public void add(WeakReference<T> weakReference) {
        synchronized (list) {
            list.add(weakReference);
        }
    }

    @Override
    public boolean remove(T item) {
        synchronized (list) {
            return list.remove(item);
        }
    }

    @Override
    public boolean remove(WeakReference<T> weakReference) {
        synchronized (list) {
            return list.remove(weakReference);
        }
    }

    @Override
    public void removeAt(int index) {
        synchronized (list) {
            list.removeAt(index);
        }
    }

    @Override
    public boolean contains(T item) {
        synchronized (list) {
            return list.contains(item);
        }
    }

    @Override
    public boolean contains(WeakReference<T> weakReference) {
        synchronized (list) {
            return list.contains(weakReference);
        }
    }

    @Override
    public void clear() {
        synchronized (list) {
            list.clear();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LockedIterator();
    }

    private class LockedIterator implements Iterator<T> {

        private final Iterator<T> iterator;

        private T next;

        public LockedIterator() {
            synchronized (list) {
                iterator = list.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            synchronized (list) {
                return iterator.hasNext();
            }
        }

        @Override
        public T next() {
            synchronized (list) {
                if (!iterator.hasNext()) {
                    throw new NoSuchElementException();
                }

                return iterator.next();
            }
        }
    }
}