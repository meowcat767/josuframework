package osu.framework.lists;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

public final class SlimReadOnlyListWrapper<T>
        extends AbstractList<T> {

    private final List<T> list;

    public SlimReadOnlyListWrapper(
            List<T> list
    ) {
        this.list = Objects.requireNonNull(list);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    /*
     * Mutating operations intentionally unsupported.
     */

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }
}