package osu.framework;

import osu.framework.graphics.Drawable;
import osu.framework.graphics.shapes.Box;
import osu.framework.graphics.containers.Container;
import osu.framework.TestGame.Assert;

public class FadeTest extends Container implements TestScene {
    private Drawable box;
    private double time;

    public void load() {
        box = new Box();
        add(box);
    }

    public void update() {
        time += 0.1; // Manually advance time as Time.Elapsed might be 0 in this simplified test environment
        Time.Current += 0.1;

        box.setAlpha(time / 1.0);

        if (time >= 1.0) {
            Assert.equals(1.0, box.getAlpha(), 0.01);
        }
    }

    public boolean isComplete() {
        return time > 1.0;
    }

    public boolean passed() {
        return Math.abs(box.getAlpha() - 1.0) < 0.01;
    }
}