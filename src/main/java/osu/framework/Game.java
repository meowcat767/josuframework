package osu.framework;

import osu.framework.graphics.containers.Container;
import osu.framework.graphics.Drawable;
import osu.framework.threading.Scheduler;
import java.util.ArrayList;
import java.util.List;

public class Game extends Container {
    public Scheduler scheduler = new Scheduler();

    public void Add(Drawable drawable) {
        add(drawable);
    }

    @Override
    public void Update() {
        scheduler.update();
        super.Update();
    }

    public void Run() {
        // Simulate load
        injectDependencies(this);

        // Simulate loop for now
        long startTime = System.nanoTime();
        long lastTime = startTime;
        for (int i = 0; i < 100; i++) { // Run 100 frames
            long currentTime = System.nanoTime();
            Time.Elapsed = (currentTime - lastTime) / 1000000.0; // ms
            Time.Current = (currentTime - startTime) / 1000000.0; // ms
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
