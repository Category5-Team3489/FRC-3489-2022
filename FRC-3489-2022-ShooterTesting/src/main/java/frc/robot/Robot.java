package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private WPI_TalonFX bottomFalcon = new WPI_TalonFX(16);
  private WPI_TalonFX topFalcon = new WPI_TalonFX(15);

  private Joystick joystick = new Joystick(2);

  private double bottomSpeed = 0;
  private double topSpeed = 0;

  private boolean isControllingBottom = true;

  private static final int ToggleControlButton = 11;
  private static final int MirrorBottomSpeedButton = 12;
  private static final int MirrorTopSpeedButton = 10;

  private static final double SpeedDeltaConstant = 1.0 / 50.0;

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {

    if (joystick.getRawButtonPressed(7))
      setSelected(0.6);
    if (joystick.getRawButtonPressed(8))
      setSelected(0.7);
    if (joystick.getRawButtonPressed(9))
      setSelected(0.8);

    if (joystick.getRawButtonPressed(ToggleControlButton)) {
      isControllingBottom = !isControllingBottom;
    }

    if (joystick.getRawButtonPressed(MirrorBottomSpeedButton))
      topSpeed = bottomSpeed;
    if (joystick.getRawButtonPressed(MirrorTopSpeedButton))
      bottomSpeed = topSpeed;

    if (joystick.getRawButton(1))
    {
      topSpeed = 0;
      bottomSpeed = 0;
    }

    if (joystick.getRawButtonPressed(3))
      setSelected(-0.025 + getSelected());
    if (joystick.getRawButtonPressed(4))
      setSelected(0.025 + getSelected());
    if (joystick.getRawButtonPressed(5))
      setSelected(-0.05 + getSelected());
    if (joystick.getRawButtonPressed(6))
      setSelected(0.05 + getSelected());

    setMotors();

    System.out.println("Is Controlling Bottom? " + isControllingBottom);
    System.out.println("Set (B, T): " + ((int)(bottomSpeed * 1000)) / 10d + " : " + (int)(topSpeed * 1000) / 10d);
    System.out.println("Bottom RPS: " + ((Math.abs(bottomFalcon.getSelectedSensorVelocity()) * 10.0) / 4096.0));
    System.out.println("Top RPS: " + ((Math.abs(topFalcon.getSelectedSensorVelocity()) * 10.0) / 4096.0));

    double joystickY = joystick.getY();
    if (Math.abs(joystickY) < 0.1) return;
    setSelected((-joystickY * SpeedDeltaConstant) + getSelected());
  }

  private void setSelected(double speed) {
    if (isControllingBottom) bottomSpeed = speed;
    else topSpeed = speed;
  }

  private double getSelected() {
    return isControllingBottom ? bottomSpeed : topSpeed;
  }

  private void setMotors() {
    bottomFalcon.set(-bottomSpeed);
    topFalcon.set(topSpeed);
  }

}
