// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3711.deepspace.subsystems;


import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.usfirst.frc3711.deepspace.TalonID;
import org.usfirst.frc3711.deepspace.talon.SlotConfigBuilder;
import org.usfirst.frc3711.deepspace.talon.TalonUtil;

import java.util.Map;


/**
 *
 */
public class Arm extends TalonSubsystem {

  @SuppressWarnings("WeakerAccess")
  static class TalonSettings {
    static class PIDSlots {
      public static final SlotConfiguration POSITION_SLOT =
          SlotConfigBuilder.newBuilder()
              .withKP(2.0)
              .build();

      public static final SlotConfiguration MM_SLOT =
          SlotConfigBuilder.builderWithBaseConfiguration(POSITION_SLOT)
              .withKP(2.5)
              // .withKI(1e-9)
              .withKF(10.0)
              .withMaxIntegralAccumulator(10000)
              // .withClosedLoopPeakOutput(0.9)
              .build();

      public static SlotConfiguration configurationForSlot(int slot) {
        switch (slot) {
          case 0:
            return MM_SLOT;
          case 1:
            return POSITION_SLOT;
          default:
            throw new IllegalArgumentException("invalid slot: " + slot);
        }
      }

      public static SlotConfiguration configurationForMode(ControlMode mode) {
        switch (mode) {
          case Position:
          case Disabled:
            return POSITION_SLOT;
          case MotionMagic:
            return MM_SLOT;
          default:
            throw new IllegalArgumentException("Control Mode:" + mode);
        }
      }

      public static int slotForMode(ControlMode mode) {
        if (mode == ControlMode.MotionMagic) {
          return 0;
        }
        return 1;
      }
    }


    public static final TalonSRXConfiguration CONFIGURATION = new TalonSRXConfiguration();

    @SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
    static TalonSRXConfiguration applyConfig(TalonSRXConfiguration config) {
      config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;

      config.remoteFilter0.remoteSensorSource = RemoteSensorSource.Off;
      config.remoteFilter1.remoteSensorSource = RemoteSensorSource.Off;

      config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
      config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

      config.forwardLimitSwitchNormal = LimitSwitchNormal.Disabled;
      config.reverseLimitSwitchNormal = LimitSwitchNormal.Disabled;

      config.forwardSoftLimitThreshold = 3500; //this is probably a little too far back
      config.reverseSoftLimitThreshold = -100;
      config.forwardSoftLimitEnable = true;
      config.reverseSoftLimitEnable = true;


      config.openloopRamp = 1.0;
      // config.closedloopRamp = 1.705000;

      config.motionCruiseVelocity = 80;
      config.motionAcceleration = 90;

      config.peakOutputForward = 1.0;
      config.peakOutputReverse = -0.5;



        /*
        https://phoenix-documentation.readthedocs.io/en/latest/ch13_MC.html#ramping
       The nominal outputs can be selected to ensure that any non-zero requested motor output gets promoted
       to a minimum output. For example, if the nominal forward is set to +0.10 (+10%), then any motor request
       within (0%, +10%) will be promoted to +10% assuming request is beyond the neutral dead band.
       This is useful for mechanisms that require a minimum output for movement,
       and can be used as a simpler alternative to the kI (integral) component of closed-looping in some circumstances.
         */
      config.nominalOutputForward = 0;
      config.nominalOutputReverse = 0;


      // config.neutralDeadband = 0.199413; //TODO: configure



      /*
Talon SRX and Victor SPX can be configured to adjust their outputs in response to the battery
voltage measurement (in all control modes). Use the voltage compensation saturation config to determine
what voltage represents 100% output.
 */
      config.voltageCompSaturation = 10.0;


              /*
        After setting the three configurations, current limiting must be enabled via enableCurrentLimit() or LabVIEW VI.
         */
      config.peakCurrentLimit = 25;
      config.peakCurrentDuration = 2000;
      config.continuousCurrentLimit = 1;

      config.slot0 = PIDSlots.configurationForSlot(0);
      config.slot1 = PIDSlots.configurationForSlot(1);

      return config;
    }


    /*
things not handled with config:
[x]Current Limit Enable (though the thresholds are configs)
[x]Voltage Compensation Enable (though the nominal voltage is a config)
Control Mode and Target/Output demand (percent, position, velocity, etc.)
[x]Invert direction and sensor phase
[x]Closed-loop slot selection [0,3] for primary and aux PID loops.
Neutral mode override (convenient to temporarily override configs)
Limit switch override (convenient to temporarily override configs)
Soft Limit override (convenient to temporarily override configs)
Status Frame Periods
*/
    public static void configure(TalonSRX talon) {
      talon.configFactoryDefault();

      talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
      talon.selectProfileSlot(0, 0); //motion magic slot & primary pid


      talon.setInverted(true);
      talon.setSensorPhase(false);


      talon.getAllConfigs(CONFIGURATION);
      applyConfig(CONFIGURATION);
      talon.configAllSettings(CONFIGURATION);

      talon.enableVoltageCompensation(true);
      talon.enableCurrentLimit(true);

      talon.configMotionSCurveStrength(8);
    }
  }


