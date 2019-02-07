package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;

@SuppressWarnings({"WeakerAccess", "unused"})
public  final class SlotConfigBuilder {

//	private int slotId;
//
//	private ControlMode controlMode;
	/**
	 * P Gain
	 *
	 * This is multiplied by closed loop error in sensor units.
	 * Note the closed loop output interprets a final value of 1023 as full output.
	 * So use a gain of '0.25' to get full output if err is 4096u (Mag Encoder 1 rotation)
	 */
	private double kP;
	/**
	 * I Gain
	 *
	 * This is multiplied by accumulated closed loop error in sensor units every PID Loop.
	 * Note the closed loop output interprets a final value of 1023 as full output.
	 * So use a gain of '0.00025' to get full output if err is 4096u for 1000 loops (accumulater holds 4,096,000),
	 * [which is equivalent to one CTRE mag encoder rotation for 1000 milliseconds].
	 */
	private double kI;
	/**
	 * D Gain
	 *
	 * This is multiplied by derivative error (sensor units per PID loop, typically 1ms).
	 * Note the closed loop output interprets a final value of 1023 as full output.
	 * So use a gain of '250' to get full output if derr is 4096u (Mag Encoder 1 rotation) per 1000 loops (typ 1 sec)
	 */
	private double kD;
	/**
	 * F Gain
	 *
	 * See documentation for calculation details.
	 * If using velocity, motion magic, or motion profile,
	 * use (1023 * duty-cycle / sensor-velocity-sensor-units-per-100ms).
	 *
	 */
	private double kF;
	/**
	 * Integral zone (in native units)
	 *
	 * If the (absolute) closed-loop error is outside of this zone, integral
	 * accumulator is automatically cleared. This ensures than integral wind up
	 * events will stop after the sensor gets far enough from its target.
	 *
	 */
	private int integralZone;
	/**
	 * Allowable closed loop error to neutral (in native units)
	 */
	private int allowableClosedloopError;
	/**
	 * Max integral accumulator (in native units)
	 */
	private double maxIntegralAccumulator;
	/**
	 * Peak output from closed loop [0,1]
	 */
	private double closedLoopPeakOutput;
	/**
	 * Desired period of closed loop [1,64]ms
	 */
	private int closedLoopPeriod;

	private SlotConfigBuilder() {
	}

	public static SlotConfigBuilder newBuilder() {
		return builderWithBaseConfiguration(new SlotConfiguration());
	}

	public static SlotConfigBuilder builderWithBaseConfiguration(SlotConfiguration slot){
		return new SlotConfigBuilder()
				.withKP(slot.kP)
				.withKI(slot.kI)
				.withKD(slot.kD)
				.withKF(slot.kF)
				.withIntegralZone(slot.integralZone)
				.withAllowableClosedloopError(slot.allowableClosedloopError)
				.withMaxIntegralAccumulator(slot.maxIntegralAccumulator)
				.withClosedLoopPeakOutput(slot.closedLoopPeakOutput)
				.withClosedLoopPeriod(slot.closedLoopPeriod);
	}

	public SlotConfigBuilder withKP(double kP) {
		this.kP = kP;
		return this;
	}

	public SlotConfigBuilder withKI(double kI) {
		this.kI = kI;
		return this;
	}

	public SlotConfigBuilder withKD(double kD) {
		this.kD = kD;
		return this;
	}

	public SlotConfigBuilder withKF(double kF) {
		this.kF = kF;
		return this;
	}

	public SlotConfigBuilder withIntegralZone(int integralZone) {
		this.integralZone = integralZone;
		return this;
	}

	public SlotConfigBuilder withAllowableClosedloopError(int allowableClosedloopError) {
		this.allowableClosedloopError = allowableClosedloopError;
		return this;
	}

	public SlotConfigBuilder withMaxIntegralAccumulator(double maxIntegralAccumulator) {
		this.maxIntegralAccumulator = maxIntegralAccumulator;
		return this;
	}

	public SlotConfigBuilder withClosedLoopPeakOutput(double closedLoopPeakOutput) {
		this.closedLoopPeakOutput = closedLoopPeakOutput;
		return this;
	}

	public SlotConfigBuilder withClosedLoopPeriod(int closedLoopPeriod) {
		this.closedLoopPeriod = closedLoopPeriod;
		return this;
	}

//	public SlotConfigBuilder withSlot(int slotId){
//		this.slotId = slotId;
//		return this;
//	}
//
//	public SlotConfigBuilder withControlMode(ControlMode controlMode){
//		this.controlMode = controlMode;
//		return this;
//	}

	public SlotConfigBuilder but() {
		return newBuilder()
				.withKP(kP)
				.withKI(kI)
				.withKD(kD)
				.withKF(kF)
				.withIntegralZone(integralZone)
				.withAllowableClosedloopError(allowableClosedloopError)
				.withMaxIntegralAccumulator(maxIntegralAccumulator)
				.withClosedLoopPeakOutput(closedLoopPeakOutput)
				.withClosedLoopPeriod(closedLoopPeriod);
//				.withSlot(slotId)
//				.withControlMode(controlMode);
	}

	public SlotConfiguration applyTo(SlotConfiguration slotConfig){
		slotConfig.allowableClosedloopError = this.allowableClosedloopError;
		slotConfig.closedLoopPeakOutput = this.closedLoopPeakOutput;
		slotConfig.integralZone = this.integralZone;
		slotConfig.closedLoopPeriod = this.closedLoopPeriod;
		slotConfig.kP = this.kP;
		slotConfig.kI = this.kI;
		slotConfig.kD = this.kD;
		slotConfig.kF = this.kF;
		slotConfig.maxIntegralAccumulator = this.maxIntegralAccumulator;
		return slotConfig;
	}

	public SlotConfiguration build() {
		return applyTo(new SlotConfiguration());
	}




}
