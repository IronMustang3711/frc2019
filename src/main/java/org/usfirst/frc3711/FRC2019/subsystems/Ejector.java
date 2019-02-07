/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc3711.FRC2019.TalonID;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Ejector extends TalonSubsystem {
 public Ejector(){
   super(Ejector.class.getSimpleName(),TalonID.EJECTOR.getId());
 }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public boolean isRunning(){
    return talon.getOutputCurrent() != 0;
}

public void run(){
    talon.set(ControlMode.PercentOutput, 1.0);
}

public void stop(){
    talon.neutralOutput();
}
}
