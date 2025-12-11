package osu.framework.allocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DependencyContainer implements IReadOnlyDependencyContainer {
    private final Map<CacheKey, Object> cache = new HashMap<>();
    private final DependencyContainer parent;

    public DependencyContainer(DependencyContainer parent) {
        this.parent = parent;
    }

    public DependencyContainer() {
        this(null);
    }

    public <T> void cache(T instance) {
        cache.put(new CacheKey(instance.getClass(), new CacheInfo()), instance);
    }

    public <T> void cacheAs(Class<T> type, T instance) {
        cache.put(new CacheKey(type, new CacheInfo()), instance);
    }

    public <T> void cacheAs(Class<T> type, T instance, CacheInfo info) {
        cache.put(new CacheKey(type, info), instance);
    }

    @Override
    public Object get(Class<?> type) {
        return get(type, new CacheInfo());
    }

    @Override
    public Object get(Class<?> type, CacheInfo info) {
        CacheKey key = new CacheKey(type, info);
        Object val = cache.get(key);
        if (val != null) {
            return val;
        }
        if (parent != null) {
            return parent.get(type, info);
        }
        return null;
    }

    @Override
    public <T extends IDependencyInjectionCandidate> void inject(T instance) {
        DependencyActivator.activate(instance, this);
    }

    /**
     * Internal key class for cache lookups combining type and CacheInfo.
     */
    private static class CacheKey {
        private final Class<?> type;
        private final CacheInfo info;

        public CacheKey(Class<?> type, CacheInfo info) {
            this.type = type;
            this.info = info != null ? info : new CacheInfo();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof CacheKey))
                return false;
            CacheKey other = (CacheKey) obj;
            return Objects.equals(type, other.type) && Objects.equals(info, other.info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, info);
        }
    }
}
