package osu.framework.lists;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class SlimReadOnlyDictionaryWrapper<TKey, TValue> {

    private final Map<TKey, TValue> dict;

    public SlimReadOnlyDictionaryWrapper(
            Map<TKey, TValue> dict
    ) {
        this.dict = Objects.requireNonNull(dict);
    }

    public boolean containsKey(TKey key) {
        return dict.containsKey(key);
    }

    public TValue get(TKey key) {
        return dict.get(key);
    }

    public Set<TKey> keys() {
        return Collections.unmodifiableSet(dict.keySet());
    }

    public Collection<TValue> values() {
        return Collections.unmodifiableCollection(
                dict.values()
        );
    }

    public Set<Map.Entry<TKey, TValue>> entrySet() {
        return Collections.unmodifiableSet(
                dict.entrySet()
        );
    }

    public int size() {
        return dict.size();
    }

    public boolean tryGetValue(
            TKey key,
            Holder<TValue> value
    ) {

        if (!dict.containsKey(key)) {
            value.value = null;
            return false;
        }

        value.value = dict.get(key);
        return true;
    }

    public static class Holder<T> {
        public T value;
    }
}