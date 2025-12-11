package osu.framework.bindables;

/**
 * A bindable for boolean values.
 */
public class BindableBool extends Bindable<Boolean> {
    public BindableBool(boolean defaultValue) {
        super(defaultValue);
    }

    public BindableBool() {
        super(false);
    }

    /**
     * Toggles the current value.
     */
    public void toggle() {
        setValue(!getValue());
    }

    @Override
    protected Bindable<Boolean> createInstance() {
        return new BindableBool();
    }
}
