package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class Resting2 extends CommandGroup {
  public Resting2() {
    super(Resting2.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);

    addSequential(new MotionMagicSetpoint("Elevator->Rest", Robot.elevator, 100,1.5));
    addSequential(new MotionMagicSetpoint("Wrist->Rest", Robot.wrist, 0), 2.0);
    addSequential(new MotionMagicSetpoint("Arm->Rest", Robot.arm, 0), 2.0);

  }

}
