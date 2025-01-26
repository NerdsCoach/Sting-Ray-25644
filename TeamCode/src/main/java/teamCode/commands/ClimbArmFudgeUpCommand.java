package teamCode.commands;

import static teamCode.Constants.ClimbArmConstants.kClimberArmUp;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.Constants;
import teamCode.subsystems.ClimbArmSubsystem;

public class ClimbArmFudgeUpCommand extends CommandBase
{
    private ClimbArmSubsystem m_climbArmSubsystem;

    public ClimbArmFudgeUpCommand(ClimbArmSubsystem climbArmSubsystem)
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
        if (!m_climbArmSubsystem.atTarget(kClimberArmUp))
        {
            this.m_climbArmSubsystem.climbFudgeFactor(Constants.ClimbArmConstants.kClimberArmFudgeUp);
        }
//        System.out.println(this.m_climbArmSubsystem.climbEncoderReading());


//
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
