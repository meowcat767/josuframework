package osu.framework.bindables;

/**
 * A bindable for long values with min/max constraints.
 */
public class BindableLong extends Bindable<Long> {
    private Long minValue;
    private Long maxValue;

    public BindableLong(long defaultValue) {
        super(defaultValue);
    }

    public BindableLong() {
        super(0L);
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
        if (getValue() != null && minValue != null && getValue() < minValue) {
            setValue(minValue);
        }
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
        if (getValue() != null && maxValue != null && getValue() > maxValue) {
            setValue(maxValue);
        }
    }

    @Override
    public void setValue(Long value) {
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

    public void add(long value) {
        setValue(getValue() + value);
    }

    @Override
    protected Bindable<Long> createInstance() {
        return new BindableLong();
    }

    @Override
    protected void copyTo(Bindable<Long> them) {
        super.copyTo(them);
        if (them instanceof BindableLong) {
            BindableLong other = (BindableLong) them;
            other.minValue = this.minValue;
            other.maxValue = this.maxValue;
        }
    }
}
