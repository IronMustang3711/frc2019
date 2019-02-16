/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
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

    tab.add(new Command("closed loop control(Position)") {

      {
        requires(FickleFinger.this);
      }

      @Override
      protected void execute() {
        // if (ntClosedLoopEnabled.getBoolean(false)) {
        talon.set(ControlMode.Position, ntSetpoint.getDouble(0.0));
        //}
      }

      @Override
      protected boolean isFinished() {
        return false;
      }
    });

    tab.add(new Command("Run Forward") {
      {
        requires(FickleFinger.this);
      }
      @Override
      protected boolean isFinished() {
        return false;
      }

      @Override
      protected void execute() {
        super.execute();
        setMotorOutput(0.5);
      }

      @Override
      protected void end() {
        super.end();
        disable();
      }
    });

    tab.add(new InstantCommand("Reset Encoder") {
      {
        requires(FickleFinger.this);
        setRunWhenDisabled(true);
      }

      @Override
      protected void execute() {
        talon.setSelectedSensorPosition(0);
        talon.getSensorCollection().setQuadraturePosition(0, 50);

      }

    });
  }

  @Override
  void configureTalon() {
    super.configureTalon();
    talon.setSensorPhase(true);
    talon.config_kP(0,1.5);

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
