package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveHandler {
    
    public DifferentialDrive differentialDrive;

    public DriveHandler(ComponentsContainer components)
    {
        components.backLeftDrive.follow(components.frontLeftDrive);
        components.backRightDrive.follow(components.frontRightDrive);
        components.frontRightDrive.setInverted(true);
        differentialDrive = new DifferentialDrive(components.frontLeftDrive, components.frontRightDrive);
    }
}
