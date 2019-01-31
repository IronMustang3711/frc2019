package org.usfirst.frc3711.FRC2019.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc3711.FRC2019.TalonID;


public class Elevator extends LinkageSubsystem {
   private static final int TALON_ID = 1;



    public Elevator() {
     super(Elevator.class.getSimpleName(), TalonID.ELEVATOR.getId());
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
     super.periodic();
    }

}

