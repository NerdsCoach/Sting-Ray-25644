package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.Constants;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;

public class ArmIntakeResetCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsytem;

    public ArmIntakeResetCommand(LiftArmSubsystem liftArmSubsystem,
                                 SlideArmSubsystem slideArmSubsystem, IntakePivotSubsystem intakePivotSubsystem)
    {
        this.m_liftArmSubsystem = liftArmSubsystem;
        this.m_slideArmSubsystem = slideArmSubsystem;
        this.m_intakePivotSubsytem = intakePivotSubsystem;

        addRequirements(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsytem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        this.m_liftArmSubsystem.liftArm(Constants.LiftArmConstants.kLiftArmIntakeReset);
        if (m_liftArmSubsystem.atTarget(Constants.LiftArmConstants.kLiftArmIntakeReset))
        {
            this.m_slideArmSubsystem.slideArm(Constants.SlideArmConstants.kSlideArmHome);
            this.m_intakePivotSubsytem.pivotIntake(Constants.PivotIntakeConstants.kIntakePivotPickUp);
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
