package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import edu.wpi.first.wpilibj.SendableImpl;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TalonLiveWindowSupport extends SendableImpl {
	private static final Logger LOG = Logger.getLogger(TalonLiveWindowSupport.class.getName());

	private final IMotorController controller;

	public TalonLiveWindowSupport(IMotorController controller) {
		this.controller = controller;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		super.initSendable(builder);
		builder.setSmartDashboardType("PIDController");
		builder.setSafeState(this::safeState);
		builder.addDoubleProperty("p",this::getP,this::setP);
		builder.addDoubleProperty("i", this::getI, this::setI);
		builder.addDoubleProperty("d", this::getD, this::setD);
		builder.addDoubleProperty("f", this::getF, this::setF);
		builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
		builder.addBooleanProperty("enabled", this::isEnabled, this::setEnabled);
	}

	private void setEnabled(boolean b) {
		if(!b) safeState();
		else setSetpoint(getSetpoint());
	}

	private boolean isEnabled() {
		return controller.getControlMode() != ControlMode.Disabled;
	}

	void safeState(){
		controller.neutralOutput();
	}

	double getP(){
		return controller.configGetParameter(ParamEnum.eProfileParamSlot_P,0,10);
	}

	 void setP(double p){
		ErrorCode errorCode = controller.config_kP(0, p, 10);
		if(errorCode != ErrorCode.OK){
			LOG.log(Level.SEVERE,"talon timeout");
		}
	}

	double getI(){
		return controller.configGetParameter(ParamEnum.eProfileParamSlot_I,0,10);
	}
	void setI(double i){
		controller.config_kI(0,i,10);
	}
	double getD(){
		return controller.configGetParameter(ParamEnum.eProfileParamSlot_D,0,10);
	}
	void setD(double d){
		controller.config_kD(0,d,10);
	}

	double getF(){
		return controller.configGetParameter(ParamEnum.eProfileParamSlot_F,0,10);
	}
	void setF(double f){
		controller.config_kF(0,f,10);
	}


	double getSetpoint(){
		return controller.getClosedLoopTarget(0);
	}

	void setSetpoint(double setpoint){
		controller.set(ControlMode.Position,setpoint);
	}


}
