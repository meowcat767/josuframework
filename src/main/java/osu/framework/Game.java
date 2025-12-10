package osu.framework;

import osu.framework.graphics.Drawable;
import java.util.ArrayList;
import java.util.List;

public class Game extends Drawable {
    private final List<Drawable> children = new ArrayList<>();

    public void Add(Drawable drawable) {
        children.add(drawable);
    }

    @Override
    public void Update() {
        for (Drawable child : children) {
            child.Update();
        }
    }

    public void Run() {
        // Simulate load
        injectDependencies(this);

        // Simulate loop for now
        long lastTime = System.nanoTime();
        for (int i = 0; i < 100; i++) { // Run 100 frames
            long currentTime = System.nanoTime();
            Time.Elapsed = (currentTime - lastTime) / 1000000.0; // ms
            lastTime = currentTime;
            Update();
            try {
                Thread.sleep(16); // ~60fps
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void injectDependencies(Object target) {
        for (java.lang.reflect.Method method : target.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(osu.framework.allocation.BackgroundDependencyLoader.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(target);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
