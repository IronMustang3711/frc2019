package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

public class ManualTalonControl extends Command {

  private final TalonSubsystem subsystem;

  public ManualTalonControl(TalonSubsystem subsystem) {
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
    subsystem.talon.set(ControlMode.PercentOutput, output);

    Robot.chassis.drive(0, 0); // just in case
  }

  @Override
  protected void end() {
    subsystem.talon.neutralOutput();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
