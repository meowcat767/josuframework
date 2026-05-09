package osu.framework.bindables;

/**
 * A bindable for integer values with min/max constraints.
 */
public class BindableInt extends BindableNumber<Integer> {

    public BindableInt(int defaultValue) {
        super(defaultValue);
    }

    public BindableInt() {
        super(0);
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
    }
}
