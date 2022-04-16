package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Diagnostic extends AutoBuilder {
    
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(diagnostic.intakeError())
        .concurrently(diagnostic.cargoTransferError())
        .concurrently(diagnostic.topShooterError())
        .concurrently(diagnostic.bottomShooterError())
        .concurrently(diagnostic.leftDriveForwardError())
        .concurrently(diagnostic.leftDriveReverseError())
        .concurrently(diagnostic.rightDriveForwardError())
        .concurrently(diagnostic.rightDriveReverse())
        .concurrently(diagnostic.telescopeError())
        .concurrently(diagnostic.navxError())
        .concurrently(diagnostic.intakeLaserError());
    
    }
        
}
