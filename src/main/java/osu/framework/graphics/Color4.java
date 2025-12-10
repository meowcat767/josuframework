package osu.framework.graphics;

public class Color4 {
    public float r;
    public float g;
    public float b;
    public float a;

    public Color4(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static final Color4 Tomato = new Color4(1.0f, 0.388f, 0.278f, 1.0f);
    public static final Color4 White = new Color4(1.0f, 1.0f, 1.0f, 1.0f);
}
