package osu.framework.allocation;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {
    private final Map<Class<?>, Object> cache = new HashMap<>();

    public <T> void cache(T instance) {
        cache.put(instance.getClass(), instance);
    }

    public <T> void cacheAs(Class<T> type, T instance) {
        cache.put(type, instance);
    }

    public <T> T get(Class<T> type) {
        return type.cast(cache.get(type));
    }
}
