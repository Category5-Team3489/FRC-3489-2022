package frc.robot.handlers;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class AutoShootHandler extends RobotHandler {
    public enum State {
        Driving,
        Aiming,
        Accelerating,
        Shooting,
    }

    // State
    private State currentState = State.Driving;

    private double lastDistanceEstimate = Double.MAX_VALUE;
    private PIDController aimController = new PIDController(Constants.AutoShoot.AimP, Constants.AutoShoot.AimI, Constants.AutoShoot.AimD);
    private PIDController holdAimController = new PIDController(Constants.AutoShoot.HoldAimP, Constants.AutoShoot.HoldAimI, Constants.AutoShoot.HoldAimD);

    @Override
    public void robotInit() {
        aimController.setSetpoint(0);
        aimController.setTolerance(Constants.AutoShoot.AimTolerance);

        holdAimController.setSetpoint(0);
        holdAimController.setTolerance(Constants.AutoShoot.HoldAimTolerance);

        setState(State.Driving);
    }

    @Override
    public void robotPeriodic() {
        lastDistanceEstimate = limelight.getDistanceEstimate();
        if (currentState != State.Driving && lastDistanceEstimate != Double.MAX_VALUE) {
            shooter.setShooterAtDistance(lastDistanceEstimate);
        }

        // TODO want shooter spinup when robot has 2 balls?

        switch (currentState) {
            case Driving:
                return;
            case Aiming:
                aiming();
                break;
            case Accelerating:
                accelerating();
                break;
            case Shooting:
                shooting();
                break;
        }
    }

    private void aiming() {
        if (limelight.isXValid()) {
            double output = filterOutput(aimController.calculate(limelight.getX()), Constants.AutoShoot.AimFrictionConstant);
            if (aimController.atSetpoint()) {
                components.drive.stopMotor();
                setState(State.Accelerating);
            }
            else {
                components.drive.tankDrive(-output, output);
            }
        }
        else {
            initAiming();
        }
    }
    private void accelerating() {
        if (aimAgainIfNeeded()) {
            return;
        }
        holdAim();
        if (shooter.canShoot()) {
            setState(State.Shooting);
        }
    }
    private void shooting() {
        if (aimAgainIfNeeded()) {
            return;
        }
        holdAim();

        // need to check shooter.canShoot again?
    }

    private void holdAim() {
        if (limelight.isXValid()) {
            double output = filterOutput(holdAimController.calculate(limelight.getX()), Constants.AutoShoot.HoldAimFrictionConstant);
            if (holdAimController.atSetpoint()) {
                components.drive.stopMotor();
            }
            else {
                components.drive.tankDrive(-output, output);
            }
        }
        else {
           holdAimController.reset();
        }
    }

    // #region State Management
    public boolean setState(State state) {
        if (currentState != state) {
            currentState = state;
            initState();
            return true;
        }
        return false;
    }
    private void initState() {
        switch (currentState) {
            case Driving:
                initDriving();
                break;
            case Aiming:
                initAiming();
                break;
            case Accelerating:
                initAccelerating();
                break;
            case Shooting:
                initShooting();
                break;
        }
    }
    private void initDriving() {
        limelight.setState(LimelightHandler.State.Off);
        shooter.setStopped();
    }
    private void initAiming() {
        limelight.setState(LimelightHandler.State.AutoAim);
        aimController.reset();
    }
    private void initAccelerating() {
        holdAimController.reset();
    }
    private void initShooting() {
        
    }

    public boolean isAutoShooting() {
        return currentState != State.Driving;
    }

    // Handling shared control of the drive train
    public void gaveControl() {
    
    }
    public void tookControl() {

    }
    // #endregion State Management

    private boolean aimAgainIfNeeded() {
        if (limelight.isXValid()) {
            if (Math.abs(limelight.getX()) < Constants.AutoShoot.AimAgainThreshold) {
                return false;
            }
        }
        setState(State.Aiming);
        return true;
    }

    private double filterOutput(double output, double frictionConstant) {
        double signedFrictionConstant = output >= 0 ? frictionConstant : -frictionConstant;
        return signedFrictionConstant + output;
    }
}
