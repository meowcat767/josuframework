package osu.framework.lists;

import java.util.Objects;
import java.util.function.BiPredicate;

public class FuncEqualityComparer<T> {
    private final BiPredicate<T, T> func;

    private FuncEqualityComparer(BiPredicate<T, T> func) {
        this.func = func;
    }

    public boolean equals(T x, T y) {
        return func.test(x, y);
    }

    public int hashCode(T obj) {
        return Objects.hashCode(obj);
    }


}
