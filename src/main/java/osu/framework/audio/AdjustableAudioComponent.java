package osu.framework.audio;

import osu.framework.bindables.BindableNumber;
import osu.framework.bindables.IBindable;
import osu.framework.bindables.ValueChangedEvent;

/**
 * An audio component which allows for basic bindable adjustments to be applied.
 */
public class AdjustableAudioComponent extends AudioComponent implements IAdjustableAudioComponent {
    private static final Object ADJUSTMENTS_LOCK = new Object();

    private volatile AudioAdjustments adjustments;

    /**
     * Gets the adjustments for this audio component, creating them if necessary.
     */
    protected AudioAdjustments getAdjustments() {
        if (adjustments != null) {
            return adjustments;
        }

        synchronized (ADJUSTMENTS_LOCK) {
            if (adjustments != null) {
                return adjustments;
            }

            AudioAdjustments adj = new AudioAdjustments();

            // Bind to aggregate changes to invalidate state
            adj.getAggregateVolume().bindValueChanged(this::invalidateState, false);
            adj.getAggregateBalance().bindValueChanged(this::invalidateState, false);
            adj.getAggregateFrequency().bindValueChanged(this::invalidateState, false);
            adj.getAggregateTempo().bindValueChanged(this::invalidateState, false);

            adjustments = adj;
        }

        return adjustments;
    }

    @Override
    public BindableNumber<Double> getVolume() {
        return getAdjustments().getVolume();
    }

    @Override
    public BindableNumber<Double> getBalance() {
        return getAdjustments().getBalance();
    }

    @Override
    public BindableNumber<Double> getFrequency() {
        return getAdjustments().getFrequency();
    }

    @Override
    public BindableNumber<Double> getTempo() {
        return getAdjustments().getTempo();
    }

    @Override
    public void addAdjustment(AdjustableProperty type, IBindable<Double> adjustBindable) {
        getAdjustments().addAdjustment(type, adjustBindable);
    }

    @Override
    public void removeAdjustment(AdjustableProperty type, IBindable<Double> adjustBindable) {
        getAdjustments().removeAdjustment(type, adjustBindable);
    }

    @Override
    public void removeAllAdjustments(AdjustableProperty type) {
        getAdjustments().removeAllAdjustments(type);
    }

    private boolean invalidationPending = false;

    /**
     * Invalidates the state of this component, triggering a state change.
     */
    protected void invalidateState(ValueChangedEvent<Double> event) {
        if (canPerformInline()) {
            onStateChanged();
        } else {
            invalidationPending = true;
        }
    }

    /**
     * Called when the state of this component has changed.
     */
    protected void onStateChanged() {
        // Override in subclasses
    }

    @Override
    protected void updateState() {
        super.updateState();

        if (invalidationPending) {
            invalidationPending = false;
            onStateChanged();
        }
    }

    @Override
    public void bindAdjustments(IAggregateAudioAdjustment component) {
        getAdjustments().bindAdjustments(component);
    }

    @Override
    public void unbindAdjustments(IAggregateAudioAdjustment component) {
        if (adjustments != null) {
            adjustments.unbindAdjustments(component);
        }
    }

    @Override
    public IBindable<Double> getAggregateVolume() {
        return getAdjustments().getAggregateVolume();
    }

    @Override
    public IBindable<Double> getAggregateBalance() {
        return getAdjustments().getAggregateBalance();
    }

    @Override
    public IBindable<Double> getAggregateFrequency() {
        return getAdjustments().getAggregateFrequency();
    }

    @Override
    public IBindable<Double> getAggregateTempo() {
        return getAdjustments().getAggregateTempo();
    }

    @Override
    protected void dispose(boolean disposing) {
        super.dispose(disposing);

        if (adjustments != null) {
            getAggregateVolume().unbindAll();
            getAggregateBalance().unbindAll();
            getAggregateFrequency().unbindAll();
            getAggregateTempo().unbindAll();
        }
    }
}
