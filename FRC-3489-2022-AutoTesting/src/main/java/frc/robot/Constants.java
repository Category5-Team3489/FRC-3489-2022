package frc.robot;

import frc.robot.types.RobotType;

public final class Constants {

    public static final boolean HasCameras = false;
    public static final RobotType SelectedRobot = RobotType.IonV;
    public static final boolean SafetiesEnabled = false;

    public final class Auto {
        
        public static final double DriveClicksPerInch = 217.3;
        public static final double DriveClicksPerFoot = 12 * DriveClicksPerInch;
        public static final double CargoTransferClicksPerBall = 1000;
    }
}
