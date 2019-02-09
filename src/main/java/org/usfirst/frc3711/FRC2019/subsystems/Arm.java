// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3711.FRC2019.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import org.usfirst.frc3711.FRC2019.TalonID;
import org.usfirst.frc3711.FRC2019.commands.MotionMagicSetpoint;
import org.usfirst.frc3711.FRC2019.commands.SetpointCommand;
import org.usfirst.frc3711.FRC2019.talon.SlotConfigBuilder;
import org.usfirst.frc3711.FRC2019.talon.TalonLiveWindowSupport;
import org.usfirst.frc3711.FRC2019.talon.TalonTelemetry;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 */
public class Arm extends TalonSubsystem {

    @SuppressWarnings("WeakerAccess")
    static class TalonSettings {
        static class PIDSlots {
            public static final SlotConfiguration POSITION_SLOT =
                    SlotConfigBuilder.newBuilder()
                            .withKP(2.0)
                            .build();

            public static final SlotConfiguration MM_SLOT =
                    SlotConfigBuilder.builderWithBaseConfiguration(POSITION_SLOT)
                            .withKP(2.0)
                            .withKF(2.0)
                            .build();

            public static SlotConfiguration configurationForSlot(int slot){
                switch (slot){
                    case 0: return MM_SLOT;
                    case 1: return POSITION_SLOT;
                    default: throw new IllegalArgumentException("invalid slot: "+slot);
                }
            }
        }


        public static final TalonSRXConfiguration CONFIGURATION = new TalonSRXConfiguration();

        @SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
        static TalonSRXConfiguration applyConfig(TalonSRXConfiguration config){
            config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;

            config.remoteFilter0.remoteSensorSource = RemoteSensorSource.Off;
            config.remoteFilter1.remoteSensorSource = RemoteSensorSource.Off;

            config.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            config.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

            config.forwardLimitSwitchNormal = LimitSwitchNormal.Disabled;
            config.reverseLimitSwitchNormal = LimitSwitchNormal.Disabled;

            config.forwardSoftLimitThreshold = 3000;
            config.reverseSoftLimitThreshold = -3000;
            config.forwardSoftLimitEnable = true;
            config.reverseSoftLimitEnable = true;


            // config.openloopRamp = 1.023000; //TODO: configure this / or dont?
            // config.closedloopRamp = 1.705000;

            config.motionCruiseVelocity = 75;
            config.motionAcceleration = 75;

            config.peakOutputForward = 0.8;
            config.peakOutputReverse = -0.3;



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


            // config.neutralDeadband = 0.199413; //TODO: configure



      /*
Talon SRX and Victor SPX can be configured to adjust their outputs in response to the battery
voltage measurement (in all control modes). Use the voltage compensation saturation config to determine
what voltage represents 100% output.
 */
            config.voltageCompSaturation = 9.0;


              /*
        After setting the three configurations, current limiting must be enabled via enableCurrentLimit() or LabVIEW VI.
         */
            config.peakCurrentLimit = 8;
            config.peakCurrentDuration = 3000;
            config.continuousCurrentLimit = 2;

            config.slot0 = PIDSlots.configurationForSlot(0);
            config.slot1 = PIDSlots.configurationForSlot(1);

            return config;
        }



        /*
	 things not handled with config:
	[x]Current Limit Enable (though the thresholds are configs)
	[x]Voltage Compensation Enable (though the nominal voltage is a config)
	Control Mode and Target/Output demand (percent, position, velocity, etc.)
	[x]Invert direction and sensor phase
	[x]Closed-loop slot selection [0,3] for primary and aux PID loops.
	Neutral mode override (convenient to temporarily override configs)
	Limit switch override (convenient to temporarily override configs)
	Soft Limit override (convenient to temporarily override configs)
	Status Frame Periods
	  */
        public static void configure(TalonSRX talon){
            talon.configFactoryDefault();

            talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
            talon.selectProfileSlot(0,0); //motion magic slot & primary pid


            talon.setInverted(true);
            talon.setSensorPhase(false);



            talon.getAllConfigs(CONFIGURATION);
            applyConfig(CONFIGURATION);
            talon.configAllSettings(CONFIGURATION);

            talon.enableVoltageCompensation(true);
            talon.enableCurrentLimit(true);

        }
    }


    public Arm() {
      super(Arm.class.getSimpleName(), TalonID.ARM.getId());
      TalonTelemetry.installClosedLoopTelemetry(this);




      tab.add(new Command("closed loop control"){

       {requires(Arm.this);}


        @Override
        protected void execute() {
           if(ntClosedLoopEnabled.getBoolean(false)){
               talon.set(ControlMode.MotionMagic, ntSetpoint.getDouble(talon.getSelectedSensorPosition()));
           }
        }

        @Override
            protected void end() {
                disable();
            }



          @Override
          protected boolean isFinished() {
              return false;
          }
      });
 
       tab.add(new InstantCommand("Reset Encoder"){
           {requires(Arm.this);}
 
         @Override
         protected void execute() {
            talon.setSelectedSensorPosition(0);
            talon.getSensorCollection().setQuadraturePosition(0, 50);
 
         }
 
       });

//       MotionMagicSetpoint top = new MotionMagicSetpoint("top",this,2000);
//       top.setSetpoint(2000);
//       addChild("top", top);
//       tab.add(top);
//
//       MotionMagicSetpoint home = new MotionMagicSetpoint("home",this,1000);
//       home.setSetpoint(1000);
//       addChild("home",home);
//       tab.add(home);

    }

    @Override // Put code here to be run every loop
    public void initDefaultCommand() {
       // setDefaultCommand(new SetpointCommand("stow", this, 0 , ControlMode.MotionMagic));
    }

//    public void configMotionMagicClosedLoop(){
//        talon.config_kP(0, 4, 50);
//        talon.config_kI(0,0.0,50);
//        talon.config_IntegralZone(0,200,50);
//        talon.config_kD(0,0,50);
//        talon.config_kF(0,4.0,50);
//
//
//        talon.configMotionCruiseVelocity(200);
//        talon.configMotionAcceleration(100);
//      }

    @Override
    void configureTalon() {
        super.configureTalon();
        TalonSettings.configure(talon);
    }

    @Override
    public void periodic() {
        super.periodic();
//            SmartDashboard.putNumber("Arm position", talon.getSensorCollection().getQuadraturePosition());
//            SmartDashboard.putNumber("Arm position2", talon.getSelectedSensorPosition());
    }

}

