package frc.robot;

import java.util.ArrayList;
import java.util.List;

public class AutoEvent implements Runnable {

    private List<Runnable> runnables = new ArrayList<Runnable>();

    public void sub(Runnable runnable) {
        runnables.add(runnable);
    }

    public void run() {
        runnables.forEach(Runnable::run);
    }
}
