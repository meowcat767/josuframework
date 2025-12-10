package osu.framework.bindables;

public class BindableDouble extends Bindable<Double> {
    public BindableDouble(double defaultValue) {
        super(defaultValue);
    }

    public BindableDouble() {
        super(0.0);
    }
}
