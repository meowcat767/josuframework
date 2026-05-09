package osu.framework.bindables;

/**
 * A bindable for float values with min/max/precision constraints.
 */
public class BindableFloat extends BindableNumber<Float> {
    private Float precision = Float.MIN_VALUE; // Epsilon

    public BindableFloat(float defaultValue) {
        super(defaultValue);
    }

    public BindableFloat() {
        super(0.0f);
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
            other.precision = this.precision;
        }
    }
}
