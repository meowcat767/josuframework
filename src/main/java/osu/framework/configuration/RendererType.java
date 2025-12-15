package osu.framework.configuration;

/**
 * Renderer types supported by the framework.
 */
public enum RendererType {
    /**
     * Automatically select the best available renderer.
     */
    Automatic,

    /**
     * OpenGL renderer.
     */
    OpenGL,

    /**
     * Vulkan renderer.
     */
    Vulkan,

    /**
     * Direct3D 11 renderer (Windows only).
     */
    Direct3D11,

    /**
     * Metal renderer (macOS/iOS only).
     */
    Metal
}
