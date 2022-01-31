package frc.robot.framework;

import java.util.ArrayList;
import java.util.List;

import frc.robot.ComponentsContainer;
import frc.robot.Robot;
import frc.robot.TestHandler;

public class RobotManager extends RobotHandler {

    private List<RobotHandler> handlers = new ArrayList<RobotHandler>();

    public RobotManager(Robot robot) {
        this.robot = robot;
        this.robotManager = this;
        this.components = new ComponentsContainer();

        testHandler = new TestHandler();
        handlers.add(testHandler);

        for (RobotHandler handler : handlers) {
            handler.robot = robot;
            handler = this;
            handler.components = components;
    
            handler.testHandler = testHandler;
        }
    }

    public void robotInit() {
        handlers.forEach(RobotHandler::robotInit);
    }
    public void robotPeriodic() {
        handlers.forEach(RobotHandler::robotPeriodic);
    }
    public void disabledInit() {
        handlers.forEach(RobotHandler::disabledInit);
    }
    public void disabledPeriodic() {
        handlers.forEach(RobotHandler::disabledPeriodic);
    }
    public void autonomousInit() {
        handlers.forEach(RobotHandler::autonomousInit);
    }
    public void autonomousPeriodic() {
        handlers.forEach(RobotHandler::autonomousPeriodic);
    }
    public void teleopInit() {
        handlers.forEach(RobotHandler::teleopInit);
    }
    public void teleopPeriodic() {
        handlers.forEach(RobotHandler::teleopPeriodic);
    }
    public void testInit() {
        handlers.forEach(RobotHandler::testInit);
    }
    public void testPeriodic() {
        handlers.forEach(RobotHandler::testPeriodic);
    }

}