  private final Runnable talonTelemetry;

  private final SendableChooser<ControlMode> modeChooser;
  private final NetworkTableEntry percentOutput;
  private final NetworkTableEntry lowPowerMode;

  private long lowPowerStartTime;

  public Arm() {
    super(Arm.class.getSimpleName(), TalonID.ARM.getId());
    talonTelemetry = TalonUtil.motionMagicTelemetry(this);
    modeChooser = new SendableChooser<>();
    addChild("mode chooser", modeChooser);
    modeChooser.setDefaultOption("MotionMagic", ControlMode.MotionMagic);
    modeChooser.addOption("Position", ControlMode.Position);

    tab.add(modeChooser);

    tab.add(this);

    percentOutput = tab.add("output%", 0.0)
                        .withWidget(BuiltInWidgets.kNumberSlider)
                        .withProperties(Map.of("min", -4, "max", 0.4))
                        .getEntry();

    lowPowerMode = tab.add("low power mode", false)
                       .withWidget(BuiltInWidgets.kBooleanBox)
                       .getEntry();

    tab.add(new Command("closed loop control") {

      {
        requires(Arm.this);
      }

      boolean lowPower;


      @SuppressWarnings("deprecation") //for talon.configureSlot
      @Override
      protected void initialize() {
        ControlMode mode = modeChooser.getSelected();
        SlotConfiguration configuration = TalonSettings.PIDSlots.configurationForMode(mode);
        int slot = TalonSettings.PIDSlots.slotForMode(mode);
        talon.configureSlot(configuration, slot, 50);

        lowPowerMode.setBoolean(lowPower = false);
        lowPowerStartTime = System.currentTimeMillis();

      }

      @Override
      protected void execute() {

        double sp = ntSetpoint.getDouble(talon.getSelectedSensorPosition());

//        long elapsed = System.currentTimeMillis() - lowPowerStartTime;
//
//        if (Math.abs(talon.getErrorDerivative()) < 4.0
//                &&  elapsed > 500) {
//          if (!lowPower) {
//            System.out.println("low power @ " + elapsed
//                                   + " E=" + talon.getClosedLoopError()
//                                   + " dE=" + talon.getErrorDerivative());
//            lowPowerMode.setBoolean(lowPower = true);
//            talon.configVoltageCompSaturation(5.0);
//           // enableCurrentLimiting();
//          }
//        } else {
//          if (lowPower) {
//            System.out.println("full power @ " + elapsed
//                                   + " E=" + talon.getClosedLoopError()
//                                   + " dE=" + talon.getErrorDerivative());
//            lowPowerMode.setBoolean(lowPower = false);
//            talon.configVoltageCompSaturation(9.0);
//            // disableCurrentLimiting();
//          }
//        }
        talon.set(modeChooser.getSelected(), sp);
      }

      @Override
      protected void end() {
        disable();
        talon.configVoltageCompSaturation(9.0);
        enableCurrentLimiting();
      }

      @Override
      protected boolean isFinished() {
        return false;
      }
    });

    tab.add(new InstantCommand("Reset Encoder") {
      {
        requires(Arm.this);
      }

      @Override
      protected void execute() {
        talon.setSelectedSensorPosition(0);
        talon.getSensorCollection().setQuadraturePosition(0, 50);

      }

    });

    tab.add(new Command("Constant Output") {

      {
        requires(Arm.this);
      }

      @Override
      protected void execute() {
        talon.set(ControlMode.PercentOutput, percentOutput.getDouble(0.0));
      }

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
  protected void onSetpointChange(double newSetpoint) {
    super.onSetpointChange(newSetpoint);
    talon.setIntegralAccumulator(0);
    disableCurrentLimiting();
    enableCurrentLimiting();
    lowPowerStartTime = System.currentTimeMillis();
  }


//      }

  @Override
  void configureTalon() {
    super.configureTalon();
    TalonSettings.configure(talon);

    /* Set relevant frame periods to be at least as fast as periodic rate */
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
    talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

  }

  @Override
  public void periodic() {
    super.periodic();
    talonTelemetry.run();
  }

}

