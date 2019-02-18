package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.MyCommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class Level1ToHome extends MyCommandGroup {
  public Level1ToHome(){
    super(Level1ToHome.class.getSimpleName());

    var elevatorUp = new MotionMagicSetpoint("Elevator Up", Robot.elevator,5000);
    addParallel(elevatorUp);
    addSequential(Commands.delayUntil(() -> Math.abs(Robot.elevator.talon.getClosedLoopError()) < 500));

    var wristHome = new MotionMagicSetpoint("Wrist Home",Robot.wrist,0);
    addParallel(wristHome);
    addSequential(Commands.delayUntil(()-> wristHome.getMotionProgress() > 0.5));

    var armHome = new MotionMagicSetpoint.ArmSetpoint("Arm Home",0);
    addParallel(armHome);

    addSequential(Commands.delayUntil(()-> armHome.getMotionProgress() > 0.5 && wristHome.getMotionProgress() > 0.6));
    addSequential(new MotionMagicSetpoint("Elevator Home",Robot.elevator,10));
  }

}
