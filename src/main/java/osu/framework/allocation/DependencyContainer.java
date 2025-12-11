package osu.framework.allocation;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {
    private final Map<Class<?>, Object> cache = new HashMap<>();
    private final DependencyContainer parent;

    public DependencyContainer(DependencyContainer parent) {
        this.parent = parent;
    }

    public DependencyContainer() {
        this(null);
    }

    public <T> void cache(T instance) {
        cache.put(instance.getClass(), instance);
    }

    public <T> void cacheAs(Class<T> type, T instance) {
        cache.put(type, instance);
    }

    public <T> T get(Class<T> type) {
        Object val = cache.get(type);
        if (val != null) {
            return type.cast(val);
        }
        if (parent != null) {
            return parent.get(type);
        }
        return null;
    }

    public void inject(Object target) {
        DependencyActivator.activate(target, this);
    }
}
