package org.usfirst.frc3711.deepspace;

public enum DebugMode {
  NONE,
  BASIC,
  FULL;

  public boolean isEnabled(DebugMode mode) {
    switch(this){
      case NONE:
        return mode == NONE;
      case BASIC:
        return mode == BASIC || mode == FULL;
      case FULL:
        return mode == FULL;
        default:
          return false;
    }
  }
}
