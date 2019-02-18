package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

//TODO: use or delete.
public class StagingPose extends CommandGroup {
  public StagingPose() {
    super(StagingPose.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);

    addParallel(new MotionMagicSetpoint("0", Robot.wrist, 0, 2.0));
    addParallel(new MotionMagicSetpoint("0", Robot.arm, 0, 2.0));
    addSequential(new MotionMagicSetpoint("Home", Robot.elevator, 2000, 2.0));


    addParallel(new MotionMagicSetpoint("Staging", Robot.wrist, 0));
    addParallel(new MotionMagicSetpoint("Home", Robot.elevator, 2000));
    addSequential(new MotionMagicSetpoint("Staging", Robot.arm, 800));

  }

  @Override
  protected void initialize() {
    super.initialize();
    Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

  }

  @Override
  protected void end() {
    Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);
    Robot.elevator.talon.neutralOutput();
    Robot.arm.talon.neutralOutput();
    Robot.wrist.talon.neutralOutput();
  }
}
