// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private XboxController controller = new XboxController(0);
  
  private Solenoid fireGreen = new Solenoid(19, PneumaticsModuleType.CTREPCM, 1);
  private Solenoid fireBlue = new Solenoid(19, PneumaticsModuleType.CTREPCM, 2);
  private Solenoid fireYellow = new Solenoid(19, PneumaticsModuleType.CTREPCM, 3);
  private Solenoid fireRed = new Solenoid(19, PneumaticsModuleType.CTREPCM, 0);

  private WPI_TalonSRX frontLeft = new WPI_TalonSRX(1);
  private WPI_TalonSRX frontRight = new WPI_TalonSRX(2);
  private WPI_TalonSRX backLeft = new WPI_TalonSRX(3);
  private WPI_TalonSRX backRight = new WPI_TalonSRX(4);
  private DifferentialDrive differentialDrive;

  private boolean isFront = true;
  
  @Override
  public void robotInit() {
    differentialDrive = new DifferentialDrive(frontLeft, frontRight);
    frontRight.setInverted(true);
    backRight.setInverted(true);
    backLeft.follow(frontLeft);
    backRight.follow(frontRight);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    double leftY = controller.getLeftY();
    double rightY = controller.getRawAxis(3);
    
    if (Math.abs(leftY) < 0.1) leftY = 0;
    if (Math.abs(rightY) < 0.1) rightY = 0;

    if (controller.getStartButtonPressed()) {
      isFront = !isFront;
    }

    double speedControl = 1;
    switch (DriverStation.getLocation()) {
      case 1:
          speedControl = 1.00;
        break;
      case 2:
          speedControl = 0.85;
        break;
      case 3:
          speedControl = 0.70;
        break;
    }
    
    if (isFront) {
      differentialDrive.tankDrive(-leftY*speedControl, -rightY*speedControl);
    } 
    else {
      differentialDrive.tankDrive(rightY*speedControl, leftY*speedControl);
    }
    
    shoot();
  }

  public void shoot() {
    if (controller.getRawButton(7) && controller.getRawButton(8)) {
      fireBlue.set(controller.getXButton());
      fireRed.set(controller.getBButton());
      fireGreen.set(controller.getAButton());
      fireYellow.set(controller.getYButton());
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
