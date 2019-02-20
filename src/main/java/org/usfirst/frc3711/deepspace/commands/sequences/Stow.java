package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.MyCommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class Stow extends MyCommandGroup {
  public Stow(){
    super(Stow.class.getSimpleName());

    addParallel(new MotionMagicSetpoint("Wrist Home", Robot.wrist,0));
    addParallel(new MotionMagicSetpoint("Arm Home",Robot.arm,0));
    addParallel(new MotionMagicSetpoint("Elevator Home",Robot.elevator,100));
  }

  @Override
  protected void end() {
    Robot.elevator.talon.neutralOutput();
    Robot.arm.talon.neutralOutput();
    Robot.wrist.talon.neutralOutput();
  }
}
