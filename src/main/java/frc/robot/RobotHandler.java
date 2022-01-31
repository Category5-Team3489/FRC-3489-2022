package frc.robot;

public class RobotHandler {

    protected Robot robot;
    protected RobotManager robotManager;

    protected TestHandler testHandler;


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

    protected RobotHandler addReferences(RobotManager robotManager) {
        robot = robotManager.robot;
        this.robotManager = robotManager;

        testHandler = robotManager.testHandler;

        return this;
    }

}
