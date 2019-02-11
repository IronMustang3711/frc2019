/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.commands;

import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Add your docs here.
 */
public class DisableTalon extends InstantCommand {
final TalonSubsystem subsystem;
  
public DisableTalon(TalonSubsystem subsystem) {
    super(subsystem.getName()+"Disable");
    this.subsystem = subsystem;
    requires(subsystem);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    subsystem.disable();
  }

}
