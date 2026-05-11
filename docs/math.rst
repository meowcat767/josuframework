josuFramework - Math
=====================

The ``osu.framework.math`` package contains utilities and data structures for mathematical operations and geometric representations.

Vector2
-------

``Vector2`` is a basic 2D vector structure using float coordinates. It is extensively used throughout the framework for positioning, sizing, and other spatial representations.

Properties:
~~~~~~~~~~~

- **x**: The horizontal component of the vector.
- **y**: The vertical component of the vector.

Usage Examples:
~~~~~~~~~~~~~~~

Creating a new vector:

.. code-block:: java

    Vector2 position = new Vector2(100.0f, 250.5f);
    Vector2 zero = new Vector2(); // Initializes to (0, 0)

Using with Drawables:

.. code-block:: java

    Box box = new Box();
    box.size = new Vector2(100, 100);
    box.position = new Vector2(50, 50);
