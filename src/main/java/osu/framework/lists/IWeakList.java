package osu.framework.lists;

import java.lang.ref.WeakReference;

public interface IWeakList<T> {

    /**
     * Adds an item to this list as a weak reference.
     */
    void add(T item);

    /**
     * Adds an existing weak reference to this list.
     */
    void add(WeakReference<T> weakReference);

    /**
     * Removes an item from this list.
     *
     * @return whether the item was removed
     */
    boolean remove(T item);

    /**
     * Removes a weak reference from this list.
     *
     * @return whether the weak reference was removed
     */
    boolean remove(WeakReference<T> weakReference);

    /**
     * Removes an item at an index.
     */
    void removeAt(int index);

    /**
     * Checks whether an item is alive and present.
     */
    boolean contains(T item);

    /**
     * Checks whether a weak reference exists.
     */
    boolean contains(WeakReference<T> weakReference);

    /**
     * Clears all items.
     */
    void clear();
}