package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class TalonUtil {

  private static final Set<ControlMode> CLOSED_LOOP_MODES
      = EnumSet.complementOf(EnumSet.of(ControlMode.Disabled, ControlMode.Follower, ControlMode.PercentOutput));
  private static final Set<ControlMode> MOTION_PROFILE_MODES
      = EnumSet.of(ControlMode.MotionMagic, ControlMode.MotionProfile, ControlMode.MotionProfileArc);

  public static SlotConfiguration getSlotConfiguration(TalonSRXConfiguration srx, int slotID) {
    switch (slotID) {
      case 0:
        return srx.slot0;
      case 1:
        return srx.slot1;
      case 2:
        return srx.slot2;
      case 3:
        return srx.slot3;
      default:
        throw new IllegalArgumentException("Invalid Slot:" + slotID);
    }
  }

  public static Runnable basicTelemetry(TalonSubsystem subsystem) {
    return new BasicTelemetry(subsystem);
  }

  public static Runnable basicTelemetryWithEncoder(TalonSubsystem subsystem) {
    return new BasicEncoderTelemetry(subsystem);
  }

  public static Runnable closedLoopTelemetry(TalonSubsystem subsystem) {
    return new ClosedLoopTelemetry(subsystem);
  }

  public static Runnable motionMagicTelemetry(TalonSubsystem subsystem) {
    return new MotionMagicTelemetry(subsystem);
  }

  private static class BasicTelemetry implements Runnable {
    protected final TalonSubsystem subsystem;
    //protected final NetworkTable table;
    final ShuffleboardLayout container;

    final NetworkTableEntry outputPercent;
    final NetworkTableEntry outputVoltage;
    final NetworkTableEntry outputCurrent;

    BasicTelemetry(TalonSubsystem subsystem) {
      this.subsystem = subsystem;
      //table = NetworkTableInstance.getDefault().getTable(subsystem.getName()+"Telemetry");
      container = subsystem.tab.getLayout("talon telemetry", BuiltInLayouts.kList)
                      .withPosition(8, 0)
                      .withSize(2, 4)
                      .withProperties(Map.of("Label position", "LEFT"));

      outputPercent = container.add("outputPercent", 0.0).getEntry(); //table.getEntry("outputPercent");
      outputVoltage = container.add("outputVoltage", 0.0).getEntry();//table.getEntry("outputVoltage");
      outputCurrent = container.add("outputCurrent", 0.0).getEntry();//table.getEntry("outputCurrent");
    }


    @Override
    public void run() {
      outputPercent.setDouble(subsystem.talon.getMotorOutputPercent());
      outputVoltage.setDouble(subsystem.talon.getMotorOutputVoltage());
      outputCurrent.setDouble(subsystem.talon.getOutputCurrent());
    }

  }

  private static class BasicEncoderTelemetry extends BasicTelemetry {
    final NetworkTableEntry position;
    final NetworkTableEntry velocity;


    BasicEncoderTelemetry(TalonSubsystem subsystem) {
      super(subsystem);
      position = container.add("position", 0.0).getEntry();//table.getEntry("position");
      velocity = container.add("velocity", 0.0).getEntry();//table.getEntry("velocity");
    }

    @Override
    public void run() {
      super.run();
      position.setDouble(subsystem.talon.getSelectedSensorPosition());
      velocity.setDouble(subsystem.talon.getSelectedSensorVelocity());
    }
  }

  private static class ClosedLoopTelemetry extends BasicEncoderTelemetry {

    final NetworkTableEntry target;
    final NetworkTableEntry error;
    final NetworkTableEntry errorDerivative;
    final NetworkTableEntry iAccum;

    ClosedLoopTelemetry(TalonSubsystem subsystem) {
      super(subsystem);
      target = container.add("target", 0.0).getEntry();//table.getEntry("target");
      error = container.add("error", 0.0).getEntry();
      errorDerivative = container.add("errorDeriv", 0.0).getEntry();
      iAccum = container.add("iAccum", 0.0).getEntry();//table.getEntry("iAccum");

    }

    @Override
    public void run() {
      super.run();
      if (!CLOSED_LOOP_MODES.contains(subsystem.talon.getControlMode())) return;

      target.setDouble(subsystem.talon.getClosedLoopTarget());
      error.setDouble(subsystem.talon.getClosedLoopError());
      errorDerivative.setDouble(subsystem.talon.getErrorDerivative());
      iAccum.setDouble(subsystem.talon.getIntegralAccumulator());

    }
  }

  private static class MotionMagicTelemetry extends ClosedLoopTelemetry {

    final NetworkTableEntry trajPosition;
    final NetworkTableEntry trajVelocity;
    final NetworkTableEntry trajFF;

    MotionMagicTelemetry(TalonSubsystem subsystem) {
      super(subsystem);
      trajPosition = container.add("trajPosition", 0.0).getEntry();//table.getEntry("trajPosition");
      trajVelocity = container.add("trajVelocity", 0.0).getEntry();//table.getEntry("trajVelocity");
      trajFF = container.add("trajFF", 0.0).getEntry();//table.getEntry("trajFF");
    }

    @Override
    public void run() {
      super.run();
      if (!MOTION_PROFILE_MODES.contains(subsystem.talon.getControlMode())) return;

      trajPosition.setDouble(subsystem.talon.getActiveTrajectoryPosition());
      trajVelocity.setDouble(subsystem.talon.getActiveTrajectoryVelocity());
      trajFF.setDouble(subsystem.talon.getActiveTrajectoryArbFeedFwd());
    }
  }
}
