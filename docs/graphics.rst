josuFramework - Graphics
==========================

The graphics system in josuFramework is built around a hierarchical scene graph of ``Drawable`` objects. It provides a flexible way to manage layout, rendering, and animations.

Drawable
--------

``Drawable`` is the base class for all visual elements in the framework. It handles basic properties like position, size, rotation, and color.

Core Properties:
~~~~~~~~~~~~~~~~

- **Anchor**: Defines where the drawable is positioned relative to its parent (e.g., ``TopLeft``, ``Centre``).
- **Origin**: Defines which point of the drawable is aligned with its position (e.g., ``TopLeft``, ``Centre``).
- **Size**: A ``Vector2`` representing the dimensions of the drawable.
- **Colour**: A ``Color4`` object representing the color and transparency.
- **Rotation**: The rotation angle in degrees.
- **Depth**: Used for sorting drawables within the same parent (lower depth values are drawn on top).

Life Cycle:
~~~~~~~~~~~

1. **Instantiation**: Creating the object.
2. **Load**: The ``load(DependencyContainer)`` method is called to resolve dependencies and perform initialization.
3. **Update**: The ``Update()`` method is called every frame to update state and animations.
4. **Draw**: The ``Draw()`` method is called every frame to render the object.

Containers
----------

Containers are used to group and manage multiple drawables.

CompositeDrawable
~~~~~~~~~~~~~~~~~

``CompositeDrawable`` is a drawable that can contain other drawables (internal children). It handles propagating ``Update`` and ``Draw`` calls to its children.

Container
~~~~~~~~~

``Container`` is a public-facing version of ``CompositeDrawable`` that allows adding and removing children via ``add(Drawable)``, ``remove(Drawable)``, and ``clear()``.

.. code-block:: java

    Container container = new Container();
    container.add(new Box());
    container.add(new MyCustomDrawable());

Shapes
------

The framework provides basic primitive shapes.

Box
~~~

``Box`` is a simple rectangular shape that fills its allocated size with a solid color.

.. code-block:: java

    Box box = new Box();
    box.size = new Vector2(100, 100);
    box.colour = Color4.Tomato;
    box.anchor = Anchor.Centre;
    box.origin = Anchor.Centre;

Positioning and Anchors
-----------------------

Anchoring allows for responsive layouts by positioning elements relative to their parent's dimensions.

- **Anchor.TopLeft**: (0, 0) in parent coordinates.
- **Anchor.Centre**: (0.5, 0.5) in parent coordinates.

Transforms and Animations
-------------------------

Drawables inherit from ``Transformable``, which allows for smoothly transitioning properties over time using transforms.

.. code-block:: java

    // Rotates the box to 45 degrees over 1000ms using Easing.Out
    box.rotateTo(45, 1000, Easing.Out);

All transforms are updated during the ``Update()`` phase.

Colors
------

The ``Color4`` class represents colors using four float components (Red, Green, Blue, Alpha) in the range 0.0 to 1.0.

.. code-block:: java

    Color4 myColor = new Color4(1.0f, 0.5f, 0.0f, 1.0f); // Opaque Orange
    box.colour = Color4.White;
