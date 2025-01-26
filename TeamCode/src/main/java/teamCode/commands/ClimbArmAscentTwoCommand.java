package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.Constants;
import teamCode.subsystems.ClimbArmSubsystem;

public class ClimbArmAscentTwoCommand extends CommandBase
{
    private ClimbArmSubsystem m_climbArmSubsystem;

    public ClimbArmAscentTwoCommand(ClimbArmSubsystem climbArmSubsystem)
    {
        this.m_climbArmSubsystem = climbArmSubsystem;

        addRequirements(m_climbArmSubsystem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
//        if (!m_climbArmSubsystem.atTarget(0))
//        {
            this.m_climbArmSubsystem.climbing(Constants.ClimbArmConstants.kClimbArmAscentTwo);
//        }
    }

    @Override
    public void end(boolean interrupted)
    {
//        this.m_climbArmSubsystem.stop();
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
