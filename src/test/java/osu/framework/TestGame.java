package osu.framework;

import osu.framework.graphics.containers.Container;

import java.util.ArrayList;
import java.util.List;

public class TestGame extends Container {
    private final List<TestScene> scenes = new ArrayList<>();
    private int current = 0;

    public void addScene(TestScene scene) {
        scenes.add(scene);
    }

    @Override
    public void Update() {
        if (current >= scenes.size()) return;

        TestScene scene = scenes.get(current);

        if (!scene.isComplete()) {
            scene.update();
        } else {
            current++;
        }
    }

    public void Run() {
        injectDependencies(this);

        for (TestScene scene : scenes) {
            System.out.println("Running test scene: " + scene.getClass().getSimpleName());
            scene.load();

            while (!scene.isComplete()) {
                scene.update();
            }

            if (!scene.passed()) {
                throw new AssertionError("!!!TEST FAILED!!!" + scene.getClass().getSimpleName());
            }
            System.out.println("Test passed: " + scene.getClass().getSimpleName());
            System.out.flush();
        }
    }

    public static class Assert{
        public static void equals(double expected, double actual, double eps) {
            if (Math.abs(expected - actual) > eps) {
                throw new AssertionError("Expected: " + expected + " got " + actual);
            }
        }

        public static void trueCondition(boolean value, String message) {
            if (!value) {
                throw new AssertionError(message);
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
