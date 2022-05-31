package frc.robot.framework;

import frc.robot.Robot;
import frc.robot.containers.ComponentsContainer;
import frc.robot.handlers.AutoShootHandler;
import frc.robot.handlers.ButtonHandler;
import frc.robot.handlers.CargoSystemHandler;
import frc.robot.handlers.DriveHandler;
import frc.robot.handlers.IntakeHandler;
import frc.robot.handlers.LimelightHandler;
import frc.robot.handlers.ShooterHandler;

public class RobotReferences {

    public Robot robot;
    public RobotManager manager;
    public ComponentsContainer components;

    public DriveHandler drive;
    public ShooterHandler shooter;
    public CargoSystemHandler cargoSystem;
    public AutoShootHandler autoShoot;
    public LimelightHandler limelight;
    public IntakeHandler intake;
    public ButtonHandler button;
}
