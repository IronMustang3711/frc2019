package org.usfirst.frc3711.deepspace.commands.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class DeferedCommandBuilder {
  private final Command command;

  public DeferedCommandBuilder(Command command) {
    this.command = command;
  }

  public Command delayed(double timeSeconds){
    return new CommandGroup("Delayed") {
      {
        addSequential(new WaitCommand(timeSeconds));
        addSequential(command);
      }
    };
  }
  static class WaitForCondition extends Command {
   final BooleanSupplier guard;

    WaitForCondition(BooleanSupplier guard) {
      super("WaitForCondition");
      this.guard = guard;
    }

    @Override
    protected boolean isFinished() {
      return guard.getAsBoolean() || isCanceled();
    }
  }

  public Command runWhen(BooleanSupplier guard){
    return new CommandGroup("RunWhen"){
      {
        addSequential(new WaitForCondition(guard));
        addSequential(command);
      }
    };

  }
  public <T> Command runWhen(T source, Function<T,Boolean> guard){
    return runWhen(()->guard.apply(source));
  }
}
