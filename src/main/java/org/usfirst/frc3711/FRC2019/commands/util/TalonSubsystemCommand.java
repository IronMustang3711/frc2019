package org.usfirst.frc3711.FRC2019.commands.util;

import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

public class TalonSubsystemCommand extends AbstractCommand {
  protected final TalonSubsystem subsystem;

  public TalonSubsystemCommand(String name, TalonSubsystem subsystem, double timeout) {
    super(subsystem.getName() + ":" + name, timeout, subsystem);
    this.subsystem = subsystem;
  }

  TalonSubsystemCommand(String name, TalonSubsystem subsystem) {
    super(subsystem.getName() + ":" + name, subsystem);
    this.subsystem = subsystem;
  }
}
