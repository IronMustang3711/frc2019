package org.usfirst.frc3711.deepspace.talon;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.FilterConfiguration;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;

@SuppressWarnings("WeakerAccess")
public final class TalonConfigBuilder {
  /**
   * Primary PID configuration
   */
  public TalonSRXPIDSetConfiguration primaryPID;
  /**
   * Auxiliary PID configuration
   */
  public TalonSRXPIDSetConfiguration auxiliaryPID;
  /**
   * Forward Limit Switch Source
   * <p>
   * User can choose between the feedback connector, remote Talon SRX, CANifier, or deactivate the feature
   */
  public LimitSwitchSource forwardLimitSwitchSource;
  /**
   * Reverse Limit Switch Source
   * <p>
   * User can choose between the feedback connector, remote Talon SRX, CANifier, or deactivate the feature
   */
  public LimitSwitchSource reverseLimitSwitchSource;
  /**
   * Forward limit switch device ID
   * <p>
   * Limit Switch device id isn't used unless device is a remote
   */
  public int forwardLimitSwitchDeviceID;
  /**
   * Reverse limit switch device ID
   * <p>
   * Limit Switch device id isn't used unless device is a remote
   */
  public int reverseLimitSwitchDeviceID;
  /**
   * Forward limit switch normally open/closed
   */
  public LimitSwitchNormal forwardLimitSwitchNormal;
  /**
   * Reverse limit switch normally open/closed
   */
  public LimitSwitchNormal reverseLimitSwitchNormal;
  /**
   * Feedback Device for Sum 0 Term
   */
  public FeedbackDevice sum0Term;
  /**
   * Feedback Device for Sum 1 Term
   */
  public FeedbackDevice sum1Term;
  /**
   * Feedback Device for Diff 0 Term
   */
  public FeedbackDevice diff0Term;
  /**
   * Feedback Device for Diff 1 Term
   */
  public FeedbackDevice diff1Term;
  /**
   * Peak current in amps
   * <p>
   * Current limit is activated when current exceeds the peak limit for longer
   * than the peak duration. Then software will limit to the continuous limit.
   * This ensures current limiting while allowing for momentary excess current
   * events.
   */
  public int peakCurrentLimit;
  /**
   * Peak Current duration in milliseconds
   * <p>
   * Current limit is activated when current exceeds the peak limit for longer
   * than the peak duration. Then software will limit to the continuous limit.
   * This ensures current limiting while allowing for momentary excess current
   * events.
   */
  public int peakCurrentDuration;
  /**
   * Continuous current in amps
   * <p>
   * Current limit is activated when current exceeds the peak limit for longer
   * than the peak duration. Then software will limit to the continuous limit.
   * This ensures current limiting while allowing for momentary excess current
   * events.
   */
  public int continuousCurrentLimit;
  /**
   * Seconds to go from 0 to full in open loop
   */
  public double openloopRamp;
  /**
   * Seconds to go from 0 to full in closed loop
   */
  public double closedloopRamp;
  /**
   * Peak output in forward direction [0,1]
   */
  public double peakOutputForward;
  /**
   * Peak output in reverse direction [-1,0]
   */
  public double peakOutputReverse;
  /**
   * Nominal/Minimum output in forward direction [0,1]
   */
  public double nominalOutputForward;
  /**
   * Nominal/Minimum output in reverse direction [-1,0]
   */
  public double nominalOutputReverse;
  /**
   * Neutral deadband [0.001, 0.25]
   */
  public double neutralDeadband;
  /**
   * This is the max voltage to apply to the hbridge when voltage
   * compensation is enabled.  For example, if 10 (volts) is specified
   * and a TalonSRX is commanded to 0.5 (PercentOutput, closed-loop, etc)
   * then the TalonSRX will attempt to apply a duty-cycle to produce 5V.
   */
  public double voltageCompSaturation;
  /**
   * Number of samples in rolling average for voltage
   */
  public int voltageMeasurementFilter;
  /**
   * Desired period for velocity measurement
   */
  public VelocityMeasPeriod velocityMeasurementPeriod;
  /**
   * Desired window for velocity measurement
   */
  public int velocityMeasurementWindow;
  /**
   * Threshold for soft limits in forward direction (in raw sensor units)
   */
  public int forwardSoftLimitThreshold;
  /**
   * Threshold for soft limits in reverse direction (in raw sensor units)
   */
  public int reverseSoftLimitThreshold;
  /**
   * Enable forward soft limit
   */
  public boolean forwardSoftLimitEnable;
  /**
   * Enable reverse soft limit
   */
  public boolean reverseSoftLimitEnable;
  /**
   * Configuration for slot 0
   */
  public SlotConfiguration slot0;
  /**
   * Configuration for slot 1
   */
  public SlotConfiguration slot1;
  /**
   * Configuration for slot 2
   */
  public SlotConfiguration slot2;
  /**
   * Configuration for slot 3
   */
  public SlotConfiguration slot3;
  /**
   * PID polarity inversion
   * <p>
   * Standard Polarity:
   * Primary Output = PID0 + PID1,
   * Auxiliary Output = PID0 - PID1,
   * <p>
   * Inverted Polarity:
   * Primary Output = PID0 - PID1,
   * Auxiliary Output = PID0 + PID1,
   */
  public boolean auxPIDPolarity;
  /**
   * Configuration for RemoteFilter 0
   */
  public FilterConfiguration remoteFilter0;
  /**
   * Configuration for RemoteFilter 1
   */
  public FilterConfiguration remoteFilter1;
  /**
   * Motion Magic cruise velocity in raw sensor units per 100 ms.
   */
  public int motionCruiseVelocity;
  /**
   * Motion Magic acceleration in (raw sensor units per 100 ms) per second.
   */
  public int motionAcceleration;
  /**
   * Zero to use trapezoidal motion during motion magic.  [1,8] for S-Curve, higher value for greater smoothing.
   */
  public int motionCurveStrength;
  /**
   * Motion profile base trajectory period in milliseconds.
   * <p>
   * The period specified in a trajectory point will be
   * added on to this value
   */
  public int motionProfileTrajectoryPeriod;
  /**
   * Determine whether feedback sensor is continuous or not
   */
  public boolean feedbackNotContinuous;
  /**
   * Disable neutral'ing the motor when remote sensor is lost on CAN bus
   */
  public boolean remoteSensorClosedLoopDisableNeutralOnLOS;
  /**
   * Clear the position on forward limit
   */
  public boolean clearPositionOnLimitF;
  /**
   * Clear the position on reverse limit
   */
  public boolean clearPositionOnLimitR;
  /**
   * Clear the position on index
   */
  public boolean clearPositionOnQuadIdx;
  /**
   * Disable neutral'ing the motor when remote limit switch is lost on CAN bus
   */
  public boolean limitSwitchDisableNeutralOnLOS;
  /**
   * Disable neutral'ing the motor when remote soft limit is lost on CAN bus
   */
  public boolean softLimitDisableNeutralOnLOS;
  /**
   * Number of edges per rotation for a tachometer sensor
   */
  public int pulseWidthPeriod_EdgesPerRot;
  /**
   * Desired window size for a tachometer sensor
   */
  public int pulseWidthPeriod_FilterWindowSz;
  /**
   * Enable motion profile trajectory point interpolation (defaults to true).
   */
  public boolean trajectoryInterpolationEnable;
  /**
   * Custom Param 0
   */
  public int customParam0;
  /**
   * Custom Param 1
   */
  public int customParam1;
  /**
   * Enable optimizations for ConfigAll (defaults true)
   */
  public boolean enableOptimizations;

