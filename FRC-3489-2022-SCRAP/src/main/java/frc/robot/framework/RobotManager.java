package frc.robot.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import frc.robot.Robot;
import frc.robot.containers.ComponentsContainer;
import frc.robot.handlers.AutoShootHandler;
import frc.robot.handlers.CargoSystemHandler;
import frc.robot.handlers.DriveHandler;
import frc.robot.handlers.LimelightHandler;
import frc.robot.handlers.ShooterHandler;

public final class RobotManager extends RobotHandler {

    private RobotPhase phase = RobotPhase.Disabled;
    private List<RobotHandler> handlers = new ArrayList<RobotHandler>();

    public RobotManager(Robot robot)
    {
        this.robot = robot;
        this.manager = this;
        this.components = new ComponentsContainer();
        
        handlers.add((drive = new DriveHandler()).init((r) -> r.drive = drive));
        handlers.add((shooter = new ShooterHandler()).init((r) -> r.shooter = shooter));
        handlers.add((cargoSystem = new CargoSystemHandler()).init((r) -> r.cargoSystem = cargoSystem));
        handlers.add((autoShoot = new AutoShootHandler()).init((r) -> r.autoShoot = autoShoot));
        handlers.add((limelight = new LimelightHandler()).init((r) -> r.limelight = limelight));

        for (RobotHandler handler : handlers) {
            copyTo(handler);
        }
    }

    public void copyTo(RobotReferences references) {
        references.robot = robot;
        references.manager = this;
        references.components = components;

        for (RobotHandler handler : handlers) {
            handler.copyTo(references);
        }
    }

    public void forEachHandler(Consumer<RobotHandler> action) {
        handlers.forEach(action);
    }

    public RobotPhase getRobotPhase() {
        return phase;
    }

    @Override
    public void robotInit() {
        handlers.forEach(RobotHandler::robotInit);
    }
  
    @Override
    public void robotPeriodic() {
        handlers.forEach(RobotHandler::robotPeriodic);
    }
  
    @Override
    public void autonomousInit() {
        phase =  RobotPhase.Auto;
        handlers.forEach(RobotHandler::autonomousInit);
    }
  
    @Override
    public void autonomousPeriodic() {
        handlers.forEach(RobotHandler::autonomousPeriodic);
    }
  
    @Override
    public void teleopInit() {
        phase =  RobotPhase.Teleop;
        handlers.forEach(RobotHandler::teleopInit);
    }
  
    @Override
    public void teleopPeriodic() {
        handlers.forEach(RobotHandler::teleopPeriodic);
    }
  
    @Override
    public void disabledInit() {
        phase =  RobotPhase.Disabled;
        handlers.forEach(RobotHandler::disabledInit);
    }
  
    @Override
    public void disabledPeriodic() {
        handlers.forEach(RobotHandler::disabledPeriodic);
    }
  
    @Override
    public void testInit() {
        phase =  RobotPhase.Test;
        handlers.forEach(RobotHandler::testInit);
    }
  
    @Override
    public void testPeriodic() {
        handlers.forEach(RobotHandler::testPeriodic);
    }
  
    @Override
    public void simulationInit() {
        handlers.forEach(RobotHandler::simulationInit);
    }
  
    @Override
    public void simulationPeriodic() {
        handlers.forEach(RobotHandler::simulationPeriodic);
    }
}
