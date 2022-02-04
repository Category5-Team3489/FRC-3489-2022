package frc.robot.framework;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.ComponentsContainer;
import frc.robot.DriveHandler;
import frc.robot.JoystickHandler;
import frc.robot.Robot;
import frc.robot.TestHandler;

public class RobotHandler {

    public Robot robot;
    public RobotManager robotManager;
    public ComponentsContainer components;

    public TestHandler testHandler;
    public DriveHandler driveHandler;
    public JoystickHandler joystickHandler;

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
