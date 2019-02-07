/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.LayoutType;

//TODO: does this even work?
public class PositionClosedLoop extends Command {

    private final TalonSubsystem subsystem;

    NetworkTableEntry ntSetpoint;
    NetworkTableEntry ntClosedLoopEnabled;

    public PositionClosedLoop(TalonSubsystem subsystem) {
        this.subsystem = subsystem;
        requires(subsystem);

        setName("Position Closed Loop(" + subsystem.getName() + ")");

        var container = subsystem.tab.getLayout("Position Closed Loop", BuiltInLayouts.kList);

        ntSetpoint = container.add("setpoint", subsystem.talon.get()).getEntry();
        ntClosedLoopEnabled = container.add("enable closed loop", false).withWidget(BuiltInWidgets.kToggleButton)
                .getEntry();

        container.add(this); // TODO: create factory to avoid this

    }

    @Override
    protected void execute() {
        if (ntClosedLoopEnabled.getBoolean(false)) {
            subsystem.talon.set(ControlMode.Position, ntSetpoint.getDouble(0.0));
        }
    }

    @Override
    protected void end() {
        subsystem.talon.neutralOutput();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
