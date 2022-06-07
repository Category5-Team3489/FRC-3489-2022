package frc.robot.handlers;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.handlers.ShuffleboardHandler.Tab;

public class CameraHandler extends RobotHandler {
    @Override
    public void robotInit() {
        try {
            UsbCamera camera = CameraServer.startAutomaticCapture(0);
            //VideoSink server = CameraServer.getServer();

            HttpCamera limelightFeed = new HttpCamera("limelight", "http://limelight.local:5800/stream.mjpg");
    
            camera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            camera.setResolution(Constants.Camera.PixelWidth, Constants.Camera.PixelHeight);
            camera.setFPS(Constants.Camera.FPS);

            /*
            Tab.Main.get()
                .add(server.getSource())
                .withWidget(BuiltInWidgets.kCameraStream)
                .withSize(4, 4)
                .withPosition(0, 0);
            */
    
            Tab.Main.get()
                .add(limelightFeed)
                .withWidget(BuiltInWidgets.kCameraStream)
                .withSize(5, 4)
                .withPosition(0, 0);

        } catch (Exception e) {
            System.out.println("[CameraHandler] Couldn't init cameras");
        }
    }
}
