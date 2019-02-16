package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class JoystickPOVButton extends Trigger {
  private final Joystick stick;
  private final int direction;

  public JoystickPOVButton(Joystick stick, int direction) {
    this.stick = stick;
    this.direction = direction;
  }

  /**
   * Returns whether or not the trigger is active.
   *
   * <p>This method will be called repeatedly a command is linked to the Trigger.
   *
   * @return whether or not the trigger condition is active.
   */
  @Override
  public boolean get() {
    return stick.getPOV() == direction;
  }
}
