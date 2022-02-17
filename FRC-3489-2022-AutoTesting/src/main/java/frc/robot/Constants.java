package frc.robot;

import frc.robot.types.RobotType;

public final class Constants {

    // State
    public static final boolean HasCameras = false;
    public static final RobotType SelectedRobot = RobotType.IonV;
    public static final boolean SafetiesEnabled = false;

    // Speeds
    public static final double IntakeMotorSpeed = 0.6;
    public static final double ReverseIntakeMotorSpeed = -0.6;
    public static final double CargoTransferMotorSpeed = 0.6;
    public static final double ReverseCargoTransferMotorSpeed = -0.6;

    public static final double ShootHighBottomMotorSpeed = 0.6;
    public static final double ShootHighTopMotorSpeed = 0.6;
    public static final double ShootLowBottomMotorSpeed = 0.2;
    public static final double ShootLowTopMotorSpeed = 0.2;

    // Buttons
    public static final int SetShooterLowGoalButton = 123213;
    public static final int SetShooterHighGoalButton = 123213;
    public static final int StopShooterButton = 123213;
    public static final int ToggleIntakeButton = 123213;
    public static final int ShootButton = 1;

    // Clicks
    public static final double ClicksPerCargoLength = 1000;

    public final class Auto {
        
        public static final double DriveClicksPerInch = 4096.0 / (2 * Math.PI * 3);
        public static final double DriveClicksPerFoot = 12 * DriveClicksPerInch;
        public static final double CargoTransferClicksPerBall = ClicksPerCargoLength;
    }
}
