package osu.framework.lists;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class SortedList<T> implements Iterable<T> {

    private final List<T> list;
    private final Comparator<T> comparer;

    private SortedList(Comparator<T> comparer, List<T> list) {
        this.comparer = comparer;
        this.list = list;
    }

    /**
     * Uses natural ordering.
     */
    public SortedList() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    /**
     * Uses custom comparison function.
     */
    public SortedList(BiFunction<T, T, Integer> comparerFunc) {
        this((Comparator<T>) (a, b) -> comparerFunc.apply(a, b));
    }

    /**
     * Uses custom comparator.
     */
    public SortedList(Comparator<T> comparer) {
        this.list = new ArrayList<>();
        this.comparer = Objects.requireNonNull(comparer);
    }

    public Comparator<T> getComparer() {
        return comparer;
    }

    public int size() {
        return list.size();
    }

    public T get(int index) {
        return list.get(index);
    }

    public void set(int index, T value) {
        list.set(index, value);
    }

    public void addRange(Iterable<T> collection) {
        for (T item : collection)
            add(item);
    }

    public void removeRange(int index, int count) {
        for (int i = 0; i < count; i++)
            list.remove(index);
    }

    /**
     * Adds while preserving sort order.
     *
     * @return index inserted at
     */
    public int add(T value) {
        Objects.requireNonNull(value);

        int index = Collections.binarySearch(list, value, comparer);

        if (index < 0)
            index = ~index;

        list.add(index, value);

        return index;
    }

    public boolean remove(T item) {
        int index = indexOf(item);

        if (index < 0)
            return false;

        removeAt(index);

        return true;
    }

    public void removeAt(int index) {
        list.remove(index);
    }

    public int removeAll(Predicate<T> match) {
        int before = list.size();

        list.removeIf(match);

        return before - list.size();
    }

    public void clear() {
        list.clear();
    }

    public boolean contains(T item) {
        return indexOf(item) >= 0;
    }

    public int binarySearch(T value) {
        return Collections.binarySearch(list, value, comparer);
    }

    public int indexOf(T value) {
        return binarySearch(value);
    }

    public void copyTo(T[] array, int arrayIndex) {
        for (int i = 0; i < list.size(); i++)
            array[arrayIndex + i] = list.get(i);
    }

    public T find(Predicate<T> match) {
        for (T item : list) {
            if (match.test(item))
                return item;
        }

        return null;
    }

    public List<T> findAll(Predicate<T> match) {
        List<T> results = new ArrayList<>();

        for (T item : list) {
            if (match.test(item))
                results.add(item);
        }

        return results;
    }

    public T findLast(Predicate<T> match) {
        for (int i = list.size() - 1; i >= 0; i--) {
            T item = list.get(i);

            if (match.test(item))
                return item;
        }

        return null;
    }

    public int findIndex(Predicate<T> match) {
        for (int i = 0; i < list.size(); i++) {
            if (match.test(list.get(i)))
                return i;
        }

        return -1;
    }

    /**
     * Re-sorts the list.
     */
    public void sort() {
        list.sort(comparer);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + size() + " items)";
    }
}