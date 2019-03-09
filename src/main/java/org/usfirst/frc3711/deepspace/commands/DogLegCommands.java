package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.deepspace.Robot;

public class DogLegCommands {
  public static Command dogLegDown(){
    return new Command("Dogleg down"){
      {requires(Robot.dogLeg);}
      @Override
      protected boolean isFinished() {
        return false; //TODO: check encoder
      }

      @Override
      protected void execute() {
        super.execute();
        Robot.dogLeg.setMotorOutput(-1.0);
      }

      @Override
      protected void end() {
        super.end();
        holdPosition().start();
       // Robot.dogLeg.disable();
      }
    };
  }

  public static Command holdPosition(){
    return new Command("Hold Position") {

      private int position;

      {
        requires(Robot.dogLeg);
      }

      @Override
      protected void initialize() {
        super.initialize();
        position = Robot.dogLeg.getSelectedSensorPosition();
      }

      @Override
      protected boolean isFinished() {
        return false;
      }

      @Override
      protected void execute() {
        Robot.dogLeg.set(ControlMode.Position,position);
      }

      @Override
      protected void end() {
        Robot.dogLeg.disable();
      }
    };
  }

  public static Command runUp() {
    return new Command("Dogleg up"){

      {requires(Robot.dogLeg);}

      @Override
      protected boolean isFinished() {
        return false; //TODO: check encoder
      }

      @Override
      protected void execute() {
        super.execute();
        Robot.dogLeg.setMotorOutput(0.5);
      }

      @Override
      protected void end() {
        super.end();
        Robot.dogLeg.disable();
      }
    };
  }


}
