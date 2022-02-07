package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;

public class Auto32 extends AutoBuilder {

    @Override
    public void build() {
        begin(head()
            .drive(1000)
            .concurrently(
                print("Started driving 2000 clicks"),
                drive(2000).onCompleted(setSignal("FinishedDrive2000")),
                waitOne(signal("FinishedDrive2000"))
                .drive(1000)
            ) // only exits concurrently block when everything started inside has completed
            .drive(2000)
        );
    }
    
}
