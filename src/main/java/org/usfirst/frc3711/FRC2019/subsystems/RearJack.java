package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import org.usfirst.frc3711.FRC2019.TalonID;
import org.usfirst.frc3711.FRC2019.talon.TalonTelemetry;

import java.util.Map;

public class RearJack extends TalonSubsystem {

    private final NetworkTableEntry motorOutput;
    private final TalonTelemetry.MotionMagicTelemetry telemetry;

    public RearJack() {
        super(RearJack.class.getSimpleName(), TalonID.REAR_JACK.getId());
        telemetry = new TalonTelemetry.MotionMagicTelemetry(this);

        motorOutput = tab.add("Output", 0.0)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", -1.0, "max", 1.0)).getEntry();

        tab.add(new Command("Run Motor") {

            @Override
            protected void execute() {
               talon.set(ControlMode.PercentOutput,motorOutput.getDouble(0.0));
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
    }

    @Override
    public void periodic() {
        super.periodic();
        telemetry.run();
    }


}
