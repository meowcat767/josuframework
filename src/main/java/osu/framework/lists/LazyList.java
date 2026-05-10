package osu.framework.lists;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;

public class LazyList<TSource, TTarget> extends AbstractList<TTarget> {

    private final List<TSource> source;
    private final Function<TSource, TTarget> map;

    /**
     * Constructs a lazy list from a source list and transform function.
     */
    public LazyList(List<TSource> source, Function<TSource, TTarget> map) {
        this.source = source;
        this.map = map;
    }

    /**
     * Gets and lazily transforms an element.
     */
    @Override
    public TTarget get(int index) {
        return map.apply(source.get(index));
    }

    /**
     * Gets and lazily transforms an element.
     */
    @Override
    public int size() {
        return source.size();
    }
}
