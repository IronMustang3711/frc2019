package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

public class ZeroEncoder extends TalonSubsystemCommand {
  public ZeroEncoder(TalonSubsystem subsystem){
    super("Zero Encoder",subsystem);
    setRunWhenDisabled(true);
  }

  @Override
  protected void initialize() {
    super.initialize();
    subsystem.talon.setSelectedSensorPosition(0);
    subsystem.talon.getSensorCollection().setQuadraturePosition(0, 50);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  public static Command resetAllEncoders(){
    return new Command("Reset All Encoders") {
      {
        setRunWhenDisabled(true);
      }
      @Override
      protected boolean isFinished() {
        return true;
      }

      @Override
      protected void initialize() {
        Robot.subsystems.stream()
            .filter(TalonSubsystem.class::isInstance)
            .map(TalonSubsystem.class::cast)
            .map(ZeroEncoder::new)
            .forEach(ZeroEncoder::start);
      }
    };
  }


  /**
   * This is a lazier version of the above method.
   * @return A Command that resets all encoders
   */
  public static Command resetAllEncoders2() {
    //TODO: test and use or remove
    return new InstantCommand("Reset All encoders(Wrapper)") {
      {
        setRunWhenDisabled(true);
      }

      @Override
      protected void initialize() {
        super.initialize();
        new CommandGroup("Reset All Encoders") {
          {
            setRunWhenDisabled(true);
            for (var subsystem : Robot.subsystems) {
              if (subsystem instanceof TalonSubsystem)
                addParallel(new ZeroEncoder((TalonSubsystem) subsystem));
            }
          }
        }.start();
      }
    };
  }
}
