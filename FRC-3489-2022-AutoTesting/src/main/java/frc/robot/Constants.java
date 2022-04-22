package frc.robot;

import frc.robot.types.DataPoint;

public final class Constants {

    // General
    public static final double FastPeriodicPeriod = 1d / 200d;
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
    public static final class Speeds
    {
        public static final double ForwardIntakeMotorSpeed = 1;
        public static final double BackwardIntakeMotorSpeed = -0.8;
    
        public static final double DriveAutoAimFrictionOvercomeMotorSpeed = 0.55;
    
        public static final double CargoTransferMotorSpeed = -0.6;
        public static final double ReverseCargoTransferMotorSpeed = 0.6;
        public static final double CargoTransferShootSpeed = -0.6;
    
        public static final double ShootLowBottomMotorSpeed = 0.3;
        public static final double ShootLowTopMotorSpeed = 0.3;
    
        public static final double ShootHighBottomMotorSpeed = 0.5;
        public static final double ShootHighTopMotorSpeed = 0.5;
    
        public static final double WrongColorTopSpeed = 0.2;
        public static final double WrongColorBottomSpeed = 0.2;
    
        public static final double DriveToMidBarSpeed = 0.5;
        public static final double TelescopeExtendSpeed = 0.8;
        public static final double SlightlyExtendSpeed = -0.05;
        public static final double TelescopeRetractSpeed = -0.6;
    }

    public static final class Buttons
    {
        // Manipulator Buttons
        public static final int ToggleIntake = 2;
        public static final int Shoot = 1;
        public static final int AimCenterShoot = 4;
        //public static final int DebugCargoInLaser = 4;

        public static final int ShootLowGoal = 8;
        public static final int ShootHighGoal = 7;
        public static final int ShootWrongColor = 3;
        public static final int StopShooter = 6;

        public static final int ClimbMid = 9;
        public static final int ClimbHigh = 10;
        public static final int ClimbActivate = 5;
        public static final int ClimbReset = 11;
        public static final int ClimbEStop = 12;

        // Drive Buttons
        public static final int SwitchCamera = 13;
        public static final int SwitchCameraB = 7;
        public static final int RunDiagnostic = 1;
    }

    public static final class Diagnostic
    {
        public static final double IntakeVelocity = 10;
        public static final double CargoTransferVelocity = 10;
        public static final double TopShooterVelocity = 10;
        public static final double BottomShooterVelocity = 10;
        public static final double LeftFrontForward = 10;
        public static final double LeftFollowForward = 10;
        public static final double LeftFrontReverse = 10;
        public static final double LeftFollowReverse = 10;
        public static final double RightFrontForward = 10;
        public static final double RightFollowForward = 10;
        public static final double RightFrontReverse = 10;
        public static final double RightFollowReverse = 10;
        public static final double TelescopeVelocity = 10;

    }

    public static final class CargoTransfer
    {
        public static final double ClicksPerCargoLength = 11159 * 1.25d;//21k
    }

    public static final class Drive
    {
        public static final double GearRatio = 1 / 7.31;
        public static final double ClicksPerMotorRev = 42;
        public static final double ClicksPerRev = GearRatio * ClicksPerMotorRev;

        public static final double AimTolerance = 1.5; // degrees
        public static final double CenterTolerance = 3; // inches // was 2

        public static final double AimFrictionMotorSpeed = 0.27; // was 25
        public static final double CenterFrictionMotorSpeed = 0.15; // was 15

        public static final double ShooterDelay = 0.8;
        public static final double ShootTime = 1.5;

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
        public static final double ShootStopTimeDelay = 2; // was 3 and was too slow

        public static final double CanShootSpeedThreshold = 0.15;
        
        public static final double[] BottomShooterSpeedAtDistanceTable = { 2500, 2670, 2800, 3150, 3350, 3575, 3600, 3200, 3250, 3000, 3250, 3500 };
        public static final double[] TopShooterSpeedAtDistanceTable = { 12500, 13700, 14500, 14580, 14900, 15000, 15000,  15000, 16750, 16000, 20000, 20000 };
        public static final DataPoint[] ShooterSpeedAtDistanceTable = { DataPoint.c(0, 50), DataPoint.c(1, 60), DataPoint.c(2, 70.9), DataPoint.c(3, 77.4), DataPoint.c(4, 84.5), DataPoint.c(5, 92.4), DataPoint.c(6, 101.4), DataPoint.c(7, 108.5), DataPoint.c(8, 115.5), DataPoint.c(9, 123), DataPoint.c(10, 137), DataPoint.c(11, 154) };
    }

    public static final class Climber
    {
        public static final double ExtendTelesopeClicks = 105000 * 0.88;
        public static final double RetractTelesopeClicks = 105000 * 1.05 * 0.88;

        public static final double PitchThreshold = 3.5; // was 5

        // TODO find current draw for bagpipe falcon
        public static final double TelescopeMotorCurrentSafety = Double.MAX_VALUE;

        //public static final double DriveToMidBarSpeedA = 0.25;//0.325;
        //public static final double DriveToMidBarSpeedB = 0.25;
        public static final double SquareOnMidBarSpeed = 0.35;///////////////////////////////

        public static final double DriveToMidBarTime = 1.75 * 1.5;
    }

    public static final class Camera
    {
        public static final int PixelWidth = 160;
        public static final int PixelHeight = 120;
        public static final int FPS = 20;

        public static final int ServoStartingPositionIndex = 0;
        public static final double[] ServoPositions = { 70, 140 };
    }
}
