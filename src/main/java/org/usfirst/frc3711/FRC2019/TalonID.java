package org.usfirst.frc3711.FRC2019;

public enum TalonID {
  ARM(0),
  ELEVATOR(1),
  INTAKE(2),
  WRIST(3),

  FRONT_HOOK(4),
  REAR_SCREW(5),

  LEFT_FRONT(6),
  LEFT_REAR(7),
  RIGHT_FRONT(8),
  RIGHT_REAR(9);

  private final int id;

  public int getId() {
    return id;
  }

  TalonID(int id) {
    this.id = id;
  }


}
