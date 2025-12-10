package osu.framework.graphics;

import osu.framework.math.Vector2;
import osu.framework.graphics.containers.CompositeDrawable;

public class Drawable {
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
        // Default implementation
    }

    public void load(Object clock, Object dependencies) {
        isLoaded = true;
    }
}
