package osu.framework.lists;

public interface INotifyArrayChanged {
    void addArrayElementChangedListener(Runnable listener);

    void removeArrayElementChangedListener(Runnable listener);
}
