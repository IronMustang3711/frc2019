package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class LoadingStationFuel extends MyCommandGroup {

  public LoadingStationFuel() {
    super(LoadingStationFuel.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);


    // elevator up:
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90));
    addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 0));

    var elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 3000);
    addParallel(elevatorUp);

    // Arm out, Wrist down
    addSequential(Commands.delayUntil("Wait for elevator to get 20% way up",
        ()->elevatorUp.isRunning() && elevatorUp.getMotionProgress() >= 0.2));

    var armOut = new MotionMagicSetpoint.ArmSetpoint("Arm Out", 3100);
    addParallel(armOut);

    addSequential(Commands.delayUntil("Wait for arm to be horizontal-ish",() ->
                                         armOut.isRunning() && armOut.getMotionProgress() >= .1));

    addParallel(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -2200));

    //elevator back down
    addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -4300));

  }

}
