package frc.robot;

public class AutoSquareDriveHandler {
    
    private ComponentsContainer components;
    private DriveHandler driveHandler;

    private int currentStep = 1;

    private static final double DriveForwardClicks = 4000;
    private static final double TurnLeftClicks = 2000;

    private static final double DriveForwardSpeed = 0.65;
    private static final double TurnLeftSpeed = 0.5;

    public AutoSquareDriveHandler(ComponentsContainer components, DriveHandler driveHandler) {
        this.components = components;
        this.driveHandler = driveHandler;
    }

    public void autonomousInit() {
        resetEncoderPosition();
    }

    private void resetEncoderPosition() {
        components.frontLeftDrive.setSelectedSensorPosition(0);
    }

    private double getEncoderPositionAbs() {
        return Math.abs(components.frontLeftDrive.getSelectedSensorPosition());
    }

    private void driveForward() {
        if (getEncoderPositionAbs() < DriveForwardClicks) {
            driveHandler.tankDrive(DriveForwardSpeed, DriveForwardSpeed);
        }
        else {
            currentStep++;
            resetEncoderPosition();
        }
    }

    private void turnLeft() {
        if (getEncoderPositionAbs() < TurnLeftClicks) {
            driveHandler.tankDrive(TurnLeftSpeed, -TurnLeftSpeed);
        }
        else {
            currentStep++;
            resetEncoderPosition();
        }
    }

    public void autonomousPeriodic() {
        switch (currentStep) {
            case 1: // Drive forward
                driveForward();
                break;
            case 2: // Turn left
                turnLeft();
                break;
            case 3: // Drive forward
                driveForward();
                break;
            case 4: // Turn left
                turnLeft();
                break;
            case 5: // Drive forward
                driveForward();
                break;
            case 6: // Turn left
                turnLeft();
                break;
            case 7: // Drive forward
                driveForward();
                break;
            case 8: // Stop
                driveHandler.stop();
                break;
        }
    }
}
