/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import org.usfirst.frc3711.deepspace.TalonID;
import org.usfirst.frc3711.deepspace.talon.TalonUtil;

import java.util.Map;

/**
 * Add your docs here.
 */
public class DogLeg extends TalonSubsystem {
  private final NetworkTableEntry motorOutput;

  private final Runnable talonTelemetry;
  private final NetworkTableEntry encoderA;
  private final NetworkTableEntry encoderB;

  public DogLeg() {
    super(DogLeg.class.getSimpleName(), TalonID.DOG_LEG.getId());

    talonTelemetry = TalonUtil.basicTelemetryWithEncoder(this);

    motorOutput = tab.add("Output", 0.0)
                      .withWidget(BuiltInWidgets.kNumberSlider)
                      .withProperties(Map.of("min", -1.0, "max", 1.0))
                      .getEntry();
    encoderA = tab.add("Encoder A", false).getEntry();
    encoderB = tab.add("Encoder B", false).getEntry();

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

    talon.configPeakCurrentLimit(80);
    talon.configPeakCurrentDuration(1000);
    talon.configContinuousCurrentLimit(0);
    talon.enableCurrentLimit(false);


    talon.setInverted(true);
    talon.setSensorPhase(false); //TODO: encoder not working correctly

    talon.config_kP(0,1.0);
//    talon.configForwardSoftLimitThreshold(0);
//    talon.configForwardSoftLimitEnable(true);
  }

  public void setMotorOutput(double output){
    talon.set(ControlMode.PercentOutput,output);
}

  @Override
  public void periodic() {
    super.periodic();
    talonTelemetry.run();
    encoderA.setBoolean(talon.getSensorCollection().getPinStateQuadA());
    encoderB.setBoolean(talon.getSensorCollection().getPinStateQuadB());
  }

}
