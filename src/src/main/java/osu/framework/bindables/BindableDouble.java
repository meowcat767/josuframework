package osu.framework.bindables;

/**
 * A bindable for double values with min/max/precision constraints.
 */
public class BindableDouble extends Bindable<Double> {
    private Double minValue;
    private Double maxValue;
    private Double precision = Double.MIN_VALUE; // Epsilon

    public BindableDouble(double defaultValue) {
        super(defaultValue);
    }

    public BindableDouble() {
        super(0.0);
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
        if (getValue() != null && minValue != null && getValue() < minValue) {
            setValue(minValue);
        }
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
        if (getValue() != null && maxValue != null && getValue() > maxValue) {
            setValue(maxValue);
        }
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        if (precision != null && precision <= 0) {
            throw new IllegalArgumentException("Precision must be greater than 0");
        }
        this.precision = precision;
        // Re-apply current value with new precision
        if (getValue() != null) {
            setValue(getValue());
        }
    }

    @Override
    public void setValue(Double value) {
        if (value != null) {
            // Apply precision rounding
            if (precision != null && precision > 0) {
                value = Math.round(value / precision) * precision;
            }

            // Apply clamping
            if (minValue != null && value < minValue) {
                value = minValue;
            }
            if (maxValue != null && value > maxValue) {
                value = maxValue;
            }
        }
        super.setValue(value);
    }

    /**
     * Adds a value to the current value.
     */
    public void add(double value) {
        setValue(getValue() + value);
    }

    @Override
    protected Bindable<Double> createInstance() {
        return new BindableDouble();
    }

    @Override
    protected void copyTo(Bindable<Double> them) {
        super.copyTo(them);
        if (them instanceof BindableDouble) {
            BindableDouble other = (BindableDouble) them;
            other.minValue = this.minValue;
            other.maxValue = this.maxValue;
            other.precision = this.precision;
        }
    }
}
