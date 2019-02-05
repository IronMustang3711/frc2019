/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.talon;

import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ResetTalonEncoder extends InstantCommand {

 final TalonSubsystem subsystem;

 public ResetTalonEncoder(TalonSubsystem subsystem){
   super("reset encoder("+subsystem.getName()+")");
   this.subsystem = subsystem;
   requires(subsystem);
 }


 @Override
 protected void execute() {
  subsystem.talon.setSelectedSensorPosition(0,0,50);
  subsystem.talon.getSensorCollection().setQuadraturePosition(0, 50);
 }


}

