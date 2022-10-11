package frc.robot.framework.auto;

import java.util.ArrayList;
import java.util.List;

public final class AutoEvent implements Runnable {

    private List<Runnable> runnables = new ArrayList<Runnable>();

    public void add(Runnable runnable) {
        runnables.add(runnable);
    }

    @Override
    public void run() {
        runnables.forEach(Runnable::run);
    }
}
