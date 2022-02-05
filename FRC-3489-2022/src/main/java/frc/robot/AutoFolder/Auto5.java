public class Auto5 {
    public void Autonomous5Init() {
        resetEncoderPosition();
    }

    //classes Needed
    private DriveHandler driveHandler;
    private ComponentsContainer container;
    private Auto3 auto3;

    private int currentStep = 1;

    private static final double driveForwardSpeed = 0.65;
    private static final double leftMotorForTurn = 0.2;
    private static final double rightMotorForTurn = 0.4;
        
    private static final double driveForwardClicks = 4000;
    private static final double turnLeftClicks = 4000;

    private void resetEncoderPosition() {
        container.frontLeftDrive.setSelectedSensorPosition(0);
    }

    private double getEncoderPositionAbs() {
        return Math.abs(container.frontLeftDrive.getSelectedSensorPosition());
    }

    private void driveForward(){
        if (getEncoderPositionAbs() < driveForwardClicks){
            driveHandler.tankDrive(driveForwardSpeed, driveForwardSpeed);
        } 
        else {
            resetEncoderPosition();
            driveHandler.stop();
            currentStep ++;
        }
    }

    private void turnLeft(){
        if (getEncoderPositionAbs() < turnLeftClicks){
            driveHandler.tankDrive(leftMotorForTurn, rightMotorForTurn);
        } 
        else {
            resetEncoderPosition();
            driveHandler.stop();
            currentStep ++;
        }
    }

    public void autonomous5periodic(){
        switch(currentStep){
            case 1:
                auto3.auto3();
                break;
            case 2:
                turnLeft();
                break;
            case 3:
                driveForward();
                break;
        }
    }







}