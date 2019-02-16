package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.DeferedCommandBuilder;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class HatchPanel0_2 extends CommandGroup {
  private MotionMagicSetpoint elevatorUp;

  public HatchPanel0_2(){
    super(HatchPanel0_2.class.getSimpleName());
    elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 4000, 1.0);
    addParallel(new MotionMagicSetpoint("Bring arm out", Robot.arm, 600));

   // addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 10,0.1));
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 10,1.0));
   // addParallel(new MotionMagicSetpoint("Bring arm out", Robot.arm, 600));
//    addParallel(new DeferedCommandBuilder(
//        new MotionMagicSetpoint("Bring arm out", Robot.arm, 600){
//          @Override
//          protected void initialize() {
//            super.initialize();
//            System.out.println("HatchPanel0_2.initialize");
//          }
//        })
//                    .runWhen(()->elevatorUp.isRunning() && elevatorUp.getMotionProgress() > 0.10));
    addParallel(elevatorUp);
  }
}
