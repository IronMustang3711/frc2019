package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class HatchPanel1 extends MyCommandGroup {

  public HatchPanel1() {
    super(HatchPanel1.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);

    double elevatorUpTimeout = 1.7;

    // elevator up:
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
    addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 0), elevatorUpTimeout);
    addSequential(new MotionMagicSetpoint("bring elevator up", Robot.elevator, 3000) );

    double armOutTimeout = 3.0;
    // Arm out, Wrist down

    //	addSequential(new MotionMagicSetpoint("Wrist Down",Robot.wrist,-2949),armOutTimeout);
    addParallel(new MotionMagicSetpoint("Arm Out", Robot.arm, 3200));
    addParallel(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -1800), armOutTimeout);
    addSequential(new WaitForChildren());
    addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -7688));

  }

}
