package org.usfirst.frc3711.deepspace.commands.util;

import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

public class TalonSubsystemCommand extends AbstractCommand {
  protected final TalonSubsystem subsystem;

  public TalonSubsystemCommand(String name, TalonSubsystem subsystem, double timeout) {
    super( name, timeout, subsystem);
    this.subsystem = subsystem;
  }

  protected TalonSubsystemCommand(String name, TalonSubsystem subsystem) {
    super(name, subsystem);
    this.subsystem = subsystem;
  }
}
