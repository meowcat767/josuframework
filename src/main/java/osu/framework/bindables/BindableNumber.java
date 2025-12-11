package osu.framework.bindables;

/**
 * A bindable for numeric values with min/max constraints.
 * 
 * @param <T> The numeric type (should be Double, Integer, Float, etc.)
 */
public class BindableNumber<T extends Number> extends Bindable<T> {
    private T minValue;
    private T maxValue;

    public BindableNumber(T defaultValue) {
        super(defaultValue);
    }

    public BindableNumber() {
        super(null);
    }

    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
        // Could add validation here to clamp current value
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
        // Could add validation here to clamp current value
    }

    @Override
    public void setValue(T value) {
        // Apply clamping if min/max are set
        T clampedValue = value;
        if (minValue != null && value != null && value.doubleValue() < minValue.doubleValue()) {
            clampedValue = minValue;
        }
        if (maxValue != null && value != null && value.doubleValue() > maxValue.doubleValue()) {
            clampedValue = maxValue;
        }
        super.setValue(clampedValue);
    }

    @Override
    public IBindable<T> getUnboundCopy() {
        BindableNumber<T> copy = new BindableNumber<>(this.getValue());
        copy.setMinValue(this.minValue);
        copy.setMaxValue(this.maxValue);
        return copy;
    }
}
