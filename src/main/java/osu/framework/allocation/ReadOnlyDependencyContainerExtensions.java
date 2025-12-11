package osu.framework.allocation;

/**
 * Utility class providing extension methods for
 * {@link IReadOnlyDependencyContainer}.
 */
public class ReadOnlyDependencyContainerExtensions {
    /**
     * Retrieves a cached dependency of the specified type if it exists, and null
     * otherwise.
     * 
     * @param container The container to query.
     * @param type      The dependency type to query for.
     * @param <T>       The dependency type.
     * @return The requested dependency, or null if not found.
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(IReadOnlyDependencyContainer container, Class<T> type) {
        return get(container, type, new CacheInfo());
    }

    /**
     * Retrieves a cached dependency of the specified type if it exists, and null
     * otherwise.
     * 
     * @param container The container to query.
     * @param type      The dependency type to query for.
     * @param info      Extra information that identifies the cached dependency.
     * @param <T>       The dependency type.
     * @return The requested dependency, or null if not found.
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(IReadOnlyDependencyContainer container, Class<T> type, CacheInfo info) {
        return (T) container.get(type, info);
    }

    /**
     * Tries to retrieve a cached dependency of the specified type.
     * 
     * @param container The container to query.
     * @param type      The dependency type to query for.
     * @param <T>       The dependency type.
     * @return The requested dependency, or null if not found.
     */
    public static <T> T tryGet(IReadOnlyDependencyContainer container, Class<T> type) {
        return get(container, type);
    }

    /**
     * Tries to retrieve a cached dependency of the specified type.
     * 
     * @param container The container to query.
     * @param type      The dependency type to query for.
     * @param info      Extra information that identifies the cached dependency.
     * @param <T>       The dependency type.
     * @return The requested dependency, or null if not found.
     */
    public static <T> T tryGet(IReadOnlyDependencyContainer container, Class<T> type, CacheInfo info) {
        return get(container, type, info);
    }
}
