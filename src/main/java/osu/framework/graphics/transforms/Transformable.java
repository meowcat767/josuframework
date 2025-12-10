package osu.framework.graphics.transforms;

import osu.framework.Time;
import java.util.ArrayList;
import java.util.List;

public abstract class Transformable {
    private final List<Transform<?>> transforms = new ArrayList<>();

    public void addTransform(Transform<?> transform) {
        if (transform.startTime < Time.Current) {
            // If we're adding a transform that should have already started,
            // make sure we capture the current state as the start value if needed.
            // For now, simplified.
        }
        transforms.add(transform);
    }

    public void updateTransforms() {
        double time = Time.Current;
        for (Transform<?> t : transforms) {
            if (time >= t.startTime) {
                t.apply(this, time);
            }
        }

        // Remove completed transforms?
        transforms.removeIf(t -> time >= t.endTime);
    }

    public void clearTransforms() {
        transforms.clear();
    }
}
