package org.usfirst.frc3711.deepspace;

public enum TalonID {
  ARM(12),
  EJECTOR(1),
  ELEVATOR(2),
  INTAKE(15),
  WRIST(4),

  DOG_LEG(5),
  REAR_JACK(27),

  LEFT_FRONT(3),
  LEFT_REAR(10),
  RIGHT_FRONT(13),
  RIGHT_REAR(11);

  private final int id;

  public int getId() {
    return id;
  }

  TalonID(int id) {
    this.id = id;
  }


}
