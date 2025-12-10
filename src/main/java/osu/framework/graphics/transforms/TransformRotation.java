package osu.framework.graphics.transforms;

import osu.framework.graphics.Drawable;

public class TransformRotation extends Transform<Float> {
    @Override
    public void apply(Object target, double time) {
        if (target instanceof Drawable) {
            Drawable d = (Drawable) target;
            double progress = (time - startTime) / (endTime - startTime);
            if (progress > 1)
                progress = 1;
            if (progress < 0)
                progress = 0;
            float val = startValue + (endValue - startValue) * (float) progress;
            d.rotation = val;
        }
    }

    @Override
    public void readIntoStartValue(Object target) {
        if (target instanceof Drawable) {
            startValue = ((Drawable) target).rotation;
        }
    }
}
