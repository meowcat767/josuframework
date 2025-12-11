package osu.framework.audio;

import osu.framework.bindables.IBindable;

/**
 * Provides aggregated adjustments for an audio component.
 */
public interface IAggregateAudioAdjustment {
    /**
     * The aggregate volume of this component (0..1).
     */
    IBindable<Double> getAggregateVolume();

    /**
     * The aggregate playback balance of this sample (-1..1 where 0 is centered).
     */
    IBindable<Double> getAggregateBalance();

    /**
     * The aggregate rate at which the component is played back (affects pitch).
     * 1 is 100% playback speed, or default frequency.
     */
    IBindable<Double> getAggregateFrequency();

    /**
     * The aggregate rate at which the component is played back (does not affect
     * pitch).
     * 1 is 100% playback speed.
     */
    IBindable<Double> getAggregateTempo();

    /**
     * Gets the aggregate bindable for a specific adjustable property type.
     * 
     * @param type The property type to get the aggregate for.
     * @return The aggregate bindable for the specified property.
     */
    default IBindable<Double> getAggregate(AdjustableProperty type) {
        switch (type) {
            case Volume:
                return getAggregateVolume();
            case Balance:
                return getAggregateBalance();
            case Frequency:
                return getAggregateFrequency();
            case Tempo:
                return getAggregateTempo();
            default:
                throw new IllegalArgumentException("Unknown AdjustableProperty: " + type);
        }
    }
}
