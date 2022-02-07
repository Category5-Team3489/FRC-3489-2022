package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto32 extends AutoBuilder {

    @Override
    public void build() {

        AutoInstruction head = head();

        head
        .left(0.1, 3)
        .right(0.1, 3);

        begin(head);


        //simpleTest();
        //pauseTest();
        //concurrencyTest();
        //asynchronyTest();
        //printTest();

        // only exits concurrently block when everything started inside has completed
    }

    private void simpleTest() {
        begin(head()
            //.waitOne(signal("simpleTest"))
            .left(0.1, 3)
            .right(0.1, 3)
            //.onCompleted(setSignal("pauseTest"))
        );
    }

    private void pauseTest() {
        begin(head()
            .waitOne(signal("pauseTest"))
            .left(0.1, 3)
            .pause(2)
            .right(0.1, 3)
            .onCompleted(setSignal("concurrencyTest"))
        );
    }

    private void concurrencyTest() {
        begin(head()
            .waitOne(signal("concurrencyTest"))
            .concurrently(
                left(0.1, 3),
                right(0.1, 3)
            )
            .onCompleted(setSignal("asynchronyTest"))
        );
    }

    private void asynchronyTest() {
        begin(head()
            .waitOne(signal("asynchronyTest"))
            .asynchronously(
                left(0.1, 3),
                right(0.1, 3)
            )
            .print("Motors should still be spinning!")
            .onCompleted(setSignal("printTest"))
        );
    }

    private void printTest() {
        begin(head()
            .waitOne(signal("printTest"))
            .print("Completed all tests!")
        );
    }
    
}
