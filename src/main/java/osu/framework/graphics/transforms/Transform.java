package osu.framework.graphics.transforms;

import osu.framework.graphics.Easing;

public abstract class Transform<TValue> {
    public double startTime;
    public double endTime;
    public TValue startValue;
    public TValue endValue;
    public Easing easing = Easing.None;

    public abstract void apply(Object target, double time);

    public abstract void readIntoStartValue(Object target);
}
