// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {

  private Joystick leftDrive = new Joystick(0);
  private Joystick rightDrive = new Joystick(1);
  private Joystick manipulator = new Joystick(2);

  private WPI_TalonSRX leftFrontDriveMotor = new WPI_TalonSRX(1);
  private WPI_TalonSRX rightFrontDriveMotor = new WPI_TalonSRX(2);
  private WPI_TalonSRX leftFollowerDriveMotor = new WPI_TalonSRX(3);
  private WPI_TalonSRX rightFollowerDriveMotor = new WPI_TalonSRX(4);
  private DifferentialDrive drive;

  private WPI_TalonFX shooterBottom = new WPI_TalonFX(7); // 7
  private WPI_TalonFX shooterTop = new WPI_TalonFX(8); // 8
  private WPI_TalonSRX cargoTransfer = new WPI_TalonSRX(5); // 5
  private WPI_TalonSRX intake = new WPI_TalonSRX(6); // 6
  // 9

  // Manipulator
  private static final int Shooter = 7;
  private static final int Cargo = 9;
  private static final int Intake = 11;
  private static final int ShooterKill = 8;
  private static final int CargoKill = 10;
  private static final int IntakeKill = 12;
  private static final int EStop = 2;

  // Drive
  private static final int SwitchFront = 13;
  private static final int ClimberStepA = 5;
  private static final int ClimberStepB = 6;
  private static final int ClimberStepC = 7;
  private static final int ResetClimberEncoder = 8;
  private static final int ResetClimber = 9;

  private Subsystem activeSubsystem = Subsystem.Intake;

  private boolean isFrontSwitched = false;

  private double shooterSpeed = 0;
  private double cargoSpeed = 0;
  private double intakeSpeed = 0;

  private SlewRateLimiter leftLimiter = new SlewRateLimiter(10);
  private SlewRateLimiter rightLimiter = new SlewRateLimiter(10);

  private Solenoid lowerSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 2);
  private Solenoid upperSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 3);
  private Solenoid hookSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 4);
  private Solenoid brakeSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 5);

  private WPI_TalonFX climberMotor = new WPI_TalonFX(9);


  private long loop = 0;

  enum Subsystem
  {
    Shooter,
    Cargo,
    Intake
  }

  @Override
  public void robotInit() {
    rightFrontDriveMotor.setInverted(true);
    rightFollowerDriveMotor.setInverted(true);
    leftFollowerDriveMotor.follow(leftFrontDriveMotor);
    rightFollowerDriveMotor.follow(rightFrontDriveMotor);

    drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor);
  }

  @Override
  public void teleopInit() {
    shooterSpeed = 0;
    cargoSpeed = 0;
    intakeSpeed = 0;

    initClimber();
  }

  private void initClimber() {
    brakeSolenoid.set(false);
    lowerSolenoid.set(false);
    upperSolenoid.set(false);
    hookSolenoid.set(false);
  }

  private boolean shouldSwitchFront() {
    return leftDrive.getRawButtonPressed(SwitchFront) || rightDrive.getRawButtonPressed(SwitchFront);
  }

  @Override
  public void teleopPeriodic() {
    if (getDriveButton(ClimberStepA)) {
      climberStepA();
    }
    if (getDriveButton(ClimberStepB)) {
      climberStepB();
    }
    if (getDriveButton(ClimberStepC)) {
      climberStepC();
    }
    if (getDriveButton(ResetClimberEncoder)) {
      climberMotor.setSelectedSensorPosition(0);
    }
    if (getDriveButton(ResetClimber)) {
      initClimber();
    }

    double magnitude = (leftDrive.getThrottle() - 1) / -2;
    double winch = lerp(-0.6, 0.6, magnitude);

    if (leftDrive.getPOV() == 180) { // Down
      brakeSolenoid.set(true);
      climberMotor.set(-winch);
    }
    else if (leftDrive.getPOV() == 0) { // Up
      brakeSolenoid.set(true);
      climberMotor.set(winch);
    }
    else { // Off
      brakeSolenoid.set(false);
      climberMotor.stopMotor();
    }

    if (shouldSwitchFront())
      isFrontSwitched = !isFrontSwitched;
    double leftY = leftDrive.getY();
    double rightY = rightDrive.getY();
    double leftSpeed = leftLimiter.calculate(leftY);
    double rightSpeed = rightLimiter.calculate(rightY);
    if (Math.abs(leftY) < 0.1) leftSpeed = 0;
    if (Math.abs(rightY) < 0.1) rightSpeed = 0;
    if (isFrontSwitched)
      drive.tankDrive(rightSpeed, leftSpeed);
    else
      drive.tankDrive(-leftSpeed, -rightSpeed);

    if (manipulator.getRawButton(Shooter))
      activeSubsystem = Subsystem.Shooter;
    if (manipulator.getRawButton(Cargo))
      activeSubsystem = Subsystem.Cargo;
    if (manipulator.getRawButton(Intake))
      activeSubsystem = Subsystem.Intake;

    if (manipulator.getRawButton(ShooterKill))
      shooterSpeed = 0;
    if (manipulator.getRawButton(CargoKill))
      cargoSpeed = 0;
    if (manipulator.getRawButton(IntakeKill))
      intakeSpeed = 0;

    if (manipulator.getRawButton(EStop)) {
      shooterSpeed = 0;
      cargoSpeed = 0;
      intakeSpeed = 0;
    }

    controlActiveSubsystem();

    switch (activeSubsystem) {
      case Shooter:
        shooterBottom.set(-shooterSpeed);
        shooterTop.set(shooterSpeed);
        break;
      case Cargo:
        cargoTransfer.set(cargoSpeed);
        break;
      case Intake:
        intake.set(intakeSpeed);
        break;
    }

    if (loop % 50 == 0) {
      System.out.println("Shooter: " + ((int)(shooterSpeed * 100)));
      System.out.println("Cargo: " + ((int)(cargoSpeed * 100)));
      System.out.println("Intake: " + ((int)(intakeSpeed * 100)));
      System.out.println("Climber: " + climberMotor.getSelectedSensorPosition());
    }
    loop++;
  }

  private void controlActiveSubsystem() {

    if (Math.abs(manipulator.getY()) < 0.2) return;

    switch (activeSubsystem) {
      case Shooter:
        shooterSpeed += getAdjust();
        break;
      case Cargo:
        cargoSpeed += getAdjust();
        break;
      case Intake:
        intakeSpeed += getAdjust();
        break;
    }

    shooterSpeed = clamp(shooterSpeed, -1, 1);
    cargoSpeed = clamp(cargoSpeed, -1, 1);
    intakeSpeed = clamp(intakeSpeed, -1, 1);
  }

  private double getAdjust() {
    return manipulator.getY() * 0.005;
  }

  private static double clamp(double val, double min, double max) {
    return Math.max(min, Math.min(max, val));
  }

  @Override
  public void disabledInit() {
    initClimber();
  }

  @Override
  public void disabledPeriodic() {

  }

  private void climberStepA() {
    lowerSolenoid.set(true);
  }

  private void climberStepB() {
    upperSolenoid.set(true);
  }

  private void climberStepC() {
    hookSolenoid.set(true);
  }

  private boolean canStepA() {
    boolean upperOff = upperSolenoid.get();
    boolean hooksOn = hookSolenoid.get();
    return upperOff && hooksOn;
  }

  private boolean canStepB() {
    boolean lowerOn = lowerSolenoid.get();
    boolean hooksOn = hookSolenoid.get();
    return lowerOn && hooksOn;
  }

  private boolean canStepC() {
    boolean lowerOn = lowerSolenoid.get();
    boolean upperOn = upperSolenoid.get();
    return lowerOn && upperOn;
  }

  private boolean getDriveButton(int button) {
    return leftDrive.getRawButton(button) || rightDrive.getRawButton(button);
  }

  public void simulationPeriodic() {

  }

  private double lerp(double a, double b, double f)
  {
      return a + f * (b - a);
  }
}
