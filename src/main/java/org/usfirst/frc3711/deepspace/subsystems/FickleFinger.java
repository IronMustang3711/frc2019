/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc3711.deepspace.TalonID;
import org.usfirst.frc3711.deepspace.talon.TalonUtil;

/**
 * Add your docs here.
 */
public class FickleFinger extends TalonSubsystem {
  private final Runnable talonTelemetry;

  public FickleFinger() {
    super(FickleFinger.class.getSimpleName(), TalonID.FICKLE_FINGER.getId());

    talonTelemetry = TalonUtil.closedLoopTelemetry(this);
  }


  public boolean isRunning() {
    return talon.getMotorOutputPercent() != 0;
  }


  public void setMotorOutput(double out){
    talon.set(ControlMode.PercentOutput,out);
  }

  public void stow() {
//TODO:
  }

  public void hook() {
    //TODO:
  }

  public void eject() {
    //TODO;
  }


  public void stop() {
    talon.neutralOutput();
  }

  @Override
  public void periodic() {
    talonTelemetry.run();
  }
}
