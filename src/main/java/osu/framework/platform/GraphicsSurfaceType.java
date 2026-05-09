package osu.framework.platform;

/**
 * The graphics surface of an IWindow
 */
public enum GraphicsSurfaceType {
    /**
     * An OpenGL graphics surface.
     */
    OPENGL("OpenGL"),
    /**
     * A Metal graphics surface.
     */
    METAL("Metal"),
    /**
     * A Vulkan graphics surface.
     */
    VULKAN("Vulkan"),
    /**
     * A Direct3D11 Graphics Surface
     */
    DIRECT3D11("Direct3D 11");

    private final String displayName;

    GraphicsSurfaceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
