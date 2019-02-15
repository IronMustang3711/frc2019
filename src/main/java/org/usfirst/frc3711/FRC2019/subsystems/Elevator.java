package org.usfirst.frc3711.FRC2019.subsystems;


import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc3711.FRC2019.TalonID;
import org.usfirst.frc3711.FRC2019.commands.util.MotionMagicSetpoint;
import org.usfirst.frc3711.FRC2019.talon.SlotConfigBuilder;
import org.usfirst.frc3711.FRC2019.talon.TalonUtil;

//TODO set brake mode to neutral when disabled
public class Elevator extends TalonSubsystem {
  @SuppressWarnings("WeakerAccess")
  static class TalonSettings {
    static class PIDSlots {
      public static final SlotConfiguration POSITION_SLOT =
          SlotConfigBuilder.newBuilder()
              .withKP(1.0)
              .build();


      /*
      config.slot0.integralZone = 1000;
      config.slot0.allowableClosedloopError = 10;
      config.slot0.maxIntegralAccumulator = 254.000000;
      config.slot0.closedLoopPeakOutput = 1.0;
      config.slot0.closedLoopPeriod = 1;
      todo: reapply integral?
       */
      public static final SlotConfiguration MM_SLOT =
          SlotConfigBuilder.builderWithBaseConfiguration(POSITION_SLOT)
              .withKF(1.0)
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

      public static ControlMode controlModeForSlot(int slot) {
        switch (slot) {
          case 0:
            return ControlMode.MotionMagic;
          case 1:
            return ControlMode.Position;
          default:
            throw new IllegalArgumentException("Invalid Slot: " + slot);
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

      config.forwardSoftLimitThreshold = 13112;
      config.reverseSoftLimitThreshold = -6707;
      config.forwardSoftLimitEnable = true;
      config.reverseSoftLimitEnable = true;


      config.openloopRamp = 1.0;
      // config.closedloopRamp = 1.705000;

      config.motionCruiseVelocity = 800;
      config.motionAcceleration = 700;

      config.peakOutputForward = 0.7;
      config.peakOutputReverse = -0.4;



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
      config.voltageCompSaturation = 9.0;


              /*
        After setting the three configurations, current limiting must be enabled via enableCurrentLimit() or LabVIEW VI.
         */
      config.peakCurrentLimit = 820;
      config.peakCurrentDuration = 1000;
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

      talon.enableVoltageCompensation(true);
      talon.enableCurrentLimit(true);

      talon.getAllConfigs(CONFIGURATION);
      applyConfig(CONFIGURATION);
      talon.configAllSettings(CONFIGURATION);


    }
  }


  private final Runnable talonTelemetry;

  public Elevator() {


    super(Elevator.class.getSimpleName(), TalonID.ELEVATOR.getId());

    talonTelemetry = TalonUtil.motionMagicTelemetry(this);
    tab.add(this);

    // TalonTelemetry.installClosedLoopTelemetry(this);

    // tab.add(addChildItem("motion magic thing",new MotionMagicSetpoint.Wrapper(this)));

    tab.add(new Command("closed loop control(Position)") {

      {
        requires(Elevator.this);
      }

      @Override
      protected void execute() {
        // if (ntClosedLoopEnabled.getBoolean(false)) {
        talon.set(ControlMode.MotionMagic, ntSetpoint.getDouble(0.0));
        //}
      }


      @Override
      protected boolean isFinished() {
        return false;
      }
    });

    tab.add(new InstantCommand("Reset Encoder") {

      @Override
      protected void execute() {
        talon.setSelectedSensorPosition(0);
        talon.getSensorCollection().setQuadraturePosition(0, 50);

      }

    });

//    MotionMagicSetpoint motionMagicSetpoint = new MotionMagicSetpoint(this);
//    tab.add(motionMagicSetpoint);
//
//    ntSetpoint.addListener(entryNotification -> {
//      System.out.println("elevator setpoint updated: " + entryNotification.value.getDouble());
//      motionMagicSetpoint.setSetpoint(entryNotification.value.getDouble());
//    }, EntryListenerFlags.kUpdate);

    MotionMagicSetpoint top = new MotionMagicSetpoint("top", this, 7000);
    top.setSetpoint(7000);
    addChild("top", top);
    tab.add(top);

    MotionMagicSetpoint home = new MotionMagicSetpoint("bottom", this, 1000);
    home.setSetpoint(1000);
    addChild("home", home);
    tab.add(home);

//       Sendable s3 = new TalonLiveWindowSupport(talon);
//
//       addChild("closed loop stuff(LW)",s3);
//       tab.add(s3);

  }


  void setSetpoint(double position, boolean mm) {
    if (mm)
      talon.set(ControlMode.MotionMagic, position);
    else
      talon.set(ControlMode.Position, position);
  }


  @Override
  void configureTalon() {
    super.configureTalon();
    TalonSettings.configure(talon);
  }

  @Override
  public void initDefaultCommand() {

  }

  @Override
  public void periodic() {
    super.periodic();
    talonTelemetry.run();
  }

}

