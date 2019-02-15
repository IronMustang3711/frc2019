package org.usfirst.frc3711.FRC2019.commands.util;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.BooleanSupplier;

public class DeferedCommandBuilder {
  private final Command command;

  public DeferedCommandBuilder(Command command) {
    this.command = command;
  }

  public Command delayed(long timeMillis){
    return new AbstractCommand("Delayed") {
      long initTime;
      @Override
      protected void initialize() {
        super.initialize();
        initTime = System.currentTimeMillis();
      }

      @Override
      protected void execute() {
        super.execute();
        long currentTime = System.currentTimeMillis();
        if((currentTime - initTime) >= timeMillis){
          cancel();
        }
      }

      @Override
      protected void end() {
        super.end();
        command.start();
      }
    };
  }
  public Command runWhen(BooleanSupplier guard){
    return new AbstractCommand("RunWhen") {
      @Override
      protected void execute() {
        super.execute();
        if(guard.getAsBoolean())
          cancel();
      }

      @Override
      protected void end() {
        super.end();
        command.start();
      }
    };
  }
}
