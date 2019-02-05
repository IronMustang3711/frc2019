package org.usfirst.frc3711.FRC2019.subsystems;


import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import org.usfirst.frc3711.FRC2019.TalonID;
import org.usfirst.frc3711.FRC2019.talon.TalonLiveWindowSupport;
import org.usfirst.frc3711.FRC2019.talon.TalonTelemetry;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends TalonSubsystem {

    ShuffleboardTab tab;
    
    NetworkTableEntry ntSetpoint;
    NetworkTableEntry ntClosedLoopEnabled;

    public Elevator() {
     super(Elevator.class.getSimpleName(), TalonID.ELEVATOR.getId());

     tab = Shuffleboard.getTab(Elevator.class.getSimpleName());
        Sendable s = new TalonTelemetry.MotorIOSendable(talon);
        addChild("Elevator:motor io", s);
        Sendable s2 = new TalonTelemetry.SensorCollectionSendable(talon.getSensorCollection());
        addChild("Elevator:sensor collection",s2);
      tab.add(s);
      tab.add(s2);

     ntSetpoint = tab.add("setpoint", 0.0).getEntry();
     ntClosedLoopEnabled = tab.add("setpoint enabled",false).getEntry();


      tab.add(new Command("closed loop control"){



        @Override
        protected void execute() {
           if(ntClosedLoopEnabled.getBoolean(false)){
               talon.set(ControlMode.Position, ntSetpoint.getDouble(0.0));
           }
        }

        
      
          @Override
          protected boolean isFinished() {
              return false;
          }
      });

      tab.add(new InstantCommand("Reset Encoder"){

        @Override
        protected void execute() {
           talon.setSelectedSensorPosition(0);
           talon.getSensorCollection().setQuadraturePosition(0, 50);

        }

      });

    //   Sendable s3 = new TalonLiveWindowSupport(talon);

    //   addChild("closed loop stuff",s3);
    //   tab.add(s3);

    }

    @Override
    void configureTalon() {
        super.configureTalon();
        talon.setInverted(true);
        talon.setSensorPhase(false);
        TalonSRXConfiguration config = new TalonSRXConfiguration();


        /*
        TODO: things not handled with config:
Current Limit Enable (though the thresholds are configs)
Voltage Compensation Enable (though the nominal voltage is a config)
Control Mode and Target/Output demand (percent, position, velocity, etc.)
Invert direction and sensor phase
Closed-loop slot selection [0,3] for primary and aux PID loops.
Neutral mode override (convenient to temporarily override configs)
Limit switch override (convenient to temporarily override configs)
Soft Limit override (convenient to temporarily override configs)
Status Frame Periods
         */

/* ***************** Encoder/Sensor Setup *****************************/
//////////////////////////////////////////////////////////////////////
        {
            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
           // config.primaryPID.selectedFeedbackCoefficient = 0.328293; //TODO:what is this

            // config.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.Analog;
            //config.auxiliaryPID.selectedFeedbackCoefficient = 0.877686;

            //config.sum0Term = FeedbackDevice.QuadEncoder;
            //config.sum1Term = FeedbackDevice.RemoteSensor0;
            //config.diff0Term = FeedbackDevice.RemoteSensor1;
            //config.diff1Term = FeedbackDevice.PulseWidthEncodedPosition;

            //config.auxPIDPolarity = true;
            //config.remoteFilter0.remoteSensorDeviceID = 22;
            config.remoteFilter0.remoteSensorSource = RemoteSensorSource.Off;
            // config.remoteFilter1.remoteSensorDeviceID = 41;
            config.remoteFilter1.remoteSensorSource = RemoteSensorSource.Off;


//        config.feedbackNotContinuous = false;
//        config.remoteSensorClosedLoopDisableNeutralOnLOS = false;
//        config.clearPositionOnLimitF = true;
//        config.clearPositionOnLimitR = true;
//        config.clearPositionOnQuadIdx = false;
//
//        config.pulseWidthPeriod_EdgesPerRot = 9;
//        config.pulseWidthPeriod_FilterWindowSz = 32;

        }
/* ***************** limit switch *****************************/
////////////////////////////////////////////////////////////////
        {
            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated; //Todo: FeedbackConnector?
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;
            //config.forwardLimitSwitchDeviceID = 6;
            // config.reverseLimitSwitchDeviceID = 5;
            config.forwardLimitSwitchNormal = LimitSwitchNormal.Disabled;
            config.reverseLimitSwitchNormal = LimitSwitchNormal.Disabled;
            //        config.limitSwitchDisableNeutralOnLOS = true;
            //        config.softLimitDisableNeutralOnLOS = true;

                /*
        Soft limits can be used to disable motor drive when the “Sensor Position” is outside of a specified range.
        Forward throttle will be disabled if the “Sensor Position” is greater than the Forward Soft Limit.
         Reverse throttle will be disabled if the “Sensor Position” is less than the Reverse Soft Limit.
         The respective Soft Limit Enable must be enabled for this feature to take effect.
         */

            config.forwardSoftLimitThreshold = 9223;
            config.reverseSoftLimitThreshold = -6707;
            config.forwardSoftLimitEnable = true; //todo: enable when thresholds are correct
            config.reverseSoftLimitEnable = true;

        }

        /* ***************** Ramping *****************************/
        ////////////////////////////////////////////////////////////////
        {
        /*
        The Talon SRX can be set to honor a ramp rate to prevent instantaneous changes in throttle.
        This ramp rate is in effect regardless of which mode is selected (throttle, slave, or closed-loop).
        Ramp can be set in time from neutral to full using configOpenLoopRampRate().
         */

           // config.openloopRamp = 1.023000; //TODO: configure this
            // config.closedloopRamp = 1.705000;
        }

        /* ***************** Motion_Magic *****************************/
        ////////////////////////////////////////////////////////////////
        {
            config.motionCruiseVelocity = 37;
            config.motionAcceleration = 3;
            config.motionProfileTrajectoryPeriod = 11;
        }

        /* ***************** Peak/Nominal *****************************/
        ////////////////////////////////////////////////////////////////
        {
        /*
        Peak/Nominal Outputs Often a mechanism may not require full motor output.
        The application can cap the output via the peak forward and reverse config setting (through Tuner or API).
         */
            config.peakOutputForward = 0.6;
            config.peakOutputReverse = -0.2;

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
        }


       // config.neutralDeadband = 0.199413; //TODO: configure

        /* ***************** Voltage Compensation *****************************/
        ////////////////////////////////////////////////////////////////
        {
/*
Talon SRX and Victor SPX can be configured to adjust their outputs in response to the battery
voltage measurement (in all control modes). Use the voltage compensation saturation config to determine
what voltage represents 100% output.
TODO Then enable the voltage compensation using enableVoltageCompensation().
 */
            config.voltageCompSaturation = 9.0;
//        config.voltageMeasurementFilter = 16;
//        config.velocityMeasurementPeriod = VelocityMeasPeriod.Period_25Ms;
//        config.velocityMeasurementWindow = 8;

        }


        /* ***************** Current Limit *****************************/
        ////////////////////////////////////////////////////////////////
       {
            //TODO: do we want this?

        /*
        After setting the three configurations, current limiting must be enabled via enableCurrentLimit() or LabVIEW VI.
         */
            // config.peakCurrentLimit = 20;
            //config.peakCurrentDuration = 200;
            // config.continuousCurrentLimit = 30;
        }



        /* ***************** PID SLOTS *****************************/
        ////////////////////////////////////////////////////////////////

        {
            config.slot0.kP = 0.0;
            config.slot0.kI =0.0;
            config.slot0.kD = 0.0;
            config.slot0.kF =0.0;
            config.slot0.integralZone = 1000;
            config.slot0.allowableClosedloopError = 10;
            config.slot0.maxIntegralAccumulator = 254.000000;
            config.slot0.closedLoopPeakOutput =1.0;
            config.slot0.closedLoopPeriod = 1;


            // config.slot1.kP = 155.600000;
            // config.slot1.kI = 5.560000;
            // config.slot1.kD = 8.868600;
            // config.slot1.kF = 454.000000;
            // config.slot1.integralZone = 100;
            // config.slot1.allowableClosedloopError = 200;
            // config.slot1.maxIntegralAccumulator = 91.000000;
            // config.slot1.closedLoopPeakOutput = 0.199413;
            // config.slot1.closedLoopPeriod = 34;

            // config.slot2.kP = 223.232000;
            // config.slot2.kI = 34.000000;
            // config.slot2.kD = 67.000000;
            // config.slot2.kF = 6.323232;
            // config.slot2.integralZone = 44;
            // config.slot2.allowableClosedloopError = 343;
            // config.slot2.maxIntegralAccumulator = 334.000000;
            // config.slot2.closedLoopPeakOutput = 0.399804;
            // config.slot2.closedLoopPeriod = 14;

            // config.slot3.kP = 34.000000;
            // config.slot3.kI = 32.000000;
            // config.slot3.kD = 436.000000;
            // config.slot3.kF = 0.343430;
            // config.slot3.integralZone = 2323;
            // config.slot3.allowableClosedloopError = 543;
            // config.slot3.maxIntegralAccumulator = 687.000000;
            // config.slot3.closedLoopPeakOutput = 0.129032;
            // config.slot3.closedLoopPeriod = 12;
        }












//        config.customParam0 = 3;
//        config.customParam1 = 5;

        talon.configAllSettings(config);

    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Elevator position", talon.getSensorCollection().getQuadraturePosition());
        SmartDashboard.putNumber("Elevator Position 2", talon.getSelectedSensorPosition());
     super.periodic();
    }

}

