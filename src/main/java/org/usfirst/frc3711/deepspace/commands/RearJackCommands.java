package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.deepspace.Robot;

public class RearJackCommands {
  public static Command runDown(){
    return new Command("Run read jack down"){
      @Override
      protected boolean isFinished() {
        return false; //TODO: check encoder
      }

      @Override
      protected void execute() {
        super.execute();
        Robot.rearJack.runDown();
      }

      @Override
      protected void end() {
        super.end();
        Robot.rearJack.disable();
      }
    };
  }
  public static Command runUp(){
    return new Command("Run rear jack up"){
      @Override
      protected boolean isFinished() {
        return false; //TODO: check encoder
      }

      @Override
      protected void execute() {
        super.execute();
        Robot.rearJack.runUp();
      }

      @Override
      protected void end() {
        super.end();
        Robot.rearJack.disable();
      }
    };
  }
}
