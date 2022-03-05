package frc.robot;

public final class Constants {

    // General
    public static final double FastPeriodicPeriod = 1d / 200d;
    public static final int CameraPixelWidth = 200;
    public static final int CameraPixelHeight = 200;
    public static final int CameraFPS = 20;
    public static final double ButtonDebounceTime = 0.1;
    public static final boolean SafetiesEnabled = false;
    public static final int MotorControllerTimeout = 30;
    public static final double DriveSetSpeedDeltaLimiter = 8; // (-1, 1) set speed delta per second
    public static final double ManualCargoSystemControlThreshhold = 0.4;

    // Shuffleboard
    public static final String ShuffleboardMainTabName = "3489 2022";
    public static final String ShuffleboardAutoTabName = "3489 2022 Auto";

    // Speeds
    public static final double IntakeMotorSpeed = -0.8;
    public static final double ReverseIntakeMotorSpeed = 0.8;

    public static final double CargoTransferMotorSpeed = -0.6;
    public static final double ReverseCargoTransferMotorSpeed = 0.6;
    public static final double CargoTransferShootSpeed = -0.6;

    public static final double ShootLowBottomMotorSpeed = 0.25;
    public static final double ShootLowTopMotorSpeed = 0.25;

    public static final double ShootHighBottomMotorSpeed = 0.5;
    public static final double ShootHighTopMotorSpeed = 0.5;

    public static final double WrongColorTopSpeed = 0.2;
    public static final double WrongColorBottomSpeed = 0.2;

    public static final double DriveToMidBarSpeed = 0.35;
    public static final double TelescopeExtendSpeed = -0.6;
    public static final double SlightlyExtendSpeed = -0.2;
    public static final double TelescopeRetractSpeed = 0.6;




    // Manipulator Buttons 10
    public static final int ButtonToggleIntake = 2;
    public static final int ButtonShoot = 1;
    public static final int ButtonDebugCargoInLaser = 4;
    
    public static final int ButtonShootLowGoal = 8;
    public static final int ButtonShootHighGoal = 7;
    public static final int ButtonShootWrongColor = 3;
    public static final int ButtonStopShooter = 6;

    public static final int ButtonClimb = 9;
    public static final int ButtonClimbHigh = 10;
    public static final int ButtonClimbActivate = 5;
    public static final int ButtonClimbReset = 11;
    public static final int ButtonClimbEStop = 12;

    // Drive Buttons
    public static final int ButtonSwitchFront = 13;

    // Clicks
    public static final double ClicksPerCargoLength = 21000;
    public static final double ClicksPerInchDriven = 4096.0 / (Math.PI * 6);
    public static final double ClicksPerFootDriven = 12 * ClicksPerInchDriven;

    public static final double ClicksExtendTelesope = 107000;
    public static final double ClicksRetractTelesope = 107000;
    public static final double ClicksExtendTelescopeSlightly = ClicksExtendTelesope * 0.1;
    public static final double ClicksRetractTelescopeMajorly = ClicksExtendTelesope * 0.8;
    public static final double ClicksToDriveToMidBar = 1 * ClicksPerFootDriven;

    // Time Delays
    public static final double S1TimeDelay = 2;
    public static final double S7TimeDelay = 2;
    public static final double S9TimeDelay = 1;
}
