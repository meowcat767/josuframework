package osu.framework.allocation;

import java.util.Objects;

/**
 * Extra information that identifies a cached dependency.
 */
public class CacheInfo {
    private final String name;
    private final Object parent;

    /**
     * Creates a default (empty) CacheInfo.
     */
    public CacheInfo() {
        this(null, null);
    }

    /**
     * Creates a CacheInfo with a name.
     * 
     * @param name The name identifying this cache entry.
     */
    public CacheInfo(String name) {
        this(name, null);
    }

    /**
     * Creates a CacheInfo with a name and parent.
     * 
     * @param name   The name identifying this cache entry.
     * @param parent The parent object for this cache entry.
     */
    public CacheInfo(String name, Object parent) {
        this.name = name;
        this.parent = parent;
    }

    /**
     * Gets the name of this cache entry.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the parent object for this cache entry.
     */
    public Object getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof CacheInfo))
            return false;
        CacheInfo other = (CacheInfo) obj;
        return Objects.equals(name, other.name) && Objects.equals(parent, other.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent);
    }

    @Override
    public String toString() {
        if (name == null && parent == null) {
            return "CacheInfo(default)";
        }
        return "CacheInfo(name=" + name + ", parent=" + parent + ")";
    }
}
