package frc.robot.auto.autos;

import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto6 extends AutoBuilder {
    
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            shoot(0.9, 3), //Set shooter to 90% for 3 seconds
            pause(1) //Pause for 1 second before shooting ball
                .cargoTransfer(0.5,5 * Auto.CargoTransferClicksPerBall) //Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .pause(1) //Wait one second
        .concurrently(
            drive(0.65, 6 * Auto.DriveClicksPerFoot), //Drive at 65% for 6 feet
            intake(.6, 10) //intake at 60% for 10 seconde
        )
        .pause(1) //wait for one second
        .turn(.5, -70) //turn right 70 degrees at 50% power
        .drive(.65, 2 * Auto.DriveClicksPerFoot); //drive forward 2 feet at 65%

        return first;
    }

}


