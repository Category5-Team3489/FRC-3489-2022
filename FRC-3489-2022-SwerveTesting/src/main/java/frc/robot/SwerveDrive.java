package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;

public class SwerveDrive {
    
    private WPI_TalonFX[] motors = new WPI_TalonFX[8];

    private PIDController[] steeringControllers = new PIDController[4];

    public final static double ClicksPerRotation = 26214.4;// 32768 * (4 / 5) ////// 26204.07

    public SwerveDrive() {
        for (int i = 0; i < 8; i++)
        {
            motors[i] = new WPI_TalonFX(i + 1);
            motors[i].configFactoryDefault();
        }

        for (int i = 0; i < 4; i++)
        {
            steeringControllers[i] = new PIDController(0.0001, 0, 0);
        }
    }

    private WPI_TalonFX getDrivingMotor(int pairIndex) {
        return motors[pairIndex * 2];
    }

    private WPI_TalonFX getSteeringMotor(int pairIndex) {
        return motors[(pairIndex * 2) + 1];
    }

    public void teleopInit() {
        for (int i = 0 ; i < 8; i++)
        {
            motors[i].setSelectedSensorPosition(0);
        }
    }

    public void teleopPeriodic(double x, double y, double drive) {
        double speed = Math.sqrt(x * x + y * y);

        if (speed > 0.1) {
            double angle = Math.atan2(x, y) * 180 / Math.PI + 180;
            /*
            if (x >= 0 && y >= 0) { // QI
                rotationPercentage = lerp(0, 0.25, step(angle, 0, -90));
            }
            else if (x <= 0 && y >= 0) { // QII
                rotationPercentage = lerp(0.25, 0.5, step(angle, 90, 0));
            }
            else if (x <= 0 && y <= 0) { // QIII
                rotationPercentage = lerp(0.5, 0.75, step(angle, 0, -90));
            }
            else if (x >= 0 && y <= 0) { // QIV
                rotationPercentage = lerp(0.75, 1, step(angle, 90, 0));
            }
            */
            double targetClicks = lerp(0, ClicksPerRotation, step(angle, 0, 360));
            System.out.println(angle);

            for (int i = 0 ; i < 4; i++)
            {
                PIDController controller = steeringControllers[i];

                WPI_TalonFX drivingMotor = getDrivingMotor(i);
                //drivingMotor.set(speed);
                drivingMotor.set(drive);
                
                WPI_TalonFX steeringMotor = getSteeringMotor(i);
                double currentClicks = steeringMotor.getSelectedSensorPosition();
                double offset = currentClicks % ClicksPerRotation;
                double output = controller.calculate(currentClicks, (offset - targetClicks) + currentClicks);

                if (output > 1)
                {
                    output = 1;
                }
                else if (output < -1)
                {
                    output = -1;
                }

                steeringMotor.set(output);
            }
        }
        else {
            // joystick within deadzone
            for (int i = 0 ; i < 4; i++)
            {
                WPI_TalonFX drivingMotor = getDrivingMotor(i);
                drivingMotor.stopMotor();
                
                WPI_TalonFX steeringMotor = getSteeringMotor(i);
                steeringMotor.stopMotor();
            }
        }

        for (int i = 0 ; i < 4; i++)
        {
            WPI_TalonFX drivingMotor = getDrivingMotor(i);
            drivingMotor.set(drive);
        }
    }

    private double step(double x, double a, double b) {
        return (x - a) / (b - a);
    }

    private double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }
}
