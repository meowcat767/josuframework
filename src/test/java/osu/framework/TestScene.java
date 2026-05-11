package osu.framework;

interface TestScene {
    void load();
    void update();
    boolean isComplete();
    boolean passed();
}
