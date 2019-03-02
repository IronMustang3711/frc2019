package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.MyCommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
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
    var elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 3000);
    addParallel(elevatorUp);

    addSequential(Commands.delayUntil("wait until elevator is 1/2 way up",
        ()-> elevatorUp.getMotionProgress() >= 0.5));

    // Arm out, Wrist down
    addParallel(new MotionMagicSetpoint("Arm Out", Robot.arm, 3200));
    var wristDown = new MotionMagicSetpoint("Wrist Down", Robot.wrist, -1800);
    addParallel(wristDown, 3.0);

    //elevator down
    addSequential(Commands.delayUntil(()->wristDown.getMotionProgress() >= 0.9));
    addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -7688));

  }

}
