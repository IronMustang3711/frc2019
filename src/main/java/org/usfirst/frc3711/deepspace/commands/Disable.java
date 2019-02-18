package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

public class Disable extends TalonSubsystemCommand {
  public Disable(TalonSubsystem subsystem) {
    super("Disable", subsystem);
    setRunWhenDisabled(true);
  }

  @Override
  protected void initialize() {
    super.initialize();
    subsystem.disable();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  public static Command all() {
    return new InstantCommand("Disable All", () ->
                                                 Robot.subsystems.stream()
                                                     .filter(TalonSubsystem.class::isInstance)
                                                     .map(TalonSubsystem.class::cast)
                                                     .map(Disable::new)
                                                     .forEach(Disable::start));
  }
}
