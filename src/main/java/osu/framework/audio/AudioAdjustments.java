package osu.framework.audio;

import osu.framework.bindables.AggregateBindable;
import osu.framework.bindables.BindableDouble;
import osu.framework.bindables.BindableNumber;
import osu.framework.bindables.IBindable;

/**
 * Provides adjustable and bindable attributes for an audio component.
 * Aggregates results as an {@link IAggregateAudioAdjustment}.
 */
public class AudioAdjustments implements IAdjustableAudioComponent {
    private static final AdjustableProperty[] ALL_ADJUSTMENTS = AdjustableProperty.values();

    private final BindableNumber<Double> volume;
    private final BindableNumber<Double> balance;
    private final BindableNumber<Double> frequency;
    private final BindableNumber<Double> tempo;

    private AggregateBindable<Double> volumeAggregate;
    private AggregateBindable<Double> balanceAggregate;
    private AggregateBindable<Double> frequencyAggregate;
    private AggregateBindable<Double> tempoAggregate;

    public AudioAdjustments() {
        // Initialize volume (0..1, default 1)
        volume = new BindableDouble(1.0);
        volume.setDefault(1.0);
        volume.setMinValue(0.0);
        volume.setMaxValue(1.0);

        // Initialize balance (-1..1, default 0)
        balance = new BindableDouble(0.0);
        balance.setMinValue(-1.0);
        balance.setMaxValue(1.0);

        // Initialize frequency (default 1)
        frequency = new BindableDouble(1.0);
        frequency.setDefault(1.0);

        // Initialize tempo (default 1)
        tempo = new BindableDouble(1.0);
        tempo.setDefault(1.0);

        // Create aggregates for each property
        for (AdjustableProperty type : ALL_ADJUSTMENTS) {
            AggregateBindable<Double> aggregate = new AggregateBindable<>(
                    getAggregateFunction(type),
                    getProperty(type).getUnboundCopy());
            aggregate.addSource(getProperty(type));
            setAggregate(type, aggregate);
        }
    }

    @Override
    public BindableNumber<Double> getVolume() {
        return volume;
    }

    @Override
    public BindableNumber<Double> getBalance() {
        return balance;
    }

    @Override
    public BindableNumber<Double> getFrequency() {
        return frequency;
    }

    @Override
    public BindableNumber<Double> getTempo() {
        return tempo;
    }

    @Override
    public IBindable<Double> getAggregateVolume() {
        return volumeAggregate.getResult();
    }

    @Override
    public IBindable<Double> getAggregateBalance() {
        return balanceAggregate.getResult();
    }

    @Override
    public IBindable<Double> getAggregateFrequency() {
        return frequencyAggregate.getResult();
    }

    @Override
    public IBindable<Double> getAggregateTempo() {
        return tempoAggregate.getResult();
    }

    @Override
    public void addAdjustment(AdjustableProperty type, IBindable<Double> adjustBindable) {
        getAggregate(type).addSource(adjustBindable);
    }

    @Override
    public void removeAdjustment(AdjustableProperty type, IBindable<Double> adjustBindable) {
        getAggregate(type).removeSource(adjustBindable);
    }

    @Override
    public void bindAdjustments(IAggregateAudioAdjustment component) {
        for (AdjustableProperty type : ALL_ADJUSTMENTS) {
            getAggregate(type).addSource(component.getAggregate(type));
        }
    }

    @Override
    public void unbindAdjustments(IAggregateAudioAdjustment component) {
        for (AdjustableProperty type : ALL_ADJUSTMENTS) {
            getAggregate(type).removeSource(component.getAggregate(type));
        }
    }

    @Override
    public void removeAllAdjustments(AdjustableProperty type) {
        AggregateBindable<Double> aggregate = getAggregate(type);
        aggregate.removeAllSources();
        aggregate.addSource(getProperty(type));
    }

    private AggregateBindable<Double> getAggregate(AdjustableProperty type) {
        switch (type) {
            case Volume:
                return volumeAggregate;
            case Balance:
                return balanceAggregate;
            case Frequency:
                return frequencyAggregate;
            case Tempo:
                return tempoAggregate;
            default:
                throw new IllegalArgumentException("AdjustableProperty \"" + type + "\" is missing mapping");
        }
    }

    private void setAggregate(AdjustableProperty type, AggregateBindable<Double> aggregate) {
        switch (type) {
            case Volume:
                volumeAggregate = aggregate;
                break;
            case Balance:
                balanceAggregate = aggregate;
                break;
            case Frequency:
                frequencyAggregate = aggregate;
                break;
            case Tempo:
                tempoAggregate = aggregate;
                break;
            default:
                throw new IllegalArgumentException("AdjustableProperty \"" + type + "\" is missing mapping");
        }
    }

    private BindableNumber<Double> getProperty(AdjustableProperty type) {
        switch (type) {
            case Volume:
                return volume;
            case Balance:
                return balance;
            case Frequency:
                return frequency;
            case Tempo:
                return tempo;
            default:
                throw new IllegalArgumentException("AdjustableProperty \"" + type + "\" is missing mapping");
        }
    }

    private java.util.function.BiFunction<Double, Double, Double> getAggregateFunction(AdjustableProperty type) {
        switch (type) {
            case Balance:
                // Balance is additive
                return (a, b) -> a + b;
            default:
                // Volume, Frequency, Tempo are multiplicative
                return (a, b) -> a * b;
        }
    }
}
