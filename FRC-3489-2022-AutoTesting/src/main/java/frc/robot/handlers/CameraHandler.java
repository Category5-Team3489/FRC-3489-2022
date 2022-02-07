package frc.robot.handlers;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.framework.RobotHandler;

public class CameraHandler extends RobotHandler {
    
    private UsbCamera cameraA;
    private UsbCamera cameraB;
    private VideoSink server;

    @Override
    public void robotInit() {
        /*
        try {
            cameraA = CameraServer.startAutomaticCapture(0);
            cameraB = CameraServer.startAutomaticCapture(1);
            server = CameraServer.getServer();
    
            cameraA.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            cameraB.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    
            ShuffleboardTab tab = Shuffleboard.getTab("3489 Camera");
    
            tab.add(server.getSource()).withWidget(BuiltInWidgets.kCameraStream).withSize(4, 4).withPosition(2, 0);
    
            setCamera(false);
        } catch (Exception e) {
            System.out.println("[CameraHandler] Couldn't init cameras");
        }
        */
    }

    public void setCamera(boolean isCameraB) {
        /*
        try {
            if (isCameraB)
                server.setSource(cameraB);
            else
                server.setSource(cameraA);
        } catch (Exception e) {
            System.out.println("[CameraHandler] Had issue switching cameras");
        }
        */
    }
}
