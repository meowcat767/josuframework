package osu.framework.audio;

import osu.framework.bindables.BindableNumber;
import osu.framework.bindables.IBindable;

/**
 * An audio component which allows for adjustments to be applied.
 */
public interface IAdjustableAudioComponent extends IAggregateAudioAdjustment {
    /**
     * The volume of this component.
     */
    BindableNumber<Double> getVolume();

    /**
     * The playback balance of this sample (-1..1 where 0 is centered).
     */
    BindableNumber<Double> getBalance();

    /**
     * Rate at which the component is played back (affects pitch).
     * 1 is 100% playback speed, or default frequency.
     */
    BindableNumber<Double> getFrequency();

    /**
     * Rate at which the component is played back (does not affect pitch).
     * 1 is 100% playback speed.
     */
    BindableNumber<Double> getTempo();

    /**
     * Bind all adjustments from an {@link IAggregateAudioAdjustment}.
     * 
     * @param component The adjustment source.
     */
    void bindAdjustments(IAggregateAudioAdjustment component);

    /**
     * Unbind all adjustments from an {@link IAggregateAudioAdjustment}.
     * 
     * @param component The adjustment source.
     */
    void unbindAdjustments(IAggregateAudioAdjustment component);

    /**
     * Add a bindable adjustment source.
     * 
     * @param type           The target type for this adjustment.
     * @param adjustBindable The bindable adjustment.
     */
    void addAdjustment(AdjustableProperty type, IBindable<Double> adjustBindable);

    /**
     * Remove a bindable adjustment source.
     * 
     * @param type           The target type for this adjustment.
     * @param adjustBindable The bindable adjustment.
     */
    void removeAdjustment(AdjustableProperty type, IBindable<Double> adjustBindable);

    /**
     * Removes all adjustments of a type.
     * 
     * @param type The target type to remove all adjustments of.
     */
    void removeAllAdjustments(AdjustableProperty type);
}
