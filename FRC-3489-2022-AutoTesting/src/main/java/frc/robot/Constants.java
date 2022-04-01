package frc.robot;

public final class Constants {

    // General
    public static final double FastPeriodicPeriod = 1d / 200d;
    public static final int CameraPixelWidth = 100;
    public static final int CameraPixelHeight = 100;
    public static final int CameraFPS = 20;
    public static final double ButtonDebounceTime = 0.5;
    public static final boolean SafetiesEnabled = false;
    public static final int MotorControllerTimeout = 30;
    public static final double DriveSetSpeedDeltaLimiter = 8; // (-1, 1) set speed delta per second
    public static final double ManualCargoSystemControlThreshhold = 0.75;
    public static final String ShuffleboardMainTabName = "3489 2022";
    public static final String ShuffleboardAutoTabName = "3489 2022 Auto";
    public static final double ShuffleboardShowUpdateRate = 4;
    public static final int ShuffleboardShowUpdatePeriod = (int)(50 / ShuffleboardShowUpdateRate); 

    // Speeds
    public static final double ForwardIntakeMotorSpeed = 1;
    public static final double BackwardIntakeMotorSpeed = -0.8;

    public static final double DriveAutoAimFrictionOvercomeMotorSpeed = 0.55;

    public static final double CargoTransferMotorSpeed = -0.6;
    public static final double ReverseCargoTransferMotorSpeed = 0.6;
    public static final double CargoTransferShootSpeed = -0.6;

    public static final double ShootLowBottomMotorSpeed = 0.4;
    public static final double ShootLowTopMotorSpeed = 0.4;

    public static final double ShootHighBottomMotorSpeed = 0.5;
    public static final double ShootHighTopMotorSpeed = 0.5;

    public static final double WrongColorTopSpeed = 0.2;
    public static final double WrongColorBottomSpeed = 0.2;

    public static final double DriveToMidBarSpeed = 0.5;
    public static final double TelescopeExtendSpeed = -0.8;
    public static final double SlightlyExtendSpeed = -0.05;
    public static final double TelescopeRetractSpeed = 0.6;




    // Manipulator Buttons
    public static final int ButtonToggleIntake = 2;
    public static final int ButtonShoot = 1;
    public static final int ButtonAimCenterShoot = 4;
    //public static final int ButtonDebugCargoInLaser = 4;
    
    public static final int ButtonShootLowGoal = 8;
    public static final int ButtonShootHighGoal = 7;
    public static final int ButtonShootWrongColor = 3;
    public static final int ButtonStopShooter = 6;

    public static final int ButtonClimbMid = 9;
    public static final int ButtonClimbHigh = 10;
    public static final int ButtonClimbActivate = 5;
    public static final int ButtonClimbReset = 11;
    public static final int ButtonClimbEStop = 12;

    // Drive Buttons
    public static final int ButtonSwitchCamera = 13;
    public static final int ButtonSwitchCameraB = 7;

    // Clicks
    public static final double ClicksPerCargoLength = 11159 * 1.25d;//21k

    public static final double ShootStopTimeDelay = 2; // was 3 and was too slow

    public static final class Drive
    {
        public static final double GearRatio = 1 / 7.31;
        public static final double ClicksPerMotorRev = 42;
        public static final double ClicksPerRev = GearRatio * ClicksPerMotorRev;

        public static final double AimTolerance = 1.5; // degrees
        public static final double CenterTolerance = 3; // inches // was 2

        public static final double AimFrictionMotorSpeed = 0.25; // was 25
        public static final double CenterFrictionMotorSpeed = 0.15; // was 15

        public static final double ShooterDelay = 0.5;
        public static final double ShootTime = 1.75;

        public static final double ShootingDistance = 105; // inches 107
    }

    public static final class Intake
    {
        //public static final double LaserSensorDelay = 2;
        //public static final int LaserSensorCycleDelay = (int)(LaserSensorDelay * 50);
    }

    public static final class Auto
    {
        public static final double ClicksPerInchDriven = Drive.ClicksPerRev / (Math.PI * 6);
        public static final double ClicksPerFootDriven = 12 * ClicksPerInchDriven;
    }

    public static final class Shooter
    {
        public static final double CanShootSpeedThreshold = 0.15;
    }

    public static final class Climber
    {
        public static final double ExtendTelesopeClicks = 105000;
        public static final double RetractTelesopeClicks = 105000 * 1.05;

        public static final double PitchThreshold = 4;

        // TODO find current draw for bagpipe falcon
        public static final double TelescopeMotorCurrentSafety = Double.MAX_VALUE;

        //public static final double DriveToMidBarSpeedA = 0.25;//0.325;
        //public static final double DriveToMidBarSpeedB = 0.25;
        public static final double SquareOnMidBarSpeed = 0.3;

        public static final double DriveToMidBarTime = 1.75 * 1.5;
    }

    public static final class Camera
    {
        public static final int ServoStartingPositionIndex = 0;
        public static final double[] ServoPositions = { 70, 140 };
    }
}
