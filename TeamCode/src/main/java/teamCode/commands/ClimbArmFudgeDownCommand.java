package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.Constants;
import teamCode.subsystems.ClimbArmSubsystem;

public class ClimbArmFudgeDownCommand extends CommandBase
{
    private ClimbArmSubsystem m_climbArmSubsystem;

    public ClimbArmFudgeDownCommand(ClimbArmSubsystem climbArmSubsystem)
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
            this.m_climbArmSubsystem.climbFudgeFactor(Constants.ClimbArmConstants.kClimberArmFudgeDown);
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
