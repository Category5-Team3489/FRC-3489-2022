package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LedHandler {
    
    AddressableLED led = new AddressableLED(0);
    AddressableLEDBuffer buffer = new AddressableLEDBuffer(162);

    double leftY, rightY = 0;

    public void init() {
        led.setLength(buffer.getLength());
        led.setData(buffer);
        led.start();
    }

    public void setInput(double leftY, double rightY) {
        this.leftY = leftY;
        this.rightY = rightY;
    }

    public void periodic() {
        int location = DriverStation.getLocation(); // 1, 2, 3
        boolean isBlueAlliance = DriverStation.getAlliance() == Alliance.Blue; // true, false
        
        switch (location) {
            case 1: 
                if(isBlueAlliance) mode4();
                else mode1();
                break;
            case 2:
                if (isBlueAlliance) mode5();
                else mode2();
                break;
            case 3:
                if(isBlueAlliance) mode6();
                else mode3();
                break;
        }
    
        led.setData(buffer);
    }

    void mode1() {
        double ledPercentBrightness = (Math.abs(leftY) + Math.abs(rightY)) / 2;

        for (var i = 0; i < buffer.getLength(); i++) {
          double percent = MathUtil.interpolate(0.1, 1, ledPercentBrightness);
          
          buffer.setHSV(i, 29 / 2, (int)(1.00 * 255), (int)(percent * 255));
        }
    }
    void mode2() {
        for (var i = 0; i < buffer.getLength(); i++) {
            if (Math.abs(leftY) > 0.5 && Math.abs(rightY) > 0.5) {
                buffer.setRGB(i, 232, 144, 21);
            }
            else {
              buffer.setRGB(i, 197, 21, 232);
              //buffer.setRGB(i, 255, 255, 255);
            }
        }
    }
    void mode3() {
        double time = Timer.getFPGATimestamp();
        // fix: save time as variable and change rate that it passes
        //double percent = (Math.abs(leftY) + Math.abs(rightY)) / 2;
        double percent = 0;
        double filteredPercent = Math.pow(percent, 4);
        double multiplier = MathUtil.interpolate(0.5, 2, filteredPercent);
        double lerp = (Math.sin(Math.PI * time * multiplier) + 1) / 2;

        // Orange: 212, 102, 0
        // Purple: 186, 82, 235

        int r = (int)MathUtil.interpolate(212, 186, lerp);
        int g = (int)MathUtil.interpolate(102, 82, lerp);
        int b = (int)MathUtil.interpolate(0, 235, lerp);

        for (var i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, r, g, b);
        }
    }
    void mode4() {
        // america mode
    }
    void mode5() {
        // 0..50% and 50..100% lerp 4 colors
    }
    void mode6() {
        
    }
}