  private TalonConfigBuilder(TalonSRXConfiguration base) {
    copyFrom(base);
  }
  private TalonConfigBuilder(){
    this(new TalonSRXConfiguration());
  }

  public static TalonConfigBuilder newBuilder() {
    return new TalonConfigBuilder();
  }
  public static TalonConfigBuilder from(TalonSRXConfiguration baseConfig){
    return new TalonConfigBuilder(baseConfig);
  }


  public TalonConfigBuilder withPrimaryPID(TalonSRXPIDSetConfiguration primaryPID) {
    this.primaryPID = primaryPID;
    return this;
  }

  public TalonConfigBuilder withAuxiliaryPID(TalonSRXPIDSetConfiguration auxiliaryPID) {
    this.auxiliaryPID = auxiliaryPID;
    return this;
  }

  public TalonConfigBuilder withForwardLimitSwitchSource(LimitSwitchSource forwardLimitSwitchSource) {
    this.forwardLimitSwitchSource = forwardLimitSwitchSource;
    return this;
  }

  public TalonConfigBuilder withReverseLimitSwitchSource(LimitSwitchSource reverseLimitSwitchSource) {
    this.reverseLimitSwitchSource = reverseLimitSwitchSource;
    return this;
  }

  public TalonConfigBuilder withForwardLimitSwitchDeviceID(int forwardLimitSwitchDeviceID) {
    this.forwardLimitSwitchDeviceID = forwardLimitSwitchDeviceID;
    return this;
  }

