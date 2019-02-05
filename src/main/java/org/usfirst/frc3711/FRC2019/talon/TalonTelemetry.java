package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public abstract class TalonTelemetry {

//	double current;
//	double busVoltage;
//	double motorOutputVoltage;
//	double motorOutputPercent;

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

}
