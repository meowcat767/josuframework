package osu.framework.allocation;

/**
 * Read-only interface into a dependency container capable of injecting and
 * retrieving dependencies based on types.
 */
public interface IReadOnlyDependencyContainer {
    /**
     * Retrieves a cached dependency of the specified type if it exists and null
     * otherwise.
     * 
     * @param type The dependency type to query for.
     * @return The requested dependency, or null if not found.
     */
    Object get(Class<?> type);

    /**
     * Retrieves a cached dependency of the specified type if it exists and null
     * otherwise.
     * 
     * @param type The dependency type to query for.
     * @param info Extra information that identifies the cached dependency.
     * @return The requested dependency, or null if not found.
     */
    Object get(Class<?> type, CacheInfo info);

    /**
     * Injects dependencies into the given instance.
     * 
     * @param instance The instance to inject dependencies into.
     * @param <T>      The type of the instance to inject dependencies into.
     */
    <T extends IDependencyInjectionCandidate> void inject(T instance);
}
