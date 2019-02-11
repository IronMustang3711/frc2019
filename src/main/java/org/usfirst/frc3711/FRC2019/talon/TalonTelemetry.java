package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableImpl;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

import java.util.EnumSet;
import java.util.Set;

public abstract class TalonTelemetry {

public static void installMototIOTelemetry(TalonSubsystem subsystem){

	subsystem.tab.add(
					subsystem.addChildItem("Motor IO",new MotorIOSendable(subsystem.talon))
	).withSize(2,2);
}

public static void installSensorCollectionTelemetry(TalonSubsystem subsystem){
	subsystem.tab.add(
					subsystem.addChildItem("sensor collection",
									new SensorCollectionSendable(subsystem.talon.getSensorCollection())))
					.withSize(2,2);
}

public static void installClosedLoopTelemetry(TalonSubsystem subsystem){
	subsystem.tab.add(subsystem.addChildItem("Closed Loop Stuff", new ClosedLoopSendable(subsystem.talon)))
	.withSize(2, 2);
}
	public static class MotorIOSendable implements Sendable {
		private final IMotorControllerEnhanced motor;
		private String name;
		private String subsystem = "MotorControllers";

		public MotorIOSendable(IMotorControllerEnhanced motor) {
			this.motor = motor;
			this.name = "MotorController"+motor.getDeviceID()+"Outputs";

		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}



		@Override
		public String getSubsystem() {
			return subsystem;
		}

		@Override
		public void setSubsystem(String subsystem) {
			this.subsystem = subsystem;
		}

		@Override
		public void initSendable(SendableBuilder builder) {

			builder.addDoubleProperty("busVoltage",motor::getBusVoltage,null);
			builder.addDoubleProperty("outputPercent",motor::getMotorOutputPercent,null);
			builder.addDoubleProperty("outputVoltage",motor::getMotorOutputVoltage,null);
			builder.addDoubleProperty("outputCurrent",motor::getOutputCurrent,null);
			builder.addDoubleProperty("selectedSensorPosition",()->(double)motor.getSelectedSensorPosition(0),null);
			builder.addDoubleProperty("selectedSensorVelocity",()->(double)motor.getSelectedSensorVelocity(0),null);
		}
	}




	public static class SensorCollectionSendable implements Sendable{
		private String name;
		private String subsystem;

		private final SensorCollection sensor;

		public SensorCollectionSendable(SensorCollection sensor) {
			this.sensor = sensor;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getSubsystem() {
			return subsystem;
		}

		@Override
		public void setSubsystem(String subsystem) {
			this.subsystem = subsystem;
		}

		@Override
		public void initSendable(SendableBuilder builder) {

			builder.addDoubleProperty("quad position",() -> (double)sensor.getQuadraturePosition(),null);
			builder.addDoubleProperty("quad velocity",()->(double)sensor.getQuadratureVelocity(),null);
			builder.addBooleanProperty("quad a",sensor::getPinStateQuadA,null);
			builder.addBooleanProperty("quad b",sensor::getPinStateQuadB,null);
		}
	}


	public static class ClosedLoopSendable extends SendableImpl {
		private final IMotorController controller;

		public ClosedLoopSendable(IMotorController controller) {
			this.controller = controller;
		}

		@Override
		public void initSendable(SendableBuilder builder) {
			builder.addDoubleProperty("setpoint",this::getSetpoint,null);
			builder.addDoubleProperty("error",this::getError,null);
			builder.addDoubleProperty("delta error",this::getDeltaError,null);
		}
		double getSetpoint(){
			if(CLOSED_LOOP_MODES.contains(controller.getControlMode()))
				return controller.getClosedLoopTarget(0);
			else return -1;
		}
		double getError(){
			if(CLOSED_LOOP_MODES.contains(controller.getControlMode()))
				return controller.getClosedLoopError(0);
			else return -1;
		}
		double getDeltaError(){
			if(CLOSED_LOOP_MODES.contains(controller.getControlMode()))
				return controller.getErrorDerivative(0);
			else return -1;
		}
	}

