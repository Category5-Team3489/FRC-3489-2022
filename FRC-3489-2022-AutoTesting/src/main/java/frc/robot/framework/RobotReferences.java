package frc.robot.framework;

import frc.robot.Robot;
import frc.robot.containers.ComponentsContainer;
import frc.robot.handlers.*;

public abstract class RobotReferences {

    public Robot robot;
    public RobotManager robotManager;
    public ComponentsContainer components;

    public DriveHandler driveHandler;
    public AutoHandler autoHandler;
    public CameraHandler cameraHandler;
    public ClimberHandler climberHandler;
    public CargoTransferHandler cargoTransferHandler;
    public IntakeHandler intakeHandler;
    public ShooterHandler shooterHandler;
    public CargoSystemHandler cargoSystemHandler;
    public ShuffleboardHandler shuffleboardHandler;

}
