/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.subsystems;

import org.usfirst.frc3711.FRC2019.TalonID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Ejector extends TalonSubsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Ejector(){
    super(Ejector.class.getSimpleName(),TalonID.EJECTOR.getId());
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    super.periodic();
    SmartDashboard.putNumber("Ejector Position", talon.getSensorCollection().getQuadraturePosition());
  }
}
