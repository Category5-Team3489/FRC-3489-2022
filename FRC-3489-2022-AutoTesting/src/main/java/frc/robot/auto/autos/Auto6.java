package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto6 extends AutoBuilder {
    
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        
        .concurrently( //drive forward while intaking
            drive(0.65, 4.5 * Constants.ClicksPerFootDriven), //Drive at 65% for 4.5 feet
            intake(6) //intake at 60% speed for 6 seconde
        )
        .drive(-0.65, 4.5 * Constants.ClicksPerFootDriven)
        .pause(1) //Wait one second
        .concurrently( //shoot high
            shoot(0.5, 3), //Set shooter to 50% for 3 seconds
            pause(1) //Pause for 1 second before shooting ball
                .cargoTransfer(0.5,5 * Constants.ClicksPerCargoLength) //Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .pause(1) //wait for one second
        .turn(.5, -100) //turn right 100 degrees at 50% speed
        .pause(1)
        .drive(.65, 4.5 * Constants.ClicksPerFootDriven); //drive forward 4.5 feet at 65% speed

        return first;
    }

}


