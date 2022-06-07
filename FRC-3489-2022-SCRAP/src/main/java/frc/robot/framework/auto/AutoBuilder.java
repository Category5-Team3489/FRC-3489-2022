package frc.robot.framework.auto;

import frc.robot.framework.RobotReferences;

public abstract class AutoBuilder extends RobotReferences {

    private AutoRunner runner;

    /**
     * Sets the runner of the auto
     * Used internally
     * @param runner Runner of the auto
     */
    public final void setRunner(AutoRunner runner) {
        this.runner = runner;
    }
}