  public TalonConfigBuilder withReverseLimitSwitchDeviceID(int reverseLimitSwitchDeviceID) {
    this.reverseLimitSwitchDeviceID = reverseLimitSwitchDeviceID;
    return this;
  }

  public TalonConfigBuilder withForwardLimitSwitchNormal(LimitSwitchNormal forwardLimitSwitchNormal) {
    this.forwardLimitSwitchNormal = forwardLimitSwitchNormal;
    return this;
  }

  public TalonConfigBuilder withReverseLimitSwitchNormal(LimitSwitchNormal reverseLimitSwitchNormal) {
    this.reverseLimitSwitchNormal = reverseLimitSwitchNormal;
    return this;
  }

  public TalonConfigBuilder withSum0Term(FeedbackDevice sum0Term) {
    this.sum0Term = sum0Term;
    return this;
  }

  public TalonConfigBuilder withSum1Term(FeedbackDevice sum1Term) {
    this.sum1Term = sum1Term;
    return this;
  }

  public TalonConfigBuilder withDiff0Term(FeedbackDevice diff0Term) {
    this.diff0Term = diff0Term;
    return this;
  }

  public TalonConfigBuilder withDiff1Term(FeedbackDevice diff1Term) {
    this.diff1Term = diff1Term;
    return this;
  }

  public TalonConfigBuilder withPeakCurrentLimit(int peakCurrentLimit) {
    this.peakCurrentLimit = peakCurrentLimit;
    return this;
  }

  public TalonConfigBuilder withPeakCurrentDuration(int peakCurrentDuration) {
    this.peakCurrentDuration = peakCurrentDuration;
    return this;
  }

  public TalonConfigBuilder withContinuousCurrentLimit(int continuousCurrentLimit) {
    this.continuousCurrentLimit = continuousCurrentLimit;
    return this;
  }

  public TalonConfigBuilder withOpenloopRamp(double openloopRamp) {
    this.openloopRamp = openloopRamp;
    return this;
  }

  public TalonConfigBuilder withClosedloopRamp(double closedloopRamp) {
    this.closedloopRamp = closedloopRamp;
    return this;
  }

  public TalonConfigBuilder withPeakOutputForward(double peakOutputForward) {
    this.peakOutputForward = peakOutputForward;
    return this;
  }

  public TalonConfigBuilder withPeakOutputReverse(double peakOutputReverse) {
    this.peakOutputReverse = peakOutputReverse;
    return this;
  }

  public TalonConfigBuilder withNominalOutputForward(double nominalOutputForward) {
    this.nominalOutputForward = nominalOutputForward;
    return this;
  }

  public TalonConfigBuilder withNominalOutputReverse(double nominalOutputReverse) {
    this.nominalOutputReverse = nominalOutputReverse;
    return this;
  }

  public TalonConfigBuilder withNeutralDeadband(double neutralDeadband) {
    this.neutralDeadband = neutralDeadband;
    return this;
  }

  public TalonConfigBuilder withVoltageCompSaturation(double voltageCompSaturation) {
    this.voltageCompSaturation = voltageCompSaturation;
    return this;
  }

  public TalonConfigBuilder withVoltageMeasurementFilter(int voltageMeasurementFilter) {
    this.voltageMeasurementFilter = voltageMeasurementFilter;
    return this;
  }

  public TalonConfigBuilder withVelocityMeasurementPeriod(VelocityMeasPeriod velocityMeasurementPeriod) {
    this.velocityMeasurementPeriod = velocityMeasurementPeriod;
    return this;
  }

  public TalonConfigBuilder withVelocityMeasurementWindow(int velocityMeasurementWindow) {
    this.velocityMeasurementWindow = velocityMeasurementWindow;
    return this;
  }

