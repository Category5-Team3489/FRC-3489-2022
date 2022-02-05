package frc.robot;

public abstract class AutoInstruction {

    private boolean completed = false;

    public final AutoEvent finished = new AutoEvent();

    public final void onFinished(Runnable runnable) {
        finished.sub(runnable);
    }

    public abstract void init();

    public abstract void periodic();

    public final void complete() {
        completed = true;
        finished.run();
    }

    public final boolean hasCompleted() {
        return completed;
    }
}
