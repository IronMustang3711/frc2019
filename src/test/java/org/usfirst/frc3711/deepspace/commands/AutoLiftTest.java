/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc3711.deepspace.commands;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.PIDBase.Tolerance;

import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
/**
 * Add your docs here.
 */
public class AutoLiftTest {
    static final double Tolerance = 0.00001;
  
    static void testGearing(double rearJackPosition, double expectedDogLegPosition){
        double actualDogLegPosition = AutoLift.getDoglegPosition(rearJackPosition);

        Assert.assertEquals(expectedDogLegPosition, actualDogLegPosition, Tolerance);

    }
    @Test
    public void testGetDogLegPositionAtZero(){
        testGearing(0, 0);
    }

    public void testNegativeRearJack(){
        testGearing(-10, 0);
    }

    public void testAboveMaxRearJack(){
        testGearing(-20, -25);
    }

    @Test
    public void testGetDogLegPositionAtEnd(){
        testGearing(-10.0, -25.0);
    }
}
