package osu.framework.bindables;

/**
 * A bindable for float values with min/max/precision constraints.
 */
public class BindableFloat extends Bindable<Float> {
    private Float minValue;
    private Float maxValue;
    private Float precision = Float.MIN_VALUE; // Epsilon

    public BindableFloat(float defaultValue) {
        super(defaultValue);
    }

    public BindableFloat() {
        super(0.0f);
    }

    public Float getMinValue() {
        return minValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
        if (getValue() != null && minValue != null && getValue() < minValue) {
            setValue(minValue);
        }
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
        if (getValue() != null && maxValue != null && getValue() > maxValue) {
            setValue(maxValue);
        }
    }

    public Float getPrecision() {
        return precision;
    }

    public void setPrecision(Float precision) {
        if (precision != null && precision <= 0) {
            throw new IllegalArgumentException("Precision must be greater than 0");
        }
        this.precision = precision;
        if (getValue() != null) {
            setValue(getValue());
        }
    }

    @Override
    public void setValue(Float value) {
        if (value != null) {
            if (precision != null && precision > 0) {
                value = Math.round(value / precision) * precision;
            }

            if (minValue != null && value < minValue) {
                value = minValue;
            }
            if (maxValue != null && value > maxValue) {
                value = maxValue;
            }
        }
        super.setValue(value);
    }

    public void add(float value) {
        setValue(getValue() + value);
    }

    @Override
    protected Bindable<Float> createInstance() {
        return new BindableFloat();
    }

    @Override
    protected void copyTo(Bindable<Float> them) {
        super.copyTo(them);
        if (them instanceof BindableFloat) {
            BindableFloat other = (BindableFloat) them;
            other.minValue = this.minValue;
            other.maxValue = this.maxValue;
            other.precision = this.precision;
        }
    }
}
