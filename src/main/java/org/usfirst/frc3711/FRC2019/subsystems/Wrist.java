// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import org.usfirst.frc3711.FRC2019.TalonID;
import org.usfirst.frc3711.FRC2019.talon.SlotConfigBuilder;
import org.usfirst.frc3711.FRC2019.talon.TalonUtil;

public class Wrist extends TalonSubsystem {
  @SuppressWarnings("WeakerAccess")
  static class TalonSettings {
    static class PIDSlots {
      public static final SlotConfiguration POSITION_SLOT = SlotConfigBuilder.newBuilder().withKP(4.0).build();

      //    public void configMotionMagicClosedLoop(){
//         talon.config_kP(0,4.0,50);
//         talon.config_kI(0,0.01,50);
//         talon.config_IntegralZone(0,100,50);
//         talon.config_kD(0,0,50);
//         talon.config_kF(0,1.0,50);
//
//
//         talon.configMotionCruiseVelocity(800);
//         talon.configMotionAcceleration(700);
//    }

      public static final SlotConfiguration MM_SLOT =
          SlotConfigBuilder.builderWithBaseConfiguration(POSITION_SLOT)
              .withKP(4.5)
//                            .withKI(0.01)
//                            .withIntegralZone(100)
              .withKF(2.0)
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

      config.forwardSoftLimitThreshold = 100;
      config.reverseSoftLimitThreshold = -3100; //TODO: verify correctness
      config.forwardSoftLimitEnable = true;
      config.reverseSoftLimitEnable = true;


      config.openloopRamp = 0.5;
      // config.closedloopRamp = 1.705000;

      config.motionCruiseVelocity = 100;
      config.motionAcceleration = 100;

      config.peakOutputForward = 1.0;
      config.peakOutputReverse = -0.8;



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


      // config.neutralDeadband = 0.199413;



      /*
Talon SRX and Victor SPX can be configured to adjust their outputs in response to the battery
voltage measurement (in all control modes). Use the voltage compensation saturation config to determine
what voltage represents 100% output.
 */
      config.voltageCompSaturation = 9.0;


              /*
        After setting the three configurations, current limiting must be enabled via enableCurrentLimit() or LabVIEW VI.
         */
      config.peakCurrentLimit = 20;
      config.peakCurrentDuration = 1000;
      config.continuousCurrentLimit = 1;

      config.slot0 = PIDSlots.configurationForSlot(0);
      config.slot1 = PIDSlots.configurationForSlot(1);

      return config;
    }


    public static void configure(TalonSRX talon) {
      talon.configFactoryDefault();

      talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
      talon.selectProfileSlot(0, 0); //motion magic slot & primary pid


      talon.setInverted(false);
      talon.setSensorPhase(true);

      talon.enableVoltageCompensation(true);
      talon.enableCurrentLimit(true);

      talon.getAllConfigs(CONFIGURATION);
      applyConfig(CONFIGURATION);
      talon.configAllSettings(CONFIGURATION);

    }
  }


  private final NetworkTableEntry lowPowerMode;
  private final Runnable talonTelemetry;


  public Wrist() {
    super(Wrist.class.getSimpleName(), TalonID.WRIST.getId());


    lowPowerMode = tab.add("low power mode", false)
                       .withWidget(BuiltInWidgets.kBooleanBox)
                       .getEntry();

    talonTelemetry = TalonUtil.motionMagicTelemetry(this);


    tab.add(this);

    tab.add(new Command("closed loop control") {

      {
        requires(Wrist.this);
      }

      boolean lowPower;


      @Override
      protected void initialize() {
        lowPowerMode.setBoolean(lowPower = true);
        // talon.selectProfileSlot(1, 0);
      }


      @Override
      protected void execute() {
        //relatively low error & not much motion:
        if (Math.abs(talon.getErrorDerivative()) < 1.0 && Math.abs(talon.getClosedLoopError()) < 200) {
          //enable 'low-power' mode
          if (!lowPower) {
            talon.configVoltageCompSaturation(5.0);
            lowPower = true;
          }
        }
        //high error and/or movement
        else {
          if (lowPower) {
            lowPowerMode.setBoolean(lowPower = false);
            talon.configVoltageCompSaturation(9.0);
          }
        }
        talon.set(ControlMode.MotionMagic, ntSetpoint.getDouble(talon.getSelectedSensorPosition()));
      }

      @Override
      protected void end() {
        talon.configVoltageCompSaturation(9.0);
        talon.selectProfileSlot(0, 0);
        disable();
      }

      @Override
      protected boolean isFinished() {
        return false;
      }
    });

    tab.add(new InstantCommand("Reset Encoder") {

      {
        requires(Wrist.this);
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
    TalonSettings.configure(talon);
  }


  @Override
  public void periodic() {
    super.periodic();
    talonTelemetry.run();

  }


}
