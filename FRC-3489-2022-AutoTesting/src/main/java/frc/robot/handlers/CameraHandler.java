package frc.robot.handlers;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CameraHandler extends RobotHandler {

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
}
