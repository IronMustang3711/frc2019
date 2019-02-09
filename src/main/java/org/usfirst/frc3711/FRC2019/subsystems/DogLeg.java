/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import org.usfirst.frc3711.FRC2019.TalonID;

import java.util.Map;

/**
 * Add your docs here.
 */
public class DogLeg extends TalonSubsystem {
  private NetworkTableEntry motorOutput;

  public DogLeg(){
    super(DogLeg.class.getSimpleName(), TalonID.DOG_LEG.getId());

    motorOutput = tab.add("Output", 0.0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", -1.0, "max", 1.0))
            .getEntry();

    tab.add(new Command("Run Motor") {

      @Override
      protected void execute() {
        talon.set(ControlMode.PercentOutput,motorOutput.getDouble(0.0));
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




}