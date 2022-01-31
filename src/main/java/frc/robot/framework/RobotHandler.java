package frc.robot.framework;

import frc.robot.ComponentsContainer;
import frc.robot.Robot;
import frc.robot.TestHandler;

public class RobotHandler {

    public Robot robot;
    public RobotManager robotManager;
    public ComponentsContainer components;

    public TestHandler testHandler;

    public RobotHandler() {}
    
    public void robotInit() {}
    public void robotPeriodic() {}
    public void disabledInit() {}
    public void disabledPeriodic() {}
    public void autonomousInit() {}
    public void autonomousPeriodic() {}
    public void teleopInit() {}
    public void teleopPeriodic() {}
    public void testInit() {}
    public void testPeriodic() {}

}
