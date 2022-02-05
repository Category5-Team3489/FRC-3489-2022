package frc.robot.auto.framework;

import frc.robot.framework.RobotReferences;

public abstract class AutoInstruction extends RobotReferences {

    private boolean completed = false;

    protected final AutoEvent completedEvent = new AutoEvent();

    public final AutoInstruction onCompleted(Runnable runnable) {
        completedEvent.sub(runnable);
        return this;
    }

    public final void complete() {
        completed = true;
        completedEvent.run();
    }

    public final boolean hasCompleted() {
        return completed;
    }

    public abstract void init();

    public abstract void periodic();

    public abstract String debug();

}