	private static final Set<ControlMode> CLOSED_LOOP_MODES
	 = EnumSet.complementOf(EnumSet.of(ControlMode.Disabled, ControlMode.Follower, ControlMode.PercentOutput));
	private static final Set<ControlMode> MOTION_PROFILE_MODES 
	= EnumSet.of(ControlMode.MotionMagic,ControlMode.MotionProfile,ControlMode.MotionProfileArc);



	/*
			builder.addDoubleProperty("busVoltage",motor::getBusVoltage,null);
			builder.addDoubleProperty("outputPercent",motor::getMotorOutputPercent,null);
			builder.addDoubleProperty("outputVoltage",motor::getMotorOutputVoltage,null);
			builder.addDoubleProperty("outputCurrent",motor::getOutputCurrent,null);
			builder.addDoubleProperty("selectedSensorPosition",()->(double)motor.getSelectedSensorPosition(0),null);
			builder.addDoubleProperty("selectedSensorVelocity",()->(double)motor.getSelectedSensorVelocity(0),null);



	*/
	static class BasicTelemetry implements Runnable{
		protected final TalonSubsystem subsystem;
		protected final NetworkTable table;

		final NetworkTableEntry outputPercent;
		final NetworkTableEntry outputVoltage;
		final NetworkTableEntry outputCurrent;

		BasicTelemetry(TalonSubsystem subsystem){
			this.subsystem = subsystem;
			table = NetworkTableInstance.getDefault().getTable(subsystem.getName()+"Telemetry");
			
			outputPercent = table.getEntry("outputPercent");
			outputVoltage = table.getEntry("outputVoltage");
			outputCurrent = table.getEntry("outputCurrent");



		}


		@Override
		public void run() {
			outputPercent.setDouble(subsystem.talon.getMotorOutputPercent());
			outputVoltage.setDouble(subsystem.talon.getMotorOutputVoltage());
			outputCurrent.setDouble(subsystem.talon.getOutputCurrent());
		}

	}


	public static class MotionMagicTelemetry extends  BasicTelemetry {

		final NetworkTableEntry position;
		final NetworkTableEntry velocity;

		final NetworkTableEntry target;
		final NetworkTableEntry error;
		final NetworkTableEntry iAccum;
		final NetworkTableEntry trajPosition;
		final NetworkTableEntry trajVelocity;
		final NetworkTableEntry trajHeading;
		final NetworkTableEntry trajFF;

		public MotionMagicTelemetry(TalonSubsystem subsystem) {
			super(subsystem);

			target = table.getEntry("target");
			error = table.getEntry("error");
			iAccum = table.getEntry("iAccum");
			trajPosition = table.getEntry("trajPosition");
			trajVelocity = table.getEntry("trajVelocity");
			trajHeading = table.getEntry("trajHeading");
			trajFF = table.getEntry("trajFF");

			position = table.getEntry("position");
			velocity = table.getEntry("velocity");

		}

		@Override
		public void run() {
			super.run();
			position.setDouble(subsystem.talon.getSelectedSensorPosition());
			velocity.setDouble(subsystem.talon.getSelectedSensorVelocity());


			if(!CLOSED_LOOP_MODES.contains(subsystem.talon.getControlMode())) return;
			target.setDouble(subsystem.talon.getClosedLoopTarget());
			error.setDouble(subsystem.talon.getClosedLoopError());
			iAccum.setDouble(subsystem.talon.getIntegralAccumulator());
			if(!MOTION_PROFILE_MODES.contains(subsystem.talon.getControlMode())) return;
			trajPosition.setDouble(subsystem.talon.getActiveTrajectoryPosition());
			trajVelocity.setDouble(subsystem.talon.getActiveTrajectoryVelocity());
			trajHeading.setDouble(subsystem.talon.getActiveTrajectoryHeading());
			trajFF.setDouble(subsystem.talon.getActiveTrajectoryArbFeedFwd());
		}
	}
}
