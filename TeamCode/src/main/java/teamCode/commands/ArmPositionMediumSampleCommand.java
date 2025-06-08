package teamCode.commands;

import static teamCode.Constants.LiftArmConstants.kLiftArmFarSample;
import static teamCode.Constants.SlideArmConstants.kSlideArmFarSample;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;

public class ArmPositionMediumSampleCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsytem;

    public ArmPositionMediumSampleCommand(LiftArmSubsystem liftArmSubsystem,
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
        this.m_liftArmSubsystem.liftArm(kLiftArmFarSample);

        if (m_liftArmSubsystem.atTarget(kSlideArmFarSample))
        {
            this.m_slideArmSubsystem.slideArm(kSlideArmFarSample);
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
