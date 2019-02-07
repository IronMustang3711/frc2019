/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

/**
 * Add your docs here.
 */
public class SetpointCommand extends AbstractCommand{
    final TalonSubsystem subsystem;
    final double setpoint;
    final ControlMode mode;

    public SetpointCommand(String name, TalonSubsystem subsystem,double setpoint,ControlMode mode){
        super(name,subsystem);
        this.subsystem = subsystem;
        this.setpoint = setpoint;
        this.mode = mode;
    }

    @Override
    protected void initialize() {
       if(ControlMode.MotionMagic.equals(mode)){
           subsystem.configMotionMagicClosedLoop();
       }
    }
    @Override
    protected void execute() {
       subsystem.talon.set(mode, setpoint);
    }

    @Override
    protected boolean isFinished() {
        return super.isFinished() ||  
            (Math.abs(subsystem.talon.getClosedLoopError()) < 100.0 
            && Math.abs(subsystem.talon.getErrorDerivative()) < 4.0);
    }
}
