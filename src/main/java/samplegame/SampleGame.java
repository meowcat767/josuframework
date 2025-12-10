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
    }

    @Override
    public void Update() {
        super.Update();
        if (box != null) {
            box.rotation += (float) Time.Elapsed / 10;
            System.out.println("Box Rotation: " + box.rotation);
        }
    }

    public static void main(String[] args) {
        new SampleGame().Run();
    }
}
