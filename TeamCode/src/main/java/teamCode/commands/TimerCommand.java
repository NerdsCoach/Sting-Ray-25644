package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import java.util.function.DoubleSupplier;
import teamCode.subsystems.TimerSubsystem;


public class TimerCommand extends CommandBase
{
    private TimerSubsystem m_timerSubsystem;
    public DoubleSupplier m_timer;


    public TimerCommand(TimerSubsystem timerSubsystem, DoubleSupplier timer)
    {
        this.m_timerSubsystem = timerSubsystem;
        this.m_timer = timer;

        addRequirements(m_timerSubsystem);
            }
    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        this.m_timerSubsystem.inEndGame(m_timer.getAsDouble());
    }

}
