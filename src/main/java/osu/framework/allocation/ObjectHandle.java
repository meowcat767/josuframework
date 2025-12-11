package osu.framework.allocation;

import java.lang.ref.WeakReference;

/**
 * A handle to an object that can be used for weak references or pinning.
 * This is a simplified Java version of the C# ObjectHandle which uses GCHandle.
 * 
 * @param <T> The type of object being handled.
 */
public class ObjectHandle<T> implements AutoCloseable {
    private WeakReference<T> weakRef;
    private T strongRef;
    private final boolean isWeak;

    /**
     * Creates a strong reference handle to the target object.
     * 
     * @param target The target object to wrap.
     */
    public ObjectHandle(T target) {
        this(target, false);
    }

    /**
     * Creates a handle to the target object.
     * 
     * @param target The target object to wrap.
     * @param weak   Whether to use a weak reference (true) or strong reference
     *               (false).
     */
    public ObjectHandle(T target, boolean weak) {
        this.isWeak = weak;
        if (weak) {
            this.weakRef = new WeakReference<>(target);
        } else {
            this.strongRef = target;
        }
    }

    /**
     * Gets the object being referenced.
     * 
     * @return The targeted object, or null if it has been garbage collected (weak
     *         references only).
     */
    public T getTarget() {
        if (isWeak) {
            return weakRef != null ? weakRef.get() : null;
        } else {
            return strongRef;
        }
    }

    /**
     * Tries to get the object being referenced.
     * 
     * @return True if successful and the object is still alive, false otherwise.
     */
    public boolean isAlive() {
        return getTarget() != null;
    }

    /**
     * Disposes this handle, clearing the reference.
     */
    @Override
    public void close() {
        if (isWeak) {
            if (weakRef != null) {
                weakRef.clear();
                weakRef = null;
            }
        } else {
            strongRef = null;
        }
    }
}
