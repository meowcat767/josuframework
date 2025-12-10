package osu.framework.graphics.containers;

import osu.framework.graphics.Drawable;
import java.util.List;

public class Container extends CompositeDrawable {
    public void add(Drawable drawable) {
        addInternal(drawable);
    }

    public void remove(Drawable drawable) {
        removeInternal(drawable);
    }

    public void clear() {
        clearInternal();
    }

    public List<Drawable> getChildren() {
        return internalChildren;
    }
}
