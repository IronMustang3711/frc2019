/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc3711.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoLift extends Command {
  static class Gearing {
    final double rearJack;
    final double dogLeg;
    Gearing(double rearJack,double dogLeg){
      this.rearJack = rearJack;
      this.dogLeg = dogLeg;
    }

  }
  static final Gearing[] GEARINGS = {
    new Gearing(0, 0),
    new Gearing(-10, -25)
  };

  public AutoLift() {
    requires(Robot.dogLeg);
    requires(Robot.rearJack);
    
  }


  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
  double rearJackPosition() {
    return Robot.rearJack.talon.getSelectedSensorPosition();
  }
  // double dogLegPosition() {
  //   return Robot.dogLeg.talon.getSelectedSensorPosition();
  // }

  static double getDoglegPosition(double rearJackPosition) {
    int gearingIndex = 0;
    // for (int i = 0; i < GEARINGS.length; i ++){
    //   if (rearJackPosition <= GEARINGS[i].rearJack) {
    //     gearingIndex = i;
    //     break;
    //   }

    //}
    for (int i = GEARINGS.length - 1;i > 0;i --){
      if (rearJackPosition <= GEARINGS[i].rearJack){
        gearingIndex = i;
        break;
      }
    }
    if (gearingIndex >= GEARINGS.length - 1){
      return GEARINGS[GEARINGS.length - 1].dogLeg;
    }
    else if(gearingIndex <= 0) {
      return GEARINGS[0].dogLeg;
    }
    else {
      return GEARINGS[gearingIndex].dogLeg
      + (rearJackPosition - GEARINGS[gearingIndex].rearJack)
      * (GEARINGS[gearingIndex + 1].dogLeg - GEARINGS[gearingIndex].dogLeg)
      / (GEARINGS[gearingIndex + 1].rearJack - GEARINGS[gearingIndex].rearJack);
    }

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double rearJack = rearJackPosition();
    double dogLeg = getDoglegPosition(rearJack);
    Gearing endingGear = GEARINGS[GEARINGS.length-1];
    if(rearJackPosition() <= endingGear.rearJack){
      Robot.rearJack.disable();

    }
    else {
      Robot.rearJack.runDown();
    }

    Robot.dogLeg.talon.set(ControlMode.Position, dogLeg);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    Gearing endingGear = GEARINGS[GEARINGS.length-1];
    return rearJackPosition() <= endingGear.rearJack;
    
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
