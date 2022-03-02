package frc.robot.handlers;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CameraHandler extends RobotHandler {
    
    private UsbCamera cameraA;
    private UsbCamera cameraB;
    private VideoSink server;

    @Override
    public void robotInit() {
        //Begin For Debugging Only
        if (!Constants.HasCameras) return;
        //End For Debugging Only

        try {
            cameraA = CameraServer.startAutomaticCapture(0);
            cameraB = CameraServer.startAutomaticCapture(1);
            server = CameraServer.getServer();
    
            cameraA.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            cameraB.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    
            shuffleboardHandler.createCameraWidget(server.getSource());
    
            setCamera(true);
        } catch (Exception e) {
            System.out.println("[CameraHandler] Couldn't init cameras");
        }
    }

    public void setCamera(boolean isCameraA) {
        //Begin Debug
        if (!Constants.HasCameras) return;
        //End Debug

        try {
            if (isCameraA)
                server.setSource(cameraA);
            else
                server.setSource(cameraB);
        } catch (Exception e) {
            System.out.println("[CameraHandler] Had issue switching cameras");
        }
    }
}
