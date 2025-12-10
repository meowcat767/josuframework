package osu.framework.graphics;

import osu.framework.math.Vector2;

public class Drawable {
    public Anchor anchor = Anchor.TopLeft;
    public Anchor origin = Anchor.TopLeft;
    public Vector2 size = new Vector2();
    public Color4 colour = Color4.Tomato; // Default?
    public float rotation;

    public void Update() {
        // Default implementation
    }
}