  public TalonConfigBuilder withForwardSoftLimitThreshold(int forwardSoftLimitThreshold) {
    this.forwardSoftLimitThreshold = forwardSoftLimitThreshold;
    return this;
  }

  public TalonConfigBuilder withReverseSoftLimitThreshold(int reverseSoftLimitThreshold) {
    this.reverseSoftLimitThreshold = reverseSoftLimitThreshold;
    return this;
  }

  public TalonConfigBuilder withForwardSoftLimitEnable(boolean forwardSoftLimitEnable) {
    this.forwardSoftLimitEnable = forwardSoftLimitEnable;
    return this;
  }

  public TalonConfigBuilder withReverseSoftLimitEnable(boolean reverseSoftLimitEnable) {
    this.reverseSoftLimitEnable = reverseSoftLimitEnable;
    return this;
  }

  public TalonConfigBuilder withSlot0(SlotConfiguration slot0) {
    this.slot0 = slot0;
    return this;
  }

  public TalonConfigBuilder withSlot1(SlotConfiguration slot1) {
    this.slot1 = slot1;
    return this;
  }

  public TalonConfigBuilder withSlot2(SlotConfiguration slot2) {
    this.slot2 = slot2;
    return this;
  }

  public TalonConfigBuilder withSlot3(SlotConfiguration slot3) {
    this.slot3 = slot3;
    return this;
  }

  public TalonConfigBuilder withAuxPIDPolarity(boolean auxPIDPolarity) {
    this.auxPIDPolarity = auxPIDPolarity;
    return this;
  }

  public TalonConfigBuilder withRemoteFilter0(FilterConfiguration remoteFilter0) {
    this.remoteFilter0 = remoteFilter0;
    return this;
  }

  public TalonConfigBuilder withRemoteFilter1(FilterConfiguration remoteFilter1) {
    this.remoteFilter1 = remoteFilter1;
    return this;
  }

  public TalonConfigBuilder withMotionCruiseVelocity(int motionCruiseVelocity) {
    this.motionCruiseVelocity = motionCruiseVelocity;
    return this;
  }

  public TalonConfigBuilder withMotionAcceleration(int motionAcceleration) {
    this.motionAcceleration = motionAcceleration;
    return this;
  }

  public TalonConfigBuilder withMotionCurveStrength(int motionCurveStrength) {
    this.motionCurveStrength = motionCurveStrength;
    return this;
  }

  public TalonConfigBuilder withMotionProfileTrajectoryPeriod(int motionProfileTrajectoryPeriod) {
    this.motionProfileTrajectoryPeriod = motionProfileTrajectoryPeriod;
    return this;
  }

  public TalonConfigBuilder withFeedbackNotContinuous(boolean feedbackNotContinuous) {
    this.feedbackNotContinuous = feedbackNotContinuous;
    return this;
  }

  public TalonConfigBuilder withRemoteSensorClosedLoopDisableNeutralOnLOS(boolean remoteSensorClosedLoopDisableNeutralOnLOS) {
    this.remoteSensorClosedLoopDisableNeutralOnLOS = remoteSensorClosedLoopDisableNeutralOnLOS;
    return this;
  }

  public TalonConfigBuilder withClearPositionOnLimitF(boolean clearPositionOnLimitF) {
    this.clearPositionOnLimitF = clearPositionOnLimitF;
    return this;
  }

  public TalonConfigBuilder withClearPositionOnLimitR(boolean clearPositionOnLimitR) {
    this.clearPositionOnLimitR = clearPositionOnLimitR;
    return this;
  }

  public TalonConfigBuilder withClearPositionOnQuadIdx(boolean clearPositionOnQuadIdx) {
    this.clearPositionOnQuadIdx = clearPositionOnQuadIdx;
    return this;
  }

  public TalonConfigBuilder withLimitSwitchDisableNeutralOnLOS(boolean limitSwitchDisableNeutralOnLOS) {
    this.limitSwitchDisableNeutralOnLOS = limitSwitchDisableNeutralOnLOS;
    return this;
  }

  public TalonConfigBuilder withSoftLimitDisableNeutralOnLOS(boolean softLimitDisableNeutralOnLOS) {
    this.softLimitDisableNeutralOnLOS = softLimitDisableNeutralOnLOS;
    return this;
  }

  public TalonConfigBuilder withPulseWidthPeriod_EdgesPerRot(int pulseWidthPeriod_EdgesPerRot) {
    this.pulseWidthPeriod_EdgesPerRot = pulseWidthPeriod_EdgesPerRot;
    return this;
  }

