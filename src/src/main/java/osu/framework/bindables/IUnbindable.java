package osu.framework.bindables;

/**
 * An interface for objects that can be unbound.
 */
public interface IUnbindable {
    /**
     * Unbinds all bindings and events.
     */
    void unbindAll();

    /**
     * Unbinds from a specific bindable.
     * 
     * @param them The bindable to unbind from.
     */
    void unbindFrom(IUnbindable them);
}
