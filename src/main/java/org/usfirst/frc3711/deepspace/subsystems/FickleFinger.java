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


public class FickleFinger extends TalonSubsystem {
  private final Runnable talonTelemetry;

  public FickleFinger() {
    super(FickleFinger.class.getSimpleName(), TalonID.FICKLE_FINGER.getId());

    talonTelemetry = TalonUtil.closedLoopTelemetry(this);

    tab.add(this);

    tab.add(new Command("closed loop control(Position)") {

      {
        requires(FickleFinger.this);
      }

      @Override
      protected void execute() {
        talon.set(ControlMode.Position, ntSetpoint.getDouble(talon.getSelectedSensorPosition()));
      }

      @Override
      protected boolean isFinished() {
        return getError() < 30;
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
//    tab.add(new Command("Eject") {
//      {
//        requires(FickleFinger.this);
//      }
//
//      @Override
//      protected boolean isFinished() {
//        return isTimedOut() || isCanceled();
//      }
//
//      @Override
//      protected void initialize() {
//        talon.selectProfileSlot(0,0);
//      }
//
//      @Override
//      protected void execute() {
//        super.execute();
//        setMotorOutput(1.0);
//      }
//
//      @Override
//      protected void end() {
//        super.end();
//        disable();
//        int currentPosition = talon.getPosition();
//        int desiredPosition = ENCODER_TICKS_PER_REV *((currentPosition + ENCODER_TICKS_PER_REV/2) / ENCODER_TICKS_PER_REV);
//        talon.set(ControlMode.Position,desiredPosition);
//
//      }
//    });
//
//    tab.add(new Command("Hook" ){
//      {requires(FickleFinger.this);}
//      int baseRev;
//      @Override
//      protected boolean isFinished() {
//        return talon.getClosedLoopError() < 30;
//      }
//
//      @Override
//      protected void initialize() {
//        super.initialize();
//        baseRev = talon.getPosition() / ENCODER_TICKS_PER_REV;
//
//      }
//
//      @Override
//      protected void execute() {
//        super.execute();
//        talon.set(ControlMode.Position,252 + ENCODER_TICKS_PER_REV*baseRev);
//      }
//    });
//
//    tab.add(new Command("Hook Engage" ){
//      {requires(FickleFinger.this);}
//
//      int baseRev;
//
//      @Override
//      protected void initialize() {
//        super.initialize();
//        baseRev = talon.getPosition() / ENCODER_TICKS_PER_REV;
//
//      }
//
//      @Override
//      protected boolean isFinished() {
//        return talon.getClosedLoopError() < 30;
//      }
//
//      @Override
//      protected void execute() {
//        super.execute();
//        talon.set(ControlMode.Position,120 + ENCODER_TICKS_PER_REV*baseRev);
//      }
//    });
  }

  @Override
  void configureTalon() {
    super.configureTalon();
    talon.setInverted(false);
    talon.setSensorPhase(true);
    talon.config_kP(0,1.5);
    talon.configForwardSoftLimitThreshold(20);
    talon.configReverseSoftLimitThreshold(-2500);
    talon.configForwardSoftLimitEnable(false);
    talon.configReverseSoftLimitEnable(false);

  }

  public boolean isRunning() {
    return talon.getMotorOutputPercent() != 0;
  }


  public void setMotorOutput(double out){
    talon.set(ControlMode.PercentOutput,out);
  }


  public void stop() {
    talon.neutralOutput();
  }

  @Override
  public void periodic() {
    talonTelemetry.run();
  }

  public boolean isOut() {
    return talon.getSelectedSensorPosition() < -1000;
  }
}
