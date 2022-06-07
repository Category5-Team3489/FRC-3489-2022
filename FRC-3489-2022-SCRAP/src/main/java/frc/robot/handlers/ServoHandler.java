package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class ServoHandler extends RobotHandler {

    private boolean positionIndexDirectionReversed = false;
    private int positionIndex = Constants.Servo.StartingPositionIndex;

    @Override
    public void teleopInit() {
        components.cameraServo.setAngle(Constants.Servo.Positions[Constants.Servo.StartingPositionIndex]);
        positionIndex = Constants.Servo.StartingPositionIndex;
    }

    @Override
    public void teleopPeriodic() {
        /*
        double throttle = components.manipulatorJoystick.getThrottle();
        components.cameraServo.setAngle(GeneralUtils.lerp(70, 140, (throttle + 1d) / 2d));
        */
        if(button.shouldToggleServoPosition()){
            nextServoPosition();
        }
    }

    private void nextServoPosition() {
        if (!positionIndexDirectionReversed)
            positionIndex++;
        else
            positionIndex--;
        if (positionIndex == Constants.Servo.Positions.length) {
            positionIndexDirectionReversed = true;
            positionIndex = Constants.Servo.Positions.length - 2;
        }
        else if (positionIndex == -1) {
            positionIndexDirectionReversed = false;
            positionIndex = 1;
        }
        components.cameraServo.setAngle(Constants.Servo.Positions[positionIndex]);
    }
}
