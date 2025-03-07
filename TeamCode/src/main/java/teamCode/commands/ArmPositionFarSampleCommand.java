package teamCode.commands;

import static teamCode.Constants.LiftArmConstants.kLiftArmFarSample;
import static teamCode.Constants.SlideArmConstants.kSlideArmFarSample;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.Constants;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;

public class ArmPositionFarSampleCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsystem;

    public ArmPositionFarSampleCommand(LiftArmSubsystem liftArmSubsystem,
                                       SlideArmSubsystem slideArmSubsystem, IntakePivotSubsystem intakePivotSubsystem)
    {
        this.m_liftArmSubsystem = liftArmSubsystem;
        this.m_slideArmSubsystem = slideArmSubsystem;
        this.m_intakePivotSubsystem = intakePivotSubsystem;

        addRequirements(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        this.m_liftArmSubsystem.liftArm(kLiftArmFarSample);

        if (m_liftArmSubsystem.atTarget(kLiftArmFarSample))
        {
            this.m_slideArmSubsystem.slideArm(kSlideArmFarSample);
            this.m_intakePivotSubsystem.pivotIntake(Constants.PivotIntakeConstants.kIntakePivotFarSample);
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
