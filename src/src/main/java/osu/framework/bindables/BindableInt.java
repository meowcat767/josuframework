package osu.framework.bindables;

/**
 * A bindable for integer values with min/max constraints.
 */
public class BindableInt extends Bindable<Integer> {
    private Integer minValue;
    private Integer maxValue;

    public BindableInt(int defaultValue) {
        super(defaultValue);
    }

    public BindableInt() {
        super(0);
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
        if (getValue() != null && minValue != null && getValue() < minValue) {
            setValue(minValue);
        }
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
        if (getValue() != null && maxValue != null && getValue() > maxValue) {
            setValue(maxValue);
        }
    }

    @Override
    public void setValue(Integer value) {
        if (value != null) {
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
    public void add(int value) {
        setValue(getValue() + value);
    }

    @Override
    protected Bindable<Integer> createInstance() {
        return new BindableInt();
    }

    @Override
    protected void copyTo(Bindable<Integer> them) {
        super.copyTo(them);
        if (them instanceof BindableInt) {
            BindableInt other = (BindableInt) them;
            other.minValue = this.minValue;
            other.maxValue = this.maxValue;
        }
    }
}
