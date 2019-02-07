package org.usfirst.frc3711.FRC2019.talon;

import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

public class TalonUtil {

	public static SlotConfiguration getSlotConfiguration(TalonSRXConfiguration srx, int slotID){
		switch (slotID){
			case 0: return srx.slot0;
			case 1: return srx.slot1;
			case 2: return srx.slot2;
			case 3: return srx.slot3;
			default: throw new IllegalArgumentException("Invalid Slot:"+slotID);
		}
	}
}
