package frc.robot.framework;

import java.util.function.Consumer;

public abstract class RobotHandler extends RobotReferences {

    private Consumer<RobotReferences> copy;

    public void robotInit() {}
    public void robotPeriodic() {}
    public void autonomousInit() {}
    public void autonomousPeriodic() {}
    public void teleopInit() {}
    public void teleopPeriodic() {}
    public void disabledInit() {}
    public void disabledPeriodic() {}
    public void testInit() {}
    public void testPeriodic() {}
    public void simulationInit() {}
    public void simulationPeriodic() {}

    public RobotHandler init(Consumer<RobotReferences> copy) {
        this.copy = copy;
        return this;
    }

    public void copyTo(RobotReferences references) {
        copy.accept(references);
    }
}
