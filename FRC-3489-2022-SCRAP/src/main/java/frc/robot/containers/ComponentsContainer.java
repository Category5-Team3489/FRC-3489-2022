package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ComponentsContainer {

    // Drive Components
    public CANSparkMax leftFrontDriveMotor = new CANSparkMax(1, MotorType.kBrushless);
    public CANSparkMax rightFrontDriveMotor = new CANSparkMax(2, MotorType.kBrushless);
    public CANSparkMax leftFollowerDriveMotor = new CANSparkMax(3, MotorType.kBrushless);
    public CANSparkMax rightFollowerDriveMotor = new CANSparkMax(4, MotorType.kBrushless);
    public DifferentialDrive drive;

    // Intake Components
    public WPI_TalonFX intakeMotor = new WPI_TalonFX(6);
    public Solenoid intakeSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 6);

    // Cargo Transfer Components
    public WPI_TalonSRX cargoTransferMotor = new WPI_TalonSRX(5);

    // Shooter Components
    public WPI_TalonFX bottomShooterMotor = new WPI_TalonFX(7);
    public WPI_TalonFX topShooterMotor = new WPI_TalonFX(8);

    // Joysticks
    public Joystick leftDriveJoystick = new Joystick(0);
    public Joystick rightDriveJoystick = new Joystick(1);
    public Joystick manipulatorJoystick = new Joystick(2);

    // Sensors
    public DigitalInput intakeLaserSensor = new DigitalInput(0);
}
