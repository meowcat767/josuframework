package osu.framework.bindables;

/**
 * A bindable for numeric values with min/max constraints.
 * 
 * @param <T> The numeric type (should be Double, Integer, Float, etc.)
 */
public class BindableNumber<T extends Number> extends Bindable<T> {
    private T minValue;
    private T maxValue;
    private T defaultValue;


    public BindableNumber(T defaultValue) {
        super(defaultValue);
    }

    public BindableNumber() {
        super(null);
    }

    public T getMinValue() {
        return minValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public T get() {
        return getValue();
    }

    public void set(T value) {
        setValue(value);
    }


    public void setMinValue(T minValue) {
        this.minValue = minValue;
        if (getValue() != null && minValue != null && getValue().doubleValue() < minValue.doubleValue()) {
            setValue(minValue);
        }
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
        if (getValue() != null && maxValue != null && getValue().doubleValue() > maxValue.doubleValue()) {
            setValue(maxValue);
        }
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
