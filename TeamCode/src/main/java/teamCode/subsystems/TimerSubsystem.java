package teamCode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class TimerSubsystem extends SubsystemBase
{
    private GamepadEx m_gamepad1;
    private GamepadEx m_gamepad2;
    public double timer;


    public TimerSubsystem(GamepadEx gamepad1, GamepadEx gamepad2)
    {
        this.m_gamepad1 = gamepad1;
        this.m_gamepad2 = gamepad2;
    }


    public void inEndGame(double timer)
    {
        if(timer > 85.0 && timer < 86.5 )
        {
            this.m_gamepad1.gamepad.rumble(1000);
            this.m_gamepad2.gamepad.rumble(1000);
        }
    }
}
