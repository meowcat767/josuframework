josuFramework - Caching
=======================

The caching system in josuFramework provides a simple and lightweight way to track the validity of expensive calculations or state-dependent values. The core component is the ``Cached`` class (found in ``osu.framework.caching``).

.. note::
    This section refers to general value caching. For dependency injection caching, see :doc:`allocation`.

The Cached Class
----------------

The ``Cached`` class is essentially a state tracker that holds a boolean ``isValid`` flag. It is useful for scenarios where you only want to perform an operation (like layout calculations or resource loading) when something has changed.

Basic Usage
~~~~~~~~~~~

Typically, you store a ``Cached`` instance alongside the value you want to cache.

.. code-block:: java

    private final Cached layoutCache = new Cached();
    private float calculatedWidth;

    public float getWidth() {
        if (!layoutCache.isValid()) {
            calculatedWidth = performExpensiveCalculation();
            layoutCache.validate();
        }
        return calculatedWidth;
    }

    public void invalidateLayout() {
        layoutCache.invalidate();
    }

The ``ensureValid`` Helper
~~~~~~~~~~~~~~~~~~~~~~~~~~

To simplify the pattern above, you can use the ``ensureValid(Runnable)`` method.

.. code-block:: java

    public float getWidth() {
        layoutCache.ensureValid(() -> {
            calculatedWidth = performExpensiveCalculation();
        });
        return calculatedWidth;
    }

Methods
-------

- ``isValid()``: Returns whether the cache is currently valid.
- ``invalidate()``: Marks the cache as invalid (needs recalculation).
- ``validate()``: Marks the cache as valid (up-to-date).
- ``ensureValid(Runnable recalculate)``: Checks if the cache is valid; if not, runs the provided action and then validates the cache. Returns ``true`` if recalculation was performed.

Common Patterns
---------------

Invalidation on Change
~~~~~~~~~~~~~~~~~~~~~~

A common pattern is to invalidate a cache whenever a dependent property (like a ``Bindable``) changes.

.. code-block:: java

    private final Cached sizeCache = new Cached();

    public MyComponent() {
        someBindable.bindValueChanged(e -> sizeCache.invalidate());
    }
