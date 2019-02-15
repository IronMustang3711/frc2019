/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc3711.FRC2019.TalonID;
import org.usfirst.frc3711.FRC2019.talon.TalonUtil;

/**
 * Add your docs here.
 */
public class Ejector extends TalonSubsystem {
  private final Runnable talonTelemetry;

  public Ejector() {
    super(Ejector.class.getSimpleName(), TalonID.EJECTOR.getId());

    talonTelemetry = TalonUtil.closedLoopTelemetry(this);
  }


  public boolean isRunning() {
    return talon.getMotorOutputPercent() != 0;
  }

  public void run() {
    talon.set(ControlMode.PercentOutput, 1.0);
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
}
