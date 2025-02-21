package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.Constants;
import teamCode.subsystems.LiftArmSubsystem;

public class ClimbArmCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;

    public ClimbArmCommand(LiftArmSubsystem liftArmSubsystem)
    {
        this.m_liftArmSubsystem = liftArmSubsystem;

        addRequirements(m_liftArmSubsystem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
            this.m_liftArmSubsystem.liftArm(Constants.LiftArmConstants.kLiftArmClimb);
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
