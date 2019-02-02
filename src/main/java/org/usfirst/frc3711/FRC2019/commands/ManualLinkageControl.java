package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.subsystems.LinkageSubsystem;

public class ManualLinkageControl extends Command {

  private final LinkageSubsystem subsystem;

  public ManualLinkageControl(LinkageSubsystem subsystem) {
    this.subsystem = subsystem;
    requires(subsystem);
    requires(Robot.chassis);
  }

  @Override
  protected void initialize() {
    Shuffleboard.selectTab(subsystem.getName());
  }


  @Override
  protected void execute() {
    double output = Robot.oi.joystick1.getY();

    output *= -0.5;

    SmartDashboard.putNumber(subsystem.getName() + " output",output);

    subsystem.talon.set(ControlMode.PercentOutput,output);

    Robot.chassis.drive(0,0); // just in case
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
