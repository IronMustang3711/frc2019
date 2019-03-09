package org.usfirst.frc3711.deepspace.commands.util;

import edu.wpi.first.wpilibj.command.AbstractCommand;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

public abstract class TalonSubsystemCommand extends AbstractCommand {
  protected final TalonSubsystem subsystem;

  public TalonSubsystemCommand(String name, TalonSubsystem subsystem, double timeout) {
    super( name, timeout, subsystem);
    this.subsystem = subsystem;
  }

  public TalonSubsystemCommand(String name, TalonSubsystem subsystem) {
    super(name, subsystem);
    this.subsystem = subsystem;
  }
}
