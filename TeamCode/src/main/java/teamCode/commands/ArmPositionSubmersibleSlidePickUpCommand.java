package teamCode.commands;

import static teamCode.Constants.SlideArmConstants.kSlideArmMaxHorizontalEx;
import static teamCode.Constants.SlideArmConstants.kSlideFudgeOutMax;

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
    private DcMotor m_slideArmMotor;
    private DcMotor m_liftArmMotor;


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
        if (!m_slideArmSubsystem.subAtTarget(kSlideArmMaxHorizontalEx))
        {
//            this.m_liftArmSubsystem.fudgeFactor(2);
//            this.m_slideArmSubsystem.slideFudgeFactor(4);
            this.m_liftArmSubsystem.fudgeFactor(16);//6
            this.m_slideArmSubsystem.slideFudgeFactor(30);//15
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
