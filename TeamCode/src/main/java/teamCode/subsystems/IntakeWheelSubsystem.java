package teamCode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class IntakeWheelSubsystem extends SubsystemBase
{
    private final CRServo m_intakeWheelServo;

    private com.qualcomm.robotcore.hardware.TouchSensor m_touchSensor;
    private boolean touchSensorIsPressed = false;

    public IntakeWheelSubsystem(CRServo wheel, TouchSensor touch)
    {
        this.m_intakeWheelServo = wheel;
        this.m_intakeWheelServo.setRunMode(Motor.RunMode.VelocityControl);
        this.m_touchSensor = touch;
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

    public boolean isLoaded()
    {
       return this.m_touchSensor.isPressed();
    }

    public void stop()
    {
        this.m_intakeWheelServo.stop();
    }
}
