package frc.robot.framework;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Robot;
import frc.robot.containers.ComponentsContainer;
import frc.robot.handlers.*;

public final class RobotManager extends RobotHandler {

    private List<RobotHandler> handlers = new ArrayList<RobotHandler>();

    public RobotManager(Robot robot) {
        this.robot = robot;
        this.robotManager = this;
        this.components = new ComponentsContainer();

        handlers.add(driveHandler = new DriveHandler());
        handlers.add(autoHandler = new AutoHandler());
        handlers.add(cameraHandler = new CameraHandler());
        handlers.add(testHandler = new TestHandler());
        handlers.add(climberHandler = new ClimberHandler());
        handlers.add(cargoTransferHandler = new CargoTransferHandler());
        handlers.add(intakeHandler = new IntakeHandler());
        handlers.add(shooterHandler = new ShooterHandler());
        handlers.add(cargoSystemHandler = new CargoSystemHandler());

        for (RobotReferences references : handlers) {
            copyReferences(references);
        }
    }

    public void copyReferences(RobotReferences references) {
        references.robot = robot;
        references.robotManager = this;
        references.components = components;

        references.driveHandler = driveHandler;
        references.autoHandler = autoHandler;
        references.cameraHandler = cameraHandler;
        references.testHandler = testHandler;
        references.climberHandler = climberHandler;
        references.cargoTransferHandler = cargoTransferHandler;
        references.intakeHandler = intakeHandler;
        references.shooterHandler = shooterHandler;
        references.cargoSystemHandler = cargoSystemHandler;
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
