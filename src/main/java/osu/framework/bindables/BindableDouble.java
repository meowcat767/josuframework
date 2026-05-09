package osu.framework.bindables;

/**
 * A bindable for double values with min/max/precision constraints.
 */
public class BindableDouble extends BindableNumber<Double> {
    private Double precision = Double.MIN_VALUE; // Epsilon

    public BindableDouble(double defaultValue) {
        super(defaultValue);
    }

    public BindableDouble() {
        super(0.0);
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
            other.precision = this.precision;
        }
    }
}
