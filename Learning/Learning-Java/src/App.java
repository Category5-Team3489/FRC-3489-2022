public class App {

    public static void main(String[] args) {

        /*
            UsbCamera camera = CameraServer.startAutomaticCapture(0);
            VideoSink server = CameraServer.getServer();

            HttpCamera limelightFeed = new HttpCamera("limelight", "http://limelight.local:5800/stream.mjpg");
    
            camera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            camera.setResolution(Constants.Camera.PixelWidth, Constants.Camera.PixelHeight);
            camera.setFPS(Constants.Camera.FPS);
    
            shuffleboardHandler.createCameraWidget(server.getSource());
            shuffleboardHandler.createLimelightCameraWidget(limelightFeed);
        */

        // visibility type name;
        // visibility type name = value;

        
        String test = "hi";
        Test Test = new Test();

        String methodResult = Test.testString(test);

        System.out.println("Hello, World!");
        System.out.println(methodResult);

    }
}
