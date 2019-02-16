package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.deepspace.Robot;

public class DogLegCommands {
  public static Command dogLegDown(){
    return new Command("Dogleg down"){
      @Override
      protected boolean isFinished() {
        return false; //TODO: check encoder
      }

      @Override
      protected void execute() {
        super.execute();
        Robot.dogLeg.runDown();
      }

      @Override
      protected void end() {
        super.end();
        Robot.dogLeg.disable();
      }
    };
  }

}