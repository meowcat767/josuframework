josuFramework - Allocation
==========================

The allocation system in josuFramework provides a robust dependency injection (DI) framework, inspired by the osu!framework. It allows components to share and resolve dependencies through a hierarchical container system.

Dependency Container
--------------------

The core of the system is the ``DependencyContainer`` (and its read-only interface ``IReadOnlyDependencyContainer``). Containers store instances of objects mapped to their class types.

Creating and Nesting Containers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Containers can be nested. If a dependency cannot be found in the current container, it will look in the parent container.

.. code-block:: java

    DependencyContainer parentContainer = new DependencyContainer();
    parentContainer.cache(new MyGlobalService());

    DependencyContainer childContainer = new DependencyContainer(parentContainer);
    // childContainer can resolve MyGlobalService from parentContainer

Caching Dependencies
--------------------

To make an object available for injection, it must be "cached" in a container.

Manual Caching
~~~~~~~~~~~~~~

You can manually cache objects using the ``cache`` or ``cacheAs`` methods.

.. code-block:: java

    DependencyContainer dependencies = new DependencyContainer();
    
    // Cache by its own class
    dependencies.cache(new MyService());
    
    // Cache as a specific interface or superclass
    dependencies.cacheAs(IService.class, new MyServiceImpl());

Caching with CacheInfo
~~~~~~~~~~~~~~~~~~~~~~

For more granular control, you can use ``CacheInfo`` to differentiate between multiple instances of the same type (e.g., by name).

.. code-block:: java

    dependencies.cacheAs(String.class, "MainTitle", new CacheInfo("Header"));
    dependencies.cacheAs(String.class, "SubTitle", new CacheInfo("Footer"));

Resolving Dependencies
----------------------

There are two primary ways to resolve (inject) dependencies into an object. The object must implement ``IDependencyInjectionCandidate``.

Field Injection (``@Resolved``)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Fields annotated with ``@Resolved`` are automatically populated when ``dependencies.inject(this)`` or ``DependencyActivator.activate(this, dependencies)`` is called.

.. code-block:: java

    public class MyComponent implements IDependencyInjectionCandidate {
        @Resolved
        private MyService service;

        @Resolved(canBeNull = true)
        private OptionalService optional;
    }

Method Injection (``@BackgroundDependencyLoader``)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Methods annotated with ``@BackgroundDependencyLoader`` are called during the injection process. This is the preferred way for complex initialization that requires multiple dependencies.

.. code-block:: java

    public class MyComponent implements IDependencyInjectionCandidate {
        private MyService service;

        @BackgroundDependencyLoader
        private void load(MyService service, AnotherService another) {
            this.service = service;
            // Perform initialization using dependencies
        }
    }

The Injection Process
---------------------

In josuFramework, injection is typically triggered by the ``Drawable`` load process, but it can be manually invoked:

.. code-block:: java

    DependencyContainer dependencies = getDependencies();
    MyComponent component = new MyComponent();
    dependencies.inject(component); // Populates @Resolved fields and calls @BackgroundDependencyLoader methods

Summary of Annotations
----------------------

- **@Resolved**: Marks a field to be injected.
- **@BackgroundDependencyLoader**: Marks a method to be called with injected parameters.
- **@Cached**: (Advanced) Can be used to mark fields or types that should be automatically cached (usage depends on specific container implementations).

.. note::
    While `@Cached` exists in the codebase, the primary way to cache dependencies in the current implementation of `DependencyContainer` is through manual calls to `cache()` and `cacheAs()`.
