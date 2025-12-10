package samplegame;

import osu.framework.Game;
import osu.framework.Time;
import osu.framework.allocation.BackgroundDependencyLoader;
import osu.framework.graphics.Anchor;
import osu.framework.graphics.Color4;
import osu.framework.graphics.shapes.Box;
import osu.framework.math.Vector2;

public class SampleGame extends Game {
    private Box box;

    @BackgroundDependencyLoader
    private void load() {
        box = new Box();
        box.anchor = Anchor.Centre;
        box.origin = Anchor.Centre;
        box.size = new Vector2(150, 150);
        box.colour = Color4.Tomato;

        Add(box);

        osu.framework.graphics.transforms.TransformRotation tr = new osu.framework.graphics.transforms.TransformRotation();
        tr.startTime = 0;
        tr.endTime = 1000;
        tr.startValue = 0f;
        tr.endValue = 100f;
        box.addTransform(tr);
    }

    @Override
    public void Update() {
        super.Update();
        if (box != null) {
            // box.rotation += (float) Time.Elapsed / 10;
            System.out.println("Box Rotation: " + box.rotation);
        }
    }

    // We need a way to add transforms. For now, let's simulate it in load or
    // update.
    // Since we don't have a full Transform implementation for specific properties
    // yet,
    // we might need to add a specific transform class for rotation.

    public static void main(String[] args) {
        new SampleGame().Run();
    }
}
