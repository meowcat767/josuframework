package osu.framework.graphics.containers;

import osu.framework.graphics.Drawable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompositeDrawable extends Drawable {
    protected final List<Drawable> internalChildren = new ArrayList<>();

    protected void addInternal(Drawable drawable) {
        if (drawable.parent != null) {
            throw new IllegalStateException("Drawable already has a parent");
        }
        drawable.parent = this;
        internalChildren.add(drawable);
        // Sort by depth? For now, just append.
    }

    protected void removeInternal(Drawable drawable) {
        if (internalChildren.remove(drawable)) {
            drawable.parent = null;
        }
    }

    protected void clearInternal() {
        for (Drawable d : internalChildren) {
            d.parent = null;
        }
        internalChildren.clear();
    }

    @Override
    public void Update() {
        super.Update();
        // Update children
        // Use a copy to avoid concurrent modification if update modifies the list
        for (Drawable child : new ArrayList<>(internalChildren)) {
            if (child.isAlive) {
                child.Update();
            }
        }
    }
}