  public TalonConfigBuilder withPulseWidthPeriod_FilterWindowSz(int pulseWidthPeriod_FilterWindowSz) {
    this.pulseWidthPeriod_FilterWindowSz = pulseWidthPeriod_FilterWindowSz;
    return this;
  }

  public TalonConfigBuilder withTrajectoryInterpolationEnable(boolean trajectoryInterpolationEnable) {
    this.trajectoryInterpolationEnable = trajectoryInterpolationEnable;
    return this;
  }

  public TalonConfigBuilder withCustomParam0(int customParam0) {
    this.customParam0 = customParam0;
    return this;
  }

  public TalonConfigBuilder withCustomParam1(int customParam1) {
    this.customParam1 = customParam1;
    return this;
  }

  public TalonConfigBuilder withEnableOptimizations(boolean enableOptimizations) {
    this.enableOptimizations = enableOptimizations;
    return this;
  }

  public TalonConfigBuilder withSlot(SlotConfigurationEx slot){
    switch (slot.slotID){
      case 0: return withSlot0(slot);
      case 1: return withSlot1(slot);
      case 2: return withSlot2(slot);
      case 3: return withSlot3(slot);
      default: throw new IllegalArgumentException("Invalid slot ID: "+slot.slotID);
    }
  }

  public TalonConfigBuilder but() {
    return newBuilder().withPrimaryPID(primaryPID)
                       .withAuxiliaryPID(auxiliaryPID)
                       .withForwardLimitSwitchSource(forwardLimitSwitchSource)
                       .withReverseLimitSwitchSource(reverseLimitSwitchSource)
                       .withForwardLimitSwitchDeviceID(forwardLimitSwitchDeviceID)
                       .withReverseLimitSwitchDeviceID(reverseLimitSwitchDeviceID)
                       .withForwardLimitSwitchNormal(forwardLimitSwitchNormal)
                       .withReverseLimitSwitchNormal(reverseLimitSwitchNormal)
                       .withSum0Term(sum0Term)
                       .withSum1Term(sum1Term)
                       .withDiff0Term(diff0Term)
                       .withDiff1Term(diff1Term)
                       .withPeakCurrentLimit(peakCurrentLimit)
                       .withPeakCurrentDuration(peakCurrentDuration)
                       .withContinuousCurrentLimit(continuousCurrentLimit)
                       .withOpenloopRamp(openloopRamp)
                       .withClosedloopRamp(closedloopRamp)
                       .withPeakOutputForward(peakOutputForward)
                       .withPeakOutputReverse(peakOutputReverse)
                       .withNominalOutputForward(nominalOutputForward)
                       .withNominalOutputReverse(nominalOutputReverse)
                       .withNeutralDeadband(neutralDeadband)
                       .withVoltageCompSaturation(voltageCompSaturation)
                       .withVoltageMeasurementFilter(voltageMeasurementFilter)
                       .withVelocityMeasurementPeriod(velocityMeasurementPeriod)
                       .withVelocityMeasurementWindow(velocityMeasurementWindow)
                       .withForwardSoftLimitThreshold(forwardSoftLimitThreshold)
                       .withReverseSoftLimitThreshold(reverseSoftLimitThreshold)
                       .withForwardSoftLimitEnable(forwardSoftLimitEnable)
                       .withReverseSoftLimitEnable(reverseSoftLimitEnable)
                       .withSlot0(slot0)
                       .withSlot1(slot1)
                       .withSlot2(slot2)
                       .withSlot3(slot3)
                       .withAuxPIDPolarity(auxPIDPolarity)
                       .withRemoteFilter0(remoteFilter0)
                       .withRemoteFilter1(remoteFilter1)
                       .withMotionCruiseVelocity(motionCruiseVelocity)
                       .withMotionAcceleration(motionAcceleration)
                       .withMotionCurveStrength(motionCurveStrength)
                       .withMotionProfileTrajectoryPeriod(motionProfileTrajectoryPeriod)
                       .withFeedbackNotContinuous(feedbackNotContinuous)
                       .withRemoteSensorClosedLoopDisableNeutralOnLOS(
                                                        remoteSensorClosedLoopDisableNeutralOnLOS)
                       .withClearPositionOnLimitF(clearPositionOnLimitF)
                       .withClearPositionOnLimitR(clearPositionOnLimitR)
                       .withClearPositionOnQuadIdx(clearPositionOnQuadIdx)
                       .withLimitSwitchDisableNeutralOnLOS(limitSwitchDisableNeutralOnLOS)
                       .withSoftLimitDisableNeutralOnLOS(softLimitDisableNeutralOnLOS)
                       .withPulseWidthPeriod_EdgesPerRot(pulseWidthPeriod_EdgesPerRot)
                       .withPulseWidthPeriod_FilterWindowSz(pulseWidthPeriod_FilterWindowSz)
                       .withTrajectoryInterpolationEnable(trajectoryInterpolationEnable)
                       .withCustomParam0(customParam0)
                       .withCustomParam1(customParam1)
                       .withEnableOptimizations(enableOptimizations);
  }
  

