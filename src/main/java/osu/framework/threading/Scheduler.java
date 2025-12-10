package osu.framework.threading;

import osu.framework.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Scheduler {
    private final List<Runnable> runQueue = new ArrayList<>();
    private final PriorityQueue<ScheduledTask> timedTasks = new PriorityQueue<>(
            Comparator.comparingDouble(t -> t.executionTime));

    public void add(Runnable task) {
        synchronized (runQueue) {
            runQueue.add(task);
        }
    }

    public void addDelayed(Runnable task, double delay) {
        synchronized (timedTasks) {
            timedTasks.add(new ScheduledTask(task, Time.Current + delay));
        }
    }

    public void update() {
        double currentTime = Time.Current;

        // Process timed tasks
        synchronized (timedTasks) {
            while (!timedTasks.isEmpty() && timedTasks.peek().executionTime <= currentTime) {
                ScheduledTask task = timedTasks.poll();
                add(task.task);
            }
        }

        // Process run queue
        List<Runnable> toRun;
        synchronized (runQueue) {
            toRun = new ArrayList<>(runQueue);
            runQueue.clear();
        }

        for (Runnable task : toRun) {
            task.run();
        }
    }

    private static class ScheduledTask {
        Runnable task;
        double executionTime;

        ScheduledTask(Runnable task, double executionTime) {
            this.task = task;
            this.executionTime = executionTime;
        }
    }
}
