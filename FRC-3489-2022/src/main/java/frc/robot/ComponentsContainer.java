package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

/*
    Contains all of the components on the robot controlled by the RoboRIO
*/
public class ComponentsContainer {
    
    //Drive Motors
    public WPI_TalonSRX frontLeftDrive = new WPI_TalonSRX(Constants.FrontLeftDriveId);
    public WPI_TalonSRX frontRightDrive = new WPI_TalonSRX(Constants.FrontRightDriveId);
    public WPI_TalonSRX backLeftDrive = new WPI_TalonSRX(Constants.BackLeftDriveId);
    public WPI_TalonSRX backRightDrive = new WPI_TalonSRX(Constants.BackRightDriveId);

    // intake motor
    public WPI_TalonSRX intakeMotor = new WPI_TalonSRX(Constants.IntakeMotorId);

    //shooter components
    public WPI_TalonFX shooterTop = new WPI_TalonFX(Constants.ShooterTopId);
    public WPI_TalonFX shooterBottom = new WPI_TalonFX(Constants.ShooterBottomId);
        
    

    // Joysticks
    public Joystick leftDriveJoystick = new Joystick(Constants.LeftDriveJoystick);
    public Joystick rightDriveJoystick = new Joystick(Constants.RightDriveJoystick);
    public Joystick manipulatorJoystick = new Joystick(Constants.ManipulatorJoystick);
    
}

