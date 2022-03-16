package frc.robot.auto.autos;

//import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto32 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        //.drive(0.65, 4 * Constants.ClicksPerFootDriven);
        .turn(0.85, 180);

        /*
        first
        .left(0.1, 3)
        .right(0.1, 3)
        .pause(2)
        .concurrently(
            left(0.1, 3),
            right(0.1, 3)
        )
        .pause(2)
        .asynchronously(
            left(0.1, 3),
            right(0.1, 3).onCompleted(setTrigger("async done"))
        )
        .waitUntil(getTrigger("async done"))
        .print("Still going?");
        */
        
        return first;
    }
    
}
