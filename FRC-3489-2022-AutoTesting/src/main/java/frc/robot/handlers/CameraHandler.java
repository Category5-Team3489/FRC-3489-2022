package frc.robot.handlers;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CameraHandler extends RobotHandler {
    
    private UsbCamera cameraA;
    private VideoSink server;

    @Override
    public void robotInit() {
        try {
            cameraA = CameraServer.startAutomaticCapture(0);
            server = CameraServer.getServer();
    
            cameraA.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            cameraA.setResolution(Constants.CameraPixelWidth, Constants.CameraPixelHeight);
            cameraA.setFPS(Constants.CameraFPS);
    
            shuffleboardHandler.createCameraWidget(server.getSource());
        } catch (Exception e) {
            System.out.println("[CameraHandler] Couldn't init cameras");
        }
    }
}
