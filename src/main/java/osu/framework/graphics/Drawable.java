package osu.framework.graphics;

import osu.framework.math.Vector2;
import osu.framework.graphics.containers.CompositeDrawable;
import osu.framework.graphics.transforms.Transformable;

public class Drawable extends Transformable {
    public Anchor anchor = Anchor.TopLeft;
    public Anchor origin = Anchor.TopLeft;
    public Vector2 size = new Vector2();
    public Color4 colour = Color4.Tomato; // Default?
    public float rotation;

    public CompositeDrawable parent;
    public float depth;
    public boolean isAlive = true;

    // Simplified LoadState
    public boolean isLoaded;

    public void Update() {
        updateTransforms();
    }

    public void load(Object clock, Object dependencies) {
        isLoaded = true;
    }

    public void rotateTo(float newRotation, double duration, Easing easing) {
        osu.framework.graphics.transforms.TransformRotation tr = new osu.framework.graphics.transforms.TransformRotation();
        tr.startTime = osu.framework.Time.Current;
        tr.endTime = tr.startTime + duration;
        tr.startValue = rotation;
        tr.endValue = newRotation;
        tr.easing = easing;
        addTransform(tr);
    }

    public void rotateTo(float newRotation, double duration) {
        rotateTo(newRotation, duration, Easing.None);
    }
}
