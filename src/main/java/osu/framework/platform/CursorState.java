package osu.framework.platform;

public enum CursorState {
    /**
     * The OS cursor is always visible and can move anywhere
     */
    DEFAULT(0),


    /**
     * The OS cursor is hidden while hovering the IWindow, but can still move anywhere
     */
    HIDDEN(1),

    /**
     * The OS cursor is confined to the IWindow, while the window is in focus.
     */
    CONFINED(2),

    /**
     * The OS cursor is hidden while hovering the IWindow.
     * It is confined to the IWindow, while the window is in focus and can move freely otherwise.
     */
    HIDDEN_AND_CONFINED(HIDDEN.value | CONFINED.value);

    private final int value;

    public boolean hasFlag(CursorState state) {
        return (value & state.value) == state.value;
    }
    public int getValue() {
        return value;
    }

    CursorState(int value) {
        this.value = value;
    }
}
