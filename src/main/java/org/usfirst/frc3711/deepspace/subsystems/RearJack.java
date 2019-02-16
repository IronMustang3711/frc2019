package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import org.usfirst.frc3711.deepspace.TalonID;
import org.usfirst.frc3711.deepspace.talon.TalonUtil;

import java.util.Map;

public class RearJack extends TalonSubsystem {

  private final NetworkTableEntry motorOutput;
  private final Runnable talonTelemetry;

  public RearJack() {
    super(RearJack.class.getSimpleName(), TalonID.REAR_JACK.getId());
    talonTelemetry = TalonUtil.closedLoopTelemetry(this);

    motorOutput = tab.add("Output", 0.0)
                      .withWidget(BuiltInWidgets.kNumberSlider)
                      .withProperties(Map.of("min", -1.0, "max", 1.0)).getEntry();

    tab.add(new Command("Run Motor") {

      @Override
      protected void execute() {
        talon.set(ControlMode.PercentOutput, motorOutput.getDouble(0.0));
      }

      @Override
      protected void end() {
        disable();
      }

      @Override
      protected boolean isFinished() {
        return false;
      }
    });
  }

  @Override
  void configureTalon() {
    super.configureTalon();
    talon.setNeutralMode(NeutralMode.Brake);
    talon.setSensorPhase(false);
  }

  @Override
  public void periodic() {
    super.periodic();
    talonTelemetry.run();
  }

  public void runDown(){
    talon.set(ControlMode.PercentOutput,1.0);
  }

  public void runUp(){
    talon.set(ControlMode.PercentOutput,-1.0);
  }


}
