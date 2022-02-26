package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto6 extends AutoBuilder {
    
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            shoot(0.65, 3), //Set shooter to 65% for 3 seconds
            pause(1) //Pause for 1 second before shooting ball
                .cargoTransfer(0.5,5 * Constants.ClicksPerCargoLength) //Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .pause(1) //Wait one second
        .concurrently(
            drive(0.65, 4.5 * Constants.DriveClicksPerFoot), //Drive at 65% for 4.5 feet
            intake(0.6, 10) //intake at 60% speed for 10 seconde
        )
        .pause(1) //wait for one second
        .turn(.5, -100) //turn right 100 degrees at 50% speed
        .drive(.65, 4.5 * Constants.DriveClicksPerFoot); //drive forward 4.5 feet at 65% speed

        return first;
    }

}


