package frc.robot.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import frc.robot.Robot;
import frc.robot.containers.ComponentsContainer;
import frc.robot.handlers.*;
import frc.robot.types.RobotPhase;

public final class RobotManager extends RobotHandler {

    private RobotPhase robotPhase = RobotPhase.Disabled;

    private List<RobotHandler> handlers = new ArrayList<RobotHandler>();

    public RobotManager(Robot robot) {
        this.robot = robot;
        this.robotManager = this;
        this.components = new ComponentsContainer();

        handlers.add(driveHandler = new DriveHandler());
        handlers.add(autoHandler = new AutoHandler());
        handlers.add(cameraHandler = new CameraHandler());
        handlers.add(climberHandler = new ClimberHandler());
        handlers.add(cargoTransferHandler = new CargoTransferHandler());
        handlers.add(intakeHandler = new IntakeHandler());
        handlers.add(shooterHandler = new ShooterHandler());
        handlers.add(cargoSystemHandler = new CargoSystemHandler());
        handlers.add(limelightHandler = new LimelightHandler());
        handlers.add(diagnostic = new Diagnostic());

        // SHUFFLEBOARD MUST BE LAST
        handlers.add(shuffleboardHandler = new ShuffleboardHandler());

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
        references.climberHandler = climberHandler;
        references.cargoTransferHandler = cargoTransferHandler;
        references.intakeHandler = intakeHandler;
        references.shooterHandler = shooterHandler;
        references.cargoSystemHandler = cargoSystemHandler;
        references.limelightHandler = limelightHandler;
        references.diagnostic = diagnostic;

        // SHUFFLEBOARD MUST BE LAST
        references.shuffleboardHandler = shuffleboardHandler;
    }

    public void forEachHandler(Consumer<RobotHandler> action) {
        handlers.forEach(action);
    }

    public RobotPhase getRobotPhase() {
        return robotPhase;
    }

    public void robotInit() {
        handlers.forEach(RobotHandler::robotInit);
    }
    public void robotPeriodic() {
        // TODO Debug
        //System.out.println("F: (" + components.leftFrontDriveMotor.getEncoder().getPosition() + ", " + components.rightFrontDriveMotor.getEncoder().getPosition() + ")");
        //System.out.println("B: (" + components.leftFollowerDriveMotor.getEncoder().getPosition() + ", " + components.rightFollowerDriveMotor.getEncoder().getPosition() + ")");
        handlers.forEach(RobotHandler::robotPeriodic);
    }
    public void robotFastPeriodic() {
        handlers.forEach(RobotHandler::robotFastPeriodic);
    }
    public void disabledInit() {
        robotPhase = RobotPhase.Disabled;
        handlers.forEach(RobotHandler::disabledInit);
    }
    public void disabledPeriodic() {
        handlers.forEach(RobotHandler::disabledPeriodic);
    }
    public void autonomousInit() {
        robotPhase = RobotPhase.Autonomous;
        handlers.forEach(RobotHandler::autonomousInit);
    }
    public void autonomousPeriodic() {
        handlers.forEach(RobotHandler::autonomousPeriodic);
    }
    public void teleopInit() {
        robotPhase = RobotPhase.Teleop;
        handlers.forEach(RobotHandler::teleopInit);
    }
    public void teleopPeriodic() {
        handlers.forEach(RobotHandler::teleopPeriodic);
    }
    public void testInit() {
        robotPhase = RobotPhase.Test;
        handlers.forEach(RobotHandler::testInit);
    }
    public void testPeriodic() {
        handlers.forEach(RobotHandler::testPeriodic);
    }

}
