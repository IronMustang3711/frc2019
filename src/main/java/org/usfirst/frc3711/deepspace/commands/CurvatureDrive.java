/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.deepspace.commands;

import org.usfirst.frc3711.deepspace.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class CurvatureDrive extends Command {
  public CurvatureDrive() {
    super("CurvatureDrive");
    // Use requires() here to declare subsystem dependencies
     requires(Robot.chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Joystick joy = Robot.oi.getJoystick1();
    double forward = -1*joy.getY();
    double turn = joy.getTwist(); 
    boolean quickTurn = joy.getRawButton(5);

    Robot.chassis.curvatureDrive(forward, turn,quickTurn);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.arcadeDrive(0, 0);
  }


}
