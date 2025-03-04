package teamCode.commands;

import static teamCode.Constants.SlideArmConstants.kSlideFudgeOutMax;
import com.arcrobotics.ftclib.command.CommandBase;
import teamCode.Constants;
import teamCode.subsystems.SlideArmSubsystem;

public class SlideFudgeOutCommand extends CommandBase
{
    private SlideArmSubsystem m_slideArmSubsystem;

    public SlideFudgeOutCommand(SlideArmSubsystem slideArmSubsystem)
    {
        this.m_slideArmSubsystem = slideArmSubsystem;

        addRequirements(m_slideArmSubsystem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        if (!m_slideArmSubsystem.atTarget(kSlideFudgeOutMax))
        {
            this.m_slideArmSubsystem.slideFudgeFactor(Constants.SlideArmConstants.kSlideFudgeOut);
        }

    }

    @Override
    public void end(boolean interrupted)
    {
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
