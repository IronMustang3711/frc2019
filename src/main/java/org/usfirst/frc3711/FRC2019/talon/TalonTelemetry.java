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
			if(closedLoopModes.contains(controller.getControlMode()))
				return controller.getClosedLoopTarget(0);
			else return -1;
		}
		double getError(){
			if(closedLoopModes.contains(controller.getControlMode()))
				return controller.getClosedLoopError(0);
			else return -1;
		}
		double getDeltaError(){
			if(closedLoopModes.contains(controller.getControlMode()))
				return controller.getErrorDerivative(0);
			else return -1;
		}
	}

	private static final Set<ControlMode> closedLoopModes =
					EnumSet.complementOf(EnumSet.of(ControlMode.Disabled, ControlMode.Follower, ControlMode.PercentOutput));


	static class BasicTelemetry implements Runnable{
		protected final TalonSubsystem subsystem;
		protected final NetworkTable table;

		final NetworkTableEntry output;
		final NetworkTableEntry position;
		final NetworkTableEntry velocity;



		BasicTelemetry(TalonSubsystem subsystem){
			this.subsystem = subsystem;
			table = NetworkTableInstance.getDefault().getTable(subsystem.getName()+"Telemetry");
			output = table.getEntry("output");
			position = table.getEntry("position");
			velocity = table.getEntry("velocity");

		}


		@Override
		public void run() {
			output.setDouble(subsystem.talon.getMotorOutputPercent());
			position.setDouble(subsystem.talon.getSelectedSensorPosition());
			velocity.setDouble(subsystem.talon.getSelectedSensorVelocity());
		}

	}

	/*
	SmartDashboard.putNumber("ClosedLoopTarget", tal.getClosedLoopTarget(Constants.kPIDLoopIdx));
    		SmartDashboard.putNumber("ActTrajVelocity", tal.getActiveTrajectoryVelocity());
    		SmartDashboard.putNumber("ActTrajPosition", tal.getActiveTrajectoryPosition());
    		SmartDashboard.putNumber("ActTrajHeading", tal.getActiveTrajectoryHeading());
	 */
	public static class MotionMagicTelemetry extends  BasicTelemetry {

		final NetworkTableEntry target;
		final NetworkTableEntry error;
		final NetworkTableEntry iAccum;
		final NetworkTableEntry trajPosition;
		final NetworkTableEntry trajVelocity;
		final NetworkTableEntry trajFF;

		public MotionMagicTelemetry(TalonSubsystem subsystem) {
			super(subsystem);

			target = table.getEntry("target");
			error = table.getEntry("error");
			iAccum = table.getEntry("iAccum");
			trajPosition = table.getEntry("trajPosition");
			trajVelocity = table.getEntry("trajVelocity");
			trajFF = table.getEntry("trajFF");

		}

		@Override
		public void run() {
			super.run();
			target.setDouble(subsystem.talon.getClosedLoopTarget());
			error.setDouble(subsystem.talon.getClosedLoopError());
			iAccum.setDouble(subsystem.talon.getIntegralAccumulator());
			trajPosition.setDouble(subsystem.talon.getActiveTrajectoryPosition());
			trajVelocity.setDouble(subsystem.talon.getActiveTrajectoryVelocity());
			trajFF.setDouble(subsystem.talon.getActiveTrajectoryArbFeedFwd());
		}
	}
}
