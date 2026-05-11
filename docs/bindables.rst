josuFramework - Bindables
==============================

josuFramework utilizes ``Bindable<T>`` objects to distribute data between components. In conjunction with ``Drawable`` components,
they provide functionality to automatically remove communication between ``Bindable<T>`` objects when finalized.

Creating a Bindable<T>
-------------------------

In ``public``/``private``/``protected`` scenarios, it is recommended to store a private ``Bindable<T>`` and re-expose it publicly as one of the
read-only interfaces: ``IBindable`` or ``IBindable<T>``, depending on how much access the outside objects should have.

.. code-block:: java

    public class MyClassName {
        private final Bindable<Integer> someValue = new Bindable<>(10);

        public IBindable<Integer> someValue() {
            return someValue;
        }
    }

In ``private`` scenarios, it is simplest to store bindables as ``Bindable<T>``.

.. code-block:: java

    private final Bindable<Integer> privateBindable = new Bindable<>();

Specialized Bindables
---------------------

josuFramework provides specialized bindable classes for common types, which often include additional functionality (like min/max constraints for numbers).

- ``BindableInt``
- ``BindableDouble``
- ``BindableFloat``
- ``BindableLong``
- ``BindableBool``

Example with ``BindableInt``:

.. code-block:: java

    private final BindableInt health = new BindableInt(100);

    public void initialize() {
        health.setMinValue(0);
        health.setMaxValue(100);
    }

Binding
-------

Bindables can be "bound" to each other, creating a link where changes to one are reflected in the other.

Bi-directional Binding
~~~~~~~~~~~~~~~~~~~~~~

Use ``bindTo`` to create a bi-directional link.

.. code-block:: java

    Bindable<String> first = new Bindable<>("Initial");
    Bindable<String> second = new Bindable<>();

    second.bindTo(first);

    // Changing either will update both
    first.setValue("New Value");
    // second.getValue() is now "New Value"

Weakly Bound Copies
~~~~~~~~~~~~~~~~~~~

``getBoundCopy()`` is useful when you want to bind to a source but want the binding to be automatically removed when the copy is no longer referenced.

.. code-block:: java

    IBindable<Integer> copy = source.getBoundCopy();

Events
------

You can listen for changes in value or the disabled state of a bindable.

Value Changed
~~~~~~~~~~~~~

.. code-block:: java

    bindable.bindValueChanged(event -> {
        System.out.println("Value changed from " + event.getOldValue() + " to " + event.getNewValue());
    }, true); // The second argument determines if the callback should run immediately with the current value.

Disabled Changed
~~~~~~~~~~~~~~~~

.. code-block:: java

    bindable.bindDisabledChanged(disabled -> {
        System.out.println("Bindable is now " + (disabled ? "disabled" : "enabled"));
    }, false);

Unbinding
---------

To stop receiving updates or break links, you can use:

- ``unbindAll()``: Removes all bindings and event listeners.
- ``unbindFrom(other)``: Removes a specific binding.
- ``unbindEvents()``: Removes all event listeners but keeps bindings to other bindables.

Advanced Bindables
------------------

AggregateBindable
~~~~~~~~~~~~~~~~~

``AggregateBindable<T>`` allows combining multiple source bindables into a single result bindable using an aggregation function.

.. code-block:: java

    AggregateBindable<Double> aggregate = new AggregateBindable<>(
        (a, b) -> a * b, // Multiplication aggregation
        new BindableDouble(1.0) // Initial result
    );

    aggregate.addSource(source1);
    aggregate.addSource(source2);

    // aggregate.getResult().getValue() will now be source1 * source2