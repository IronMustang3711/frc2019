package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableImpl;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

public abstract class TalonTelemetry {

public static void installMototIOTelemetry(TalonSubsystem subsystem){

	subsystem.tab.add(
					subsystem.addChildItem("Motor IO",new MotorIOSendable(subsystem.talon))
	).withSize(2,2);
}

public static void installSensorCollectionTelemetry(TalonSubsystem subsystem){
	subsystem.tab.add(
					subsystem.addChildItem(
									new SensorCollectionSendable(subsystem.talon.getSensorCollection())))
					.withSize(2,2);
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
			builder.addDoubleProperty("setpoint",()->controller.getClosedLoopTarget(0),null);
			builder.addDoubleProperty("error",()->controller.getClosedLoopError(0),null);
			builder.addDoubleProperty("delta error",()->controller.getErrorDerivative(0),null);
		}
	}

}
