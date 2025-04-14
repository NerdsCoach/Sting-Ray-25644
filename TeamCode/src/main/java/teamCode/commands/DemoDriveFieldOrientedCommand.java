package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import java.util.function.DoubleSupplier;

import teamCode.subsystems.DemoDriveSubsystem;
import teamCode.subsystems.DriveSubsystem;

public class DemoDriveFieldOrientedCommand extends CommandBase
{
    public DemoDriveSubsystem m_demoDriveSubsystem;
    public DoubleSupplier m_leftX;
    public DoubleSupplier m_leftY;
    public DoubleSupplier m_rightX;
    public DoubleSupplier m_rightY;

    public DemoDriveFieldOrientedCommand(DemoDriveSubsystem demoDriveSubsystem, DoubleSupplier leftX, DoubleSupplier leftY, DoubleSupplier rightX, DoubleSupplier rightY)
    {
       this.m_demoDriveSubsystem = demoDriveSubsystem;
       addRequirements(m_demoDriveSubsystem);

       this.m_leftX = leftX;
       this.m_leftY = leftY;
       this.m_rightX = rightX;
       this.m_rightY = rightY;
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
       this.m_demoDriveSubsystem.headingDrive
               (
                       m_leftX.getAsDouble(),
                       m_leftY.getAsDouble(),
                       m_rightX.getAsDouble(),
                       m_rightY.getAsDouble()
               );
    }
}