  private void copyFrom(TalonSRXConfiguration from){

    this.voltageMeasurementFilter = from.voltageMeasurementFilter;
    this.slot1 = from.slot1;
    this.primaryPID = from.primaryPID;
    this.velocityMeasurementPeriod = from.velocityMeasurementPeriod;
    this.reverseLimitSwitchSource = from.reverseLimitSwitchSource;
    this.reverseSoftLimitEnable = from.reverseSoftLimitEnable;
    this.nominalOutputForward = from.nominalOutputForward;
    this.motionProfileTrajectoryPeriod = from.motionProfileTrajectoryPeriod;
    this.auxiliaryPID = from.auxiliaryPID;
    this.forwardLimitSwitchDeviceID = from.forwardLimitSwitchDeviceID;
    this.pulseWidthPeriod_FilterWindowSz = from.pulseWidthPeriod_FilterWindowSz;
    this.motionAcceleration = from.motionAcceleration;
    this.forwardSoftLimitEnable = from.forwardSoftLimitEnable;
    this.neutralDeadband = from.neutralDeadband;
    this.clearPositionOnLimitF = from.clearPositionOnLimitF;
    this.slot0 = from.slot0;
    this.slot3 = from.slot3;
    this.reverseSoftLimitThreshold = from.reverseSoftLimitThreshold;
    this.openloopRamp = from.openloopRamp;
    this.diff0Term = from.diff0Term;
    this.closedloopRamp = from.closedloopRamp;
    this.peakOutputForward = from.peakOutputForward;
    this.clearPositionOnLimitR = from.clearPositionOnLimitR;
    this.slot2 = from.slot2;
    this.auxPIDPolarity = from.auxPIDPolarity;
    this.feedbackNotContinuous = from.feedbackNotContinuous;
    this.peakCurrentLimit = from.peakCurrentLimit;
    this.motionCruiseVelocity = from.motionCruiseVelocity;
    this.clearPositionOnQuadIdx = from.clearPositionOnQuadIdx;
    this.peakOutputReverse = from.peakOutputReverse;
    this.pulseWidthPeriod_EdgesPerRot = from.pulseWidthPeriod_EdgesPerRot;
    this.forwardLimitSwitchSource = from.forwardLimitSwitchSource;
    this.reverseLimitSwitchDeviceID = from.reverseLimitSwitchDeviceID;
    this.diff1Term = from.diff1Term;
    this.remoteFilter0 = from.remoteFilter0;
    this.customParam0 = from.customParam0;
    this.continuousCurrentLimit = from.continuousCurrentLimit;
    this.velocityMeasurementWindow = from.velocityMeasurementWindow;
    this.softLimitDisableNeutralOnLOS = from.softLimitDisableNeutralOnLOS;
    this.forwardSoftLimitThreshold = from.forwardSoftLimitThreshold;
    this.motionCurveStrength = from.motionCurveStrength;
    this.nominalOutputReverse = from.nominalOutputReverse;
    this.limitSwitchDisableNeutralOnLOS = from.limitSwitchDisableNeutralOnLOS;
    this.remoteSensorClosedLoopDisableNeutralOnLOS = from.remoteSensorClosedLoopDisableNeutralOnLOS;
    this.trajectoryInterpolationEnable = from.trajectoryInterpolationEnable;
    this.reverseLimitSwitchNormal = from.reverseLimitSwitchNormal;
    this.sum1Term = from.sum1Term;
    this.peakCurrentDuration = from.peakCurrentDuration;
    this.forwardLimitSwitchNormal = from.forwardLimitSwitchNormal;
    this.enableOptimizations = from.enableOptimizations;
    this.sum0Term = from.sum0Term;
    this.customParam1 = from.customParam1;
    this.voltageCompSaturation = from.voltageCompSaturation;
    this.remoteFilter1 = from.remoteFilter1;
  }


