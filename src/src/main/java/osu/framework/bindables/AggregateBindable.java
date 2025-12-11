package osu.framework.bindables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * A bindable which aggregates multiple source bindables using a combining
 * function.
 * 
 * @param <T> The type of value.
 */
public class AggregateBindable<T> {
    private final BiFunction<T, T, T> aggregateFunction;
    private final Bindable<T> result;
    private final List<IBindable<T>> sources = new ArrayList<>();

    /**
     * Creates a new aggregate bindable.
     * 
     * @param aggregateFunction The function to combine values (e.g., (a, b) -> a *
     *                          b for multiplication).
     * @param initialValue      The initial value for the result.
     */
    public AggregateBindable(BiFunction<T, T, T> aggregateFunction, T initialValue) {
        this.aggregateFunction = aggregateFunction;
        this.result = new Bindable<>(initialValue);
    }

    /**
     * Gets the result bindable which contains the aggregated value.
     */
    public IBindable<T> getResult() {
        return result;
    }

    /**
     * Adds a source bindable to aggregate.
     * 
     * @param source The source to add.
     */
    public void addSource(IBindable<T> source) {
        if (sources.contains(source)) {
            return;
        }

        sources.add(source);
        source.bindValueChanged(e -> recalculate(), false);
        recalculate();
    }

    /**
     * Removes a source bindable.
     * 
     * @param source The source to remove.
     */
    public void removeSource(IBindable<T> source) {
        if (sources.remove(source)) {
            recalculate();
        }
    }

    /**
     * Removes all source bindables.
     */
    public void removeAllSources() {
        sources.clear();
        recalculate();
    }

    private void recalculate() {
        if (sources.isEmpty()) {
            result.setValue(result.getDefault());
            return;
        }

        T aggregated = sources.get(0).getValue();
        for (int i = 1; i < sources.size(); i++) {
            T sourceValue = sources.get(i).getValue();
            if (sourceValue != null) {
                aggregated = aggregateFunction.apply(aggregated, sourceValue);
            }
        }

        result.setValue(aggregated);
    }
}
