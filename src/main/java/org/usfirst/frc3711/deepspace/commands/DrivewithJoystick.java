// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.deepspace.Robot;

/**
 *
 */
public class DrivewithJoystick extends Command {

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
  public DrivewithJoystick() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    requires(Robot.chassis);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Shuffleboard.selectTab(Robot.chassis.tab.getTitle());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Joystick joy = Robot.oi.getJoystick1();
    double forward = joy.getY();

    forward = Math.copySign(forward*forward,forward);

    forward *= -0.8;

    double turn = joy.getTwist(); // If it doesn't work, try getZ

    turn = Math.copySign(turn*turn,turn);
    //turn = turn * turn * turn;
    turn *= 0.70;
    Robot.chassis.drive(forward, turn);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.chassis.drive(0.0, 0.0);
  }


}
