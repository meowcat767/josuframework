package osu.framework.bindables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * A bindable which aggregates multiple source bindables using a combining
 * function.
 * 
 * @param <T> The type of value.
 */
public class AggregateBindable<T> implements IBindable<T> {
    private final BiFunction<T, T, T> aggregateFunction;
    private final IBindable<T> defaultSource;
    private final List<IBindable<T>> sources = new ArrayList<>();
    private final Bindable<T> result;

    public AggregateBindable(BiFunction<T, T, T> aggregateFunction, IBindable<T> defaultSource) {
        this.aggregateFunction = aggregateFunction;
        this.defaultSource = defaultSource;
        this.result = new Bindable<>(defaultSource.getValue());
        recalculate();
    }

    /**
     * Gets the result bindable which contains the aggregated value.
     */
    public IBindable<T> getResult() {
        return result;
    }

    /**
     * Adds a source bindable to aggregate.
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
        T aggregated = defaultSource.getValue();
        for (IBindable<T> source : sources) {
            if (source.getValue() != null) {
                aggregated = aggregateFunction.apply(aggregated, source.getValue());
            }
        }
        result.setValue(aggregated);
    }

    // IBindable implementation - delegate to result
    @Override
    public T getValue() {
        return result.getValue();
    }

    @Override
    public T getDefault() {
        return result.getDefault();
    }

    @Override
    public boolean isDisabled() {
        return result.isDisabled();
    }

    @Override
    public void bindValueChanged(Consumer<ValueChangedEvent<T>> onChange, boolean runOnceImmediately) {
        result.bindValueChanged(onChange, runOnceImmediately);
    }

    @Override
    public void bindDisabledChanged(Consumer<Boolean> onChange, boolean runOnceImmediately) {
        result.bindDisabledChanged(onChange, runOnceImmediately);
    }

    @Override
    public void unbindAll() {
        result.unbindAll();
    }

    @Override
    public IBindable<T> getUnboundCopy() {
        return result.getUnboundCopy();
    }
}
