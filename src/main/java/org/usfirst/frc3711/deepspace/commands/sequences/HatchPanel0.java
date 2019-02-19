package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class HatchPanel0 extends CommandGroup {

  public HatchPanel0(){
    super(HatchPanel0.class.getSimpleName());
    MotionMagicSetpoint elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 4000, 1.0);
    addParallel(new MotionMagicSetpoint("Bring arm out", Robot.arm, 600));

   // addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 10,0.1));
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 30,1.0));
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
