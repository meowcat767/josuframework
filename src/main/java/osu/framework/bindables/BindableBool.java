package osu.framework.bindables;

public class BindableBool extends Bindable<Boolean> {
    public BindableBool(boolean defaultValue) {
        super(defaultValue);
    }

    public BindableBool() {
        super(false);
    }
}
