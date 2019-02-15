package org.usfirst.frc3711.FRC2019.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.commands.util.MotionMagicSetpoint;

public class Level1ToHome extends CommandGroup {
  public Level1ToHome(){
    super(Level1ToHome.class.getSimpleName());

    addSequential(new MotionMagicSetpoint("Elevator Up", Robot.elevator,4000,2.5));
    addParallel(new MotionMagicSetpoint("Wrist Home",Robot.wrist,10,2.5));
    addParallel(new MotionMagicSetpoint("Arm Home",Robot.arm,10,2.5));
    addSequential(new MotionMagicSetpoint("Elevator Home",Robot.arm,10,2.5));
  }

}
