package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;

public class Auto32 extends AutoBuilder {

    @Override
    public void build() {

        // later add signals for links between instructions


        
        begin()
        .drive(1000)
        .concurrently(
            print("Started driving 2000 clicks"),
            drive(2000).onCompleted(setSignal("FinishedDrive2000"))
        )
        .pause(Double.MAX_VALUE).completeOn(signal("FinishedDrive2000"))
        .drive(2000);

        // add and concurrent instruction completes when all inner are done?
        //until
        //.until
    }
    
}
