package org.usfirst.frc3711.FRC2019.subsystems;


import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import org.usfirst.frc3711.FRC2019.TalonID;


public class Elevator extends LinkageSubsystem {
    public Elevator() {
     super(Elevator.class.getSimpleName(), TalonID.ELEVATOR.getId());
    }

    @Override
    void configureTalon() {
        super.configureTalon();

        TalonSRXConfiguration config = new TalonSRXConfiguration();


        config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
        config.primaryPID.selectedFeedbackCoefficient = 0.328293; //TODO:what is this
       // config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.Analog;
        //config.auxiliaryPID.selectedFeedbackCoefficient = 0.877686;
        config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
        config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;
        //config.forwardLimitSwitchDeviceID = 6;
       // config.reverseLimitSwitchDeviceID = 5;
        config.forwardLimitSwitchNormal = LimitSwitchNormal.Disabled;
        config.reverseLimitSwitchNormal = LimitSwitchNormal.Disabled;
        //config.sum0Term = FeedbackDevice.QuadEncoder;
        //config.sum1Term = FeedbackDevice.RemoteSensor0;
        //config.diff0Term = FeedbackDevice.RemoteSensor1;
        //config.diff1Term = FeedbackDevice.PulseWidthEncodedPosition;
        //TODO: do we want this
       // config.peakCurrentLimit = 20;
        //config.peakCurrentDuration = 200;
       // config.continuousCurrentLimit = 30;
        config.openloopRamp = 1.023000; //TODO: configure this

       // config.closedloopRamp = 1.705000;

        config.peakOutputForward = 1.0;
        config.peakOutputReverse = -1.0;

        config.nominalOutputForward = 0;
        config.nominalOutputReverse = 0;


        config.neutralDeadband = 0.199413; //TODO: configure

        config.voltageCompSaturation = 9.296875;
        config.voltageMeasurementFilter = 16;
        config.velocityMeasurementPeriod = VelocityMeasPeriod.Period_25Ms;
        config.velocityMeasurementWindow = 8;


        //TODO: configure this
        config.forwardSoftLimitThreshold = 2767;
        config.reverseSoftLimitThreshold = -1219;
        config.forwardSoftLimitEnable = false; //todo: enable when thresholds are correct
        config.reverseSoftLimitEnable = false;


        config.slot0.kP = 504.000000;
        config.slot0.kI = 5.600000;
        config.slot0.kD = 0.200000;
        config.slot0.kF = 19.300000;
        config.slot0.integralZone = 900;
        config.slot0.allowableClosedloopError = 217;
        config.slot0.maxIntegralAccumulator = 254.000000;
        config.slot0.closedLoopPeakOutput = 0.869990;
        config.slot0.closedLoopPeriod = 33;


        config.slot1.kP = 155.600000;
        config.slot1.kI = 5.560000;
        config.slot1.kD = 8.868600;
        config.slot1.kF = 454.000000;
        config.slot1.integralZone = 100;
        config.slot1.allowableClosedloopError = 200;
        config.slot1.maxIntegralAccumulator = 91.000000;
        config.slot1.closedLoopPeakOutput = 0.199413;
        config.slot1.closedLoopPeriod = 34;

        config.slot2.kP = 223.232000;
        config.slot2.kI = 34.000000;
        config.slot2.kD = 67.000000;
        config.slot2.kF = 6.323232;
        config.slot2.integralZone = 44;
        config.slot2.allowableClosedloopError = 343;
        config.slot2.maxIntegralAccumulator = 334.000000;
        config.slot2.closedLoopPeakOutput = 0.399804;
        config.slot2.closedLoopPeriod = 14;

        config.slot3.kP = 34.000000;
        config.slot3.kI = 32.000000;
        config.slot3.kD = 436.000000;
        config.slot3.kF = 0.343430;
        config.slot3.integralZone = 2323;
        config.slot3.allowableClosedloopError = 543;
        config.slot3.maxIntegralAccumulator = 687.000000;
        config.slot3.closedLoopPeakOutput = 0.129032;
        config.slot3.closedLoopPeriod = 12;

        config.auxPIDPolarity = true;
        config.remoteFilter0.remoteSensorDeviceID = 22;
        config.remoteFilter0.remoteSensorSource = RemoteSensorSource.GadgeteerPigeon_Roll;
        config.remoteFilter1.remoteSensorDeviceID = 41;
        config.remoteFilter1.remoteSensorSource = RemoteSensorSource.GadgeteerPigeon_Yaw;


        config.motionCruiseVelocity = 37;
        config.motionAcceleration = 3;
        config.motionProfileTrajectoryPeriod = 11;

        config.feedbackNotContinuous = true;
        config.remoteSensorClosedLoopDisableNeutralOnLOS = false;
        config.clearPositionOnLimitF = true;
        config.clearPositionOnLimitR = true;
        config.clearPositionOnQuadIdx = false;
        config.limitSwitchDisableNeutralOnLOS = true;
        config.softLimitDisableNeutralOnLOS = false;
        config.pulseWidthPeriod_EdgesPerRot = 9;
        config.pulseWidthPeriod_FilterWindowSz = 32;
        config.customParam0 = 3;
        config.customParam1 = 5;

        talon.configAllSettings(config);

    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
     super.periodic();
    }

}

