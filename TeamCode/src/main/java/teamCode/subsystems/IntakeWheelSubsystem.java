package teamCode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

//import org.firstinspires.ftc.robotcontroller.external.samples.SensorDigitalTouch;

public class IntakeWheelSubsystem extends SubsystemBase
{
    private final CRServo m_intakeWheelServo;
    public TouchSensor m_touchSensor;
//    private SensorDigitalTouch m_digitalTouch;

    public IntakeWheelSubsystem(CRServo wheel)//, TouchSensor touch)
    {
        this.m_intakeWheelServo = wheel;
        this.m_intakeWheelServo.setRunMode(Motor.RunMode.VelocityControl);
//        this.m_intakeWheelServo.setRunMode(Motor.RunMode.VelocityControl);
//        this.m_touchSensor = touch;

        // set digital channel to input mode.
//        m_digitalTouch.setMode(DigitalChannel.Mode.INPUT);
    }

    // Spins the intake wheel forward, or in reverse.
    public void spinIntake(double speed)
    {
        this.m_intakeWheelServo.set(speed);
    }

    public void spinIntake(int spin)
    {
        this.m_intakeWheelServo.setTargetPosition(spin);
    }

//    public boolean isLoaded()
//    {
//       return this.m_touchSensor.isPressed();
//    }

    public void stop()
    {
        this.m_intakeWheelServo.stop();
    }
}
