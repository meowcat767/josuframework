package osu.framework.caching;

/**
 * A simple struct for tracking whether a cached value is valid.
 * This is useful for expensive calculations that should only be performed when
 * necessary.
 */
public class Cached {
    private boolean isValid;

    /**
     * Creates a new Cached instance.
     * 
     * @param isValid Whether the cached value is initially valid.
     */
    public Cached(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * Creates a new Cached instance with an invalid state.
     */
    public Cached() {
        this(false);
    }

    /**
     * Gets whether the cached value is valid.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Invalidates the cached value, marking it as needing recalculation.
     */
    public void invalidate() {
        isValid = false;
    }

    /**
     * Validates the cached value, marking it as up-to-date.
     */
    public void validate() {
        isValid = true;
    }

    /**
     * Checks if the cache is valid, and if not, runs the provided action to
     * recalculate.
     * 
     * @param recalculate The action to run if the cache is invalid.
     * @return True if recalculation was needed and performed.
     */
    public boolean ensureValid(Runnable recalculate) {
        if (!isValid) {
            recalculate.run();
            validate();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cached{isValid=" + isValid + "}";
    }
}