  public TalonSRXConfiguration build() {
    TalonSRXConfiguration config = new TalonSRXConfiguration();
    config.voltageMeasurementFilter = this.voltageMeasurementFilter;
    config.slot1 = this.slot1;
    config.primaryPID = this.primaryPID;
    config.velocityMeasurementPeriod = this.velocityMeasurementPeriod;
    config.reverseLimitSwitchSource = this.reverseLimitSwitchSource;
    config.reverseSoftLimitEnable = this.reverseSoftLimitEnable;
    config.nominalOutputForward = this.nominalOutputForward;
    config.motionProfileTrajectoryPeriod = this.motionProfileTrajectoryPeriod;
    config.auxiliaryPID = this.auxiliaryPID;
    config.forwardLimitSwitchDeviceID = this.forwardLimitSwitchDeviceID;
    config.pulseWidthPeriod_FilterWindowSz = this.pulseWidthPeriod_FilterWindowSz;
    config.motionAcceleration = this.motionAcceleration;
    config.forwardSoftLimitEnable = this.forwardSoftLimitEnable;
    config.neutralDeadband = this.neutralDeadband;
    config.clearPositionOnLimitF = this.clearPositionOnLimitF;
    config.slot0 = this.slot0;
    config.slot3 = this.slot3;
    config.reverseSoftLimitThreshold = this.reverseSoftLimitThreshold;
    config.openloopRamp = this.openloopRamp;
    config.diff0Term = this.diff0Term;
    config.closedloopRamp = this.closedloopRamp;
    config.peakOutputForward = this.peakOutputForward;
    config.clearPositionOnLimitR = this.clearPositionOnLimitR;
    config.slot2 = this.slot2;
    config.auxPIDPolarity = this.auxPIDPolarity;
    config.feedbackNotContinuous = this.feedbackNotContinuous;
    config.peakCurrentLimit = this.peakCurrentLimit;
    config.motionCruiseVelocity = this.motionCruiseVelocity;
    config.clearPositionOnQuadIdx = this.clearPositionOnQuadIdx;
    config.peakOutputReverse = this.peakOutputReverse;
    config.pulseWidthPeriod_EdgesPerRot = this.pulseWidthPeriod_EdgesPerRot;
    config.forwardLimitSwitchSource = this.forwardLimitSwitchSource;
    config.reverseLimitSwitchDeviceID = this.reverseLimitSwitchDeviceID;
    config.diff1Term = this.diff1Term;
    config.remoteFilter0 = this.remoteFilter0;
    config.customParam0 = this.customParam0;
    config.continuousCurrentLimit = this.continuousCurrentLimit;
    config.velocityMeasurementWindow = this.velocityMeasurementWindow;
    config.softLimitDisableNeutralOnLOS = this.softLimitDisableNeutralOnLOS;
    config.forwardSoftLimitThreshold = this.forwardSoftLimitThreshold;
    config.motionCurveStrength = this.motionCurveStrength;
    config.nominalOutputReverse = this.nominalOutputReverse;
    config.limitSwitchDisableNeutralOnLOS = this.limitSwitchDisableNeutralOnLOS;
    config.remoteSensorClosedLoopDisableNeutralOnLOS = this.remoteSensorClosedLoopDisableNeutralOnLOS;
    config.trajectoryInterpolationEnable = this.trajectoryInterpolationEnable;
    config.reverseLimitSwitchNormal = this.reverseLimitSwitchNormal;
    config.sum1Term = this.sum1Term;
    config.peakCurrentDuration = this.peakCurrentDuration;
    config.forwardLimitSwitchNormal = this.forwardLimitSwitchNormal;
    config.enableOptimizations = this.enableOptimizations;
    config.sum0Term = this.sum0Term;
    config.customParam1 = this.customParam1;
    config.voltageCompSaturation = this.voltageCompSaturation;
    config.remoteFilter1 = this.remoteFilter1;
    return config;
  }
}
