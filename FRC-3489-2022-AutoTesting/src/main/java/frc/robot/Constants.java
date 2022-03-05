package frc.robot;

public final class Constants {

    // General
    public static final double FastPeriodicPeriod = 1d / 200d;
    public static final int CameraPixelWidth = 400;
    public static final int CameraPixelHeight = 300;
    public static final int CameraFPS = 20;
    public static final double ButtonDebounceTime = 0.1;

    // State
    public static final boolean HasCameras = true;
    public static final boolean SafetiesEnabled = false;

    // Limiters
    public static final int MotorControllerTimeout = 30;
    public static final double DriveSetSpeedDeltaLimiter = 8; // (-1, 1) set speed delta per second

    // Shuffleboard
    public static final String ShuffleboardMainTabName = "3489 2022 Ducknado";
    public static final String ShuffleboardAutoTabName = "3489 2022 Ducknado Auto";

    // Speeds
    public static final double IntakeMotorSpeed = -0.8;
    public static final double ReverseIntakeMotorSpeed = 0.8;
    public static final double CargoTransferMotorSpeed = -0.6;
    public static final double ReverseCargoTransferMotorSpeed = 0.6;
    public static final double CargoTransferShootSpeed = -0.6;

    public static final double ShootHighBottomMotorSpeed = 0.5;
    public static final double ShootHighTopMotorSpeed = 0.5;
    public static final double ShootLowBottomMotorSpeed = 0.25;
    public static final double ShootLowTopMotorSpeed = 0.25;

    public static final double WrongColorTopSpeed = 0.2;
    public static final double WrongColorBottomSpeed = 0.2;


    // Manipulator Buttons
    public static final int ButtonToggleIntake = 2;
    public static final int ButtonShoot = 1;
    public static final int ButtonDebugCargoInLaser = 4;
    public static final int ButtonWrongColor = 3;
    
    public static final int ButtonShootLowGoal = 8;
    public static final int ButtonShootHighGoal = 7;
    public static final int ButtonStopShooter = 6;

    public static final int ButtonClimbActivate = 11;
    public static final int ButtonClimbMid = 9;
    public static final int ButtonClimbMidToHigh = 10;

    // Drive Buttons
    public static final int ButtonSwitchFront = 13;

    // Clicks
    public static final double ClicksPerCargoLength = 21000;
    public static final double ClicksPerInchDriven = 4096.0 / (Math.PI * 6);
    public static final double ClicksPerFootDriven = 12 * ClicksPerInchDriven;
   
}
