package frc.robot.framework;

import frc.robot.Robot;
import frc.robot.containers.ComponentsContainer;
import frc.robot.handlers.AutoHandler;
import frc.robot.handlers.CameraHandler;
import frc.robot.handlers.DriveHandler;

public abstract class RobotReferences {

    public Robot robot;
    public RobotManager robotManager;
    public ComponentsContainer components;

    public DriveHandler driveHandler;
    public AutoHandler autoHandler;
    public CameraHandler cameraHandler;

}
