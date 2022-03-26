package frc.robot.handlers;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CameraHandler extends RobotHandler {

    private boolean servoPositionIndexDirectionReversed = false;
    private int servoPositionIndex = Constants.Camera.ServoStartingPositionIndex;

    @Override
    public void robotInit() {
        try {
            UsbCamera camera = CameraServer.startAutomaticCapture(0);
            VideoSink server = CameraServer.getServer();

            HttpCamera limelightFeed = new HttpCamera("limelight", "http://limelight.local:5800/stream.mjpg");
    
            camera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            camera.setResolution(Constants.CameraPixelWidth, Constants.CameraPixelHeight);
            camera.setFPS(Constants.CameraFPS);
    
            shuffleboardHandler.createCameraWidget(server.getSource());
            shuffleboardHandler.createLimelightCameraWidget(limelightFeed);
        } catch (Exception e) {
            System.out.println("[CameraHandler] Couldn't init cameras");
        }
    }

    @Override
    public void teleopPeriodic() {
        if(shouldSwitchCamera()){
            nextServoPosition();
        }
    }

    public void nextServoPosition() {
        if (!servoPositionIndexDirectionReversed)
            servoPositionIndex++;
        else
            servoPositionIndex--;
        if (servoPositionIndex == Constants.Camera.ServoPositions.length) {
            servoPositionIndexDirectionReversed = true;
            servoPositionIndex = Constants.Camera.ServoPositions.length - 2;
        }
        else if (servoPositionIndex == -1) {
            servoPositionIndexDirectionReversed = false;
            servoPositionIndex = 1;
        }
        components.cameraServo.setAngle(Constants.Camera.ServoPositions[servoPositionIndex]);
    }

    private boolean shouldSwitchCamera() {
        return components.rightDriveJoystick.getRawButtonPressed(Constants.ButtonSwitchCamera) ||
            components.rightDriveJoystick.getRawButtonPressed(Constants.ButtonSwitchCameraB);
    }
}
