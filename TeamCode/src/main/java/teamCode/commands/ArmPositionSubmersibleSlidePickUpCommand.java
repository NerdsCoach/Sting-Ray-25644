package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import teamCode.Constants;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.LiftArmSubsystem;

public class ArmPositionSubmersibleSlidePickUpCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsytem;


    public ArmPositionSubmersibleSlidePickUpCommand(LiftArmSubsystem liftArmSubsystem,
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
        if (!m_slideArmSubsystem.atTarget(434));
        {
            this.m_liftArmSubsystem.fudgeFactor(12);
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
