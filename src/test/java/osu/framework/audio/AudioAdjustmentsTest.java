package osu.framework.audio;

import org.junit.jupiter.api.Test;
import osu.framework.audio.*;
import osu.framework.bindables.BindableDouble;

import static org.junit.jupiter.api.Assertions.*;

class AudioAdjustmentsTest {

    private static final double EPSILON = 0.00001;

    @Test
    void defaultValuesAreCorrect() {
        AudioAdjustments adjustments = new AudioAdjustments();

        assertEquals(1.0, adjustments.getVolume().getValue(), EPSILON);
        assertEquals(0.0, adjustments.getBalance().getValue(), EPSILON);
        assertEquals(1.0, adjustments.getFrequency().getValue(), EPSILON);
        assertEquals(1.0, adjustments.getTempo().getValue(), EPSILON);
    }

    @Test
    void volumeAggregatesMultiplicatively() {
        AudioAdjustments adjustments = new AudioAdjustments();

        BindableDouble modifier = new BindableDouble(0.5);

        adjustments.addAdjustment(
                AdjustableProperty.Volume,
                modifier
        );

        assertEquals(
                0.5,
                adjustments.getAggregateVolume().getValue(),
                EPSILON
        );
    }

    @Test
    void balanceAggregatesAdditively() {
        AudioAdjustments adjustments = new AudioAdjustments();

        BindableDouble modifier = new BindableDouble(0.25);

        adjustments.addAdjustment(
                AdjustableProperty.Balance,
                modifier
        );

        assertEquals(
                0.25,
                adjustments.getAggregateBalance().getValue(),
                EPSILON
        );
    }

    @Test
    void multipleVolumeAdjustmentsMultiplyTogether() {
        AudioAdjustments adjustments = new AudioAdjustments();

        adjustments.addAdjustment(
                AdjustableProperty.Volume,
                new BindableDouble(0.5)
        );

        adjustments.addAdjustment(
                AdjustableProperty.Volume,
                new BindableDouble(0.5)
        );

        assertEquals(
                0.25,
                adjustments.getAggregateVolume().getValue(),
                EPSILON
        );
    }

    @Test
    void removingAdjustmentRestoresValue() {
        AudioAdjustments adjustments = new AudioAdjustments();

        BindableDouble modifier = new BindableDouble(0.5);

        adjustments.addAdjustment(
                AdjustableProperty.Volume,
                modifier
        );

        assertEquals(
                0.5,
                adjustments.getAggregateVolume().getValue(),
                EPSILON
        );

        adjustments.removeAdjustment(
                AdjustableProperty.Volume,
                modifier
        );

        assertEquals(
                1.0,
                adjustments.getAggregateVolume().getValue(),
                EPSILON
        );
    }

    @Test
    void removeAllAdjustmentsRestoresBaseProperty() {
        AudioAdjustments adjustments = new AudioAdjustments();

        adjustments.addAdjustment(
                AdjustableProperty.Volume,
                new BindableDouble(0.5)
        );

        adjustments.removeAllAdjustments(
                AdjustableProperty.Volume
        );

        assertEquals(
                1.0,
                adjustments.getAggregateVolume().getValue(),
                EPSILON
        );
    }

    @Test
    void aggregateUpdatesWhenBindableChanges() {
        AudioAdjustments adjustments = new AudioAdjustments();

        BindableDouble modifier = new BindableDouble(0.5);

        adjustments.addAdjustment(
                AdjustableProperty.Volume,
                modifier
        );

        assertEquals(
                0.5,
                adjustments.getAggregateVolume().getValue(),
                EPSILON
        );

        modifier.setValue(0.25);

        assertEquals(
                0.25,
                adjustments.getAggregateVolume().getValue(),
                EPSILON
        );
    }

    @Test
    void bindAdjustmentsPropagatesValues() {
        AudioAdjustments parent = new AudioAdjustments();
        AudioAdjustments child = new AudioAdjustments();

        parent.getVolume().setValue(0.5);

        child.bindAdjustments(parent);

        assertEquals(
                0.5,
                child.getAggregateVolume().getValue(),
                EPSILON
        );
    }

    @Test
    void unbindAdjustmentsStopsPropagation() {
        AudioAdjustments parent = new AudioAdjustments();
        AudioAdjustments child = new AudioAdjustments();

        child.bindAdjustments(parent);

        parent.getVolume().setValue(0.5);

        assertEquals(
                0.5,
                child.getAggregateVolume().getValue(),
                EPSILON
        );

        child.unbindAdjustments(parent);

        parent.getVolume().setValue(0.25);

        assertEquals(
                1.0,
                child.getAggregateVolume().getValue(),
                EPSILON
        );
    }
}