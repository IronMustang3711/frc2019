package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class RestingPose extends CommandGroup {

  private static final RestingPose INSTANCE = new RestingPose();

  static Command run() {
    INSTANCE.cancel();
    INSTANCE.start();
    return INSTANCE;
  }

  public RestingPose() {
    super(RestingPose.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);

    addParallel(new MotionMagicSetpoint("Rest", Robot.wrist, 0, 2.0));
    addSequential(new MotionMagicSetpoint("Home", Robot.elevator, 2000, 2.0));

    addSequential(new MotionMagicSetpoint("Rest", Robot.arm, 0));
    addSequential(new MotionMagicSetpoint("Rest", Robot.elevator, 0));


  }

  @Override
  protected void initialize() {
    super.initialize();
    Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);
  }

  @Override
  protected void end() {
    super.end();
    Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

  }

}
