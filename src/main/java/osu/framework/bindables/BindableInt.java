package osu.framework.bindables;

public class BindableInt extends Bindable<Integer> {
    public BindableInt(int defaultValue) {
        super(defaultValue);
    }

    public BindableInt() {
        super(0);
    }
}
