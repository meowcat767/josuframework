josuFramework - Lists
=====================

The ``osu.framework.lists`` package provides specialized collection types designed for specific framework needs, such as memory management, performance, and reactivity.

WeakList
--------

A ``WeakList<T>`` is a collection that holds weak references to its elements. It is primarily used for storing lists of listeners or observers where you want to avoid memory leaks caused by strong references.

- **Automatic Cleanup**: When an object in the list is garbage collected, it will eventually be removed from the list during iteration or when calling ``size()``.
- **Iterability**: Implements ``Iterable<T>``, allowing for easy iteration over the currently alive objects.
- **Thread Safety**: The base ``WeakList`` is not thread-safe. Use ``LockedWeakList`` for thread-safe operations.

.. code-block:: java

    WeakList<MyListener> listeners = new WeakList<>();
    
    // Add an item (stored as a WeakReference)
    listeners.add(myObject);
    
    // Iterate over only alive items
    for (MyListener listener : listeners) {
        listener.onEvent();
    }

SortedList
----------

A ``SortedList<T>`` maintains its elements in a sorted order. It uses binary search for efficient insertions and lookups.

- **Custom Ordering**: Can be initialized with a custom ``Comparator`` or a comparison function.
- **Efficient Insertion**: The ``add(T)`` method automatically finds the correct position to maintain order.
- **Search**: Provides ``binarySearch(T)`` and ``indexOf(T)`` which utilize the sorted nature of the list.

.. code-block:: java

    // List sorted by integer value
    SortedList<Integer> scores = new SortedList<>(Integer::compare);
    
    scores.add(100);
    scores.add(50);
    scores.add(150);
    
    // Resulting order: [50, 100, 150]
    System.out.println(scores.get(0)); // 50

ObservableArray
---------------

An ``ObservableArray<T>`` wraps a standard Java array and provides change notifications when elements are modified.

- **INotifyArrayChanged**: Implements this interface to allow subscribing to changes via ``addArrayElementChangedListener``.
- **Deep Observation**: If the elements themselves implement ``INotifyArrayChanged``, the ``ObservableArray`` will automatically subscribe to their changes and propagate them.
- **Fixed Size**: Like a standard array, its size cannot be changed after creation.

.. code-block:: java

    String[] data = {"A", "B", "C"};
    ObservableArray<String> observable = new ObservableArray<>(data);
    
    observable.addArrayElementChangedListener(() -> {
        System.out.println("Array changed!");
    });
    
    observable.set(0, "Z"); // Triggers listener

LazyList
--------

A ``LazyList<TSource, TTarget>`` provides a read-only view of a source list where elements are transformed on-demand.

- **Zero Allocation**: Does not create a new list; it applies a transformation function only when an element is accessed via ``get(index)``.
- **Efficiency**: Useful for mapping large lists where only a few elements are accessed at a time.

.. code-block:: java

    List<Integer> numbers = Arrays.asList(1, 2, 3);
    LazyList<Integer, String> strings = new LazyList<>(numbers, n -> "Value: " + n);
    
    System.out.println(strings.get(0)); // "Value: 1"
