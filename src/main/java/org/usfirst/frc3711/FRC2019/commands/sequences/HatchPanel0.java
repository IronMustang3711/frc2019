package org.usfirst.frc3711.FRC2019.commands.sequences;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.commands.util.Commands;
import org.usfirst.frc3711.FRC2019.commands.util.MotionMagicSetpoint;

public class HatchPanel0 extends Command {
  private final Command wristVertical = new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 10,1.0);
  private final Command armVertical = new MotionMagicSetpoint("Arm Vertical", Robot.arm, 10,1.0);
  private final MotionMagicSetpoint elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 2000, 1.0){

    @Override
    protected void execute() {
      super.execute();
//        double motionProgress = getMotionProgress();
//        System.out.println("Motion progress: "+motionProgress);
//        DriverStation.reportWarning("Motion progress: "+motionProgress,false);
//        SmartDashboard.putNumber("motion progress",motionProgress);
    }

    @Override
    protected boolean isFinished() {
    return isMotionFinished() || super.isFinished();
    }
  };

  private final Command armOut = Commands.runWhenTrue(new MotionMagicSetpoint("Bring arm out", Robot.arm, 600),
      () -> elevatorUp.getMotionProgress() >= 0.3);

  //TODO may be too low
  private final Command elevatorDown = Commands.runWhenTrue(new MotionMagicSetpoint("bring elevator up", Robot.elevator, -800, 1.0),
      armOut::isCompleted);

//    Command elevatorHold = Commands.runWhenTrue(Commands.constantOutput(Robot.elevator,0.2),
//        ()-> elevatorUp.isCompleted() && elevatorDown.isCompleted());





  public HatchPanel0() {
    super(HatchPanel0.class.getSimpleName());

  }

  @Override
  protected void initialize() {
    super.initialize();
    Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

    wristVertical.start();
    armVertical.start();
    elevatorUp.start();
    elevatorDown.start();
  //  elevatorHold.start();
    armOut.start();

  }


  @Override
  protected boolean isFinished() {
    return armOut.isRunning();
  }

  @Override
  protected void end() {
    super.end();
    Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);
   // RestingPose.run();
  }

}
