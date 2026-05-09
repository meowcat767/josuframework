package osu.framework.graphics.shapes;

public class Box extends Shape {
    @Override
    public void Draw() {
        super.Draw();
        System.out.printf("[Box] Position: (Anchor: %s, Origin: %s), Size: (%.1f, %.1f), Rotation: %.1f, Colour: (%.2f, %.2f, %.2f, %.2f)\n",
                anchor, origin, size.x, size.y, rotation, colour.r, colour.g, colour.b, colour.a);
    }
}
