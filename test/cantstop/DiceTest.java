/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cantstop;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class DiceTest {

    /**
     * Test of roll method, of class Dice.
     */
    @Test
    public void testRoll() {
        System.out.println("roll");
        int diceMin = 1;
        int diceMax = 3;
        Dice dice = new Dice(diceMin, diceMax);
        
        int roll;
        boolean minAppears = false;
        boolean midAppears = false;
        boolean maxAppears = false;
        for (int i = 0; i < 20; i++) // Odds of failing is 0.0003 if everything is correct
        {
            roll = dice.roll();
            assertTrue(1 <= roll && roll <= 6); // Ensures values are guaranteed in range
            minAppears = (roll == diceMin || minAppears);
            maxAppears = (roll == diceMax || maxAppears);
            midAppears = ((roll != diceMin && roll != diceMax) || midAppears);
        }
        assertTrue(minAppears);
        assertTrue(midAppears);
        assertTrue(maxAppears);
    }
    
}
