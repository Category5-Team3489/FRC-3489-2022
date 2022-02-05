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
    
    private UsbCamera camera;
    private VideoSink server;

    @Override
    public void robotInit() {

        camera = CameraServer.startAutomaticCapture(0);
        server = CameraServer.getServer();
        camera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

        ShuffleboardTab tab = Shuffleboard.getTab("3489 Camera");

        tab.add(server.getSource()).withWidget(BuiltInWidgets.kCameraStream).withSize(4, 4).withPosition(2, 0);

        server.setSource(camera);
    }
}
