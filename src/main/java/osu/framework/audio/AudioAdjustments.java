package osu.framework.audio;

import osu.framework.bindables.*;

import java.util.function.BiFunction;

public class AudioAdjustments implements IAdjustableAudioComponent {

    private static final AdjustableProperty[] ALL_ADJUSTMENTS =
            AdjustableProperty.values();

    /**
     * The volume of this component.
     */
    private final BindableDouble volume = new BindableDouble(1);

    /**
     * Playback balance (-1 to 1).
     */
    private final BindableDouble balance = new BindableDouble(0);

    /**
     * Playback frequency.
     */
    private final BindableDouble frequency = new BindableDouble(1);

    /**
     * Playback tempo.
     */
    private final BindableDouble tempo = new BindableDouble(1);

    {
        volume.setDefaultValue(1.0);
        volume.setMinValue(0.0);
        volume.setMaxValue(1.0);

        balance.setMinValue(-1.0);
        balance.setMaxValue(1.0);

        frequency.setDefaultValue(1.0);
        tempo.setDefaultValue(1.0);
    }

    public IBindable<Double> getAggregateVolume() {
        return volumeAggregate.getResult();
    }

    public IBindable<Double> getAggregateBalance() {
        return balanceAggregate.getResult();
    }

    public IBindable<Double> getAggregateFrequency() {
        return frequencyAggregate.getResult();
    }

    public IBindable<Double> getAggregateTempo() {
        return tempoAggregate.getResult();
    }

    private AggregateBindable<Double> volumeAggregate;
    private AggregateBindable<Double> balanceAggregate;
    private AggregateBindable<Double> frequencyAggregate;
    private AggregateBindable<Double> tempoAggregate;

    public AudioAdjustments() {
        for (AdjustableProperty type : ALL_ADJUSTMENTS) {
            AggregateBindable<Double> aggregate =
                    new AggregateBindable<>(
                            getAggregateFunction(type),
                            getProperty(type).getUnboundCopy()
                    );

            setAggregate(type, aggregate);

            aggregate.addSource(getProperty(type));
        }
    }

    public void addAdjustment(
            AdjustableProperty type,
            IBindable<Double> adjustBindable
    ) {
        getAggregateBindable(type).addSource(adjustBindable);
    }

    public void removeAdjustment(
            AdjustableProperty type,
            IBindable<Double> adjustBindable
    ) {
        getAggregateBindable(type).removeSource(adjustBindable);
    }

    public void bindAdjustments(IAggregateAudioAdjustment component) {
        for (AdjustableProperty type : ALL_ADJUSTMENTS) {
            getAggregateBindable(type)
                    .addSource(component.getAggregate(type));
        }
    }

    public void unbindAdjustments(IAggregateAudioAdjustment component) {
        for (AdjustableProperty type : ALL_ADJUSTMENTS) {
            getAggregateBindable(type)
                    .removeSource(component.getAggregate(type));
        }
    }

    public void removeAllAdjustments(AdjustableProperty type) {
        AggregateBindable<Double> aggregate = getAggregateBindable(type);

        aggregate.removeAllSources();
        aggregate.addSource(getProperty(type));
    }

    @Override
    public IBindable<Double> getAggregate(
            AdjustableProperty type
    ) {
        return getAggregateBindable(type).getResult();
    }

    private AggregateBindable<Double> getAggregateBindable(
            AdjustableProperty type
    ) {
        return switch (type) {
            case Balance -> balanceAggregate;
            case Frequency -> frequencyAggregate;
            case Volume -> volumeAggregate;
            case Tempo -> tempoAggregate;
        };
    }

    private void setAggregate(
            AdjustableProperty type,
            AggregateBindable<Double> aggregate
    ) {
        switch (type) {
            case Balance -> balanceAggregate = aggregate;
            case Frequency -> frequencyAggregate = aggregate;
            case Volume -> volumeAggregate = aggregate;
            case Tempo -> tempoAggregate = aggregate;
        }
    }

    private BindableDouble getProperty(AdjustableProperty type) {
        return switch (type) {
            case Balance -> balance;
            case Frequency -> frequency;
            case Volume -> volume;
            case Tempo -> tempo;
        };
    }

    private BiFunction<Double, Double, Double> getAggregateFunction(
            AdjustableProperty type
    ) {
        return switch (type) {
            case Balance -> Double::sum;
            default -> (a, b) -> a * b;
        };
    }

    public BindableNumber<Double> getVolume() {
        return volume;
    }

    public BindableNumber<Double> getBalance() {
        return balance;
    }

    public BindableNumber<Double> getFrequency() {
        return frequency;
    }

    public BindableNumber<Double> getTempo() {
        return tempo;
    }
}