package osu.framework.bindables;

/**
 * An interface for objects that have a default value.
 */
public interface IHasDefaultValue {
    /**
     * Checks whether the current value is equal to the default value.
     */
    boolean isDefault();

    /**
     * Reverts the current value to the default value.
     */
    void setDefault();
}
