package teamCode.commands;

import static teamCode.Constants.PivotIntakeConstants.kIntakePivotSpecimen;
import com.arcrobotics.ftclib.command.CommandBase;
import teamCode.Constants;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.IntakeWheelSubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.LiftArmSubsystem;

public class ArmPositionScoreHighChamberCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsystem;
    private IntakeWheelSubsystem m_intakeWheelSubsystem;

    public ArmPositionScoreHighChamberCommand(LiftArmSubsystem liftArmSubsystem,
                                              SlideArmSubsystem slideArmSubsystem,
                                              IntakePivotSubsystem intakePivotSubsystem,
                                              IntakeWheelSubsystem intakeWheelSubsystem)
    {
        this.m_liftArmSubsystem = liftArmSubsystem;
        this.m_slideArmSubsystem = slideArmSubsystem;
        this.m_intakePivotSubsystem = intakePivotSubsystem;
        this.m_intakeWheelSubsystem = intakeWheelSubsystem;


        addRequirements(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem, m_intakeWheelSubsystem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotSpecimen);
            this.m_slideArmSubsystem.slideArm(Constants.SlideArmConstants.kSlideSpecimenScore);
            this.m_intakeWheelSubsystem.spinIntake(-0.2);
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
