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
public class MathTest {

    /**
     * Test of factorial method, of class Math.
     */
    @Test
    public void testFactorial() {
        System.out.println("factorial");
        assertEquals(720, Math.factorial(6));
        assertEquals(120, Math.factorial(5));
        assertEquals(24, Math.factorial(4));
        assertEquals(6, Math.factorial(3));
        assertEquals(2, Math.factorial(2));
        assertEquals(1, Math.factorial(1));
        assertEquals(1, Math.factorial(0));
        try
        {
            Math.factorial(-6);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e)
        {
            // Tests pass
        }
        // Will never have to run with negative number
        // Range will generally only be 2 and 4 in normal usage
        // I wanted to add more tests just in case we change the values a bit :)
    }

    /**
     * Test of permutation method, of class Math.
     */
    @Test
    public void testPermutation() {
        System.out.println("permutation");
        assertEquals(720, Math.permutation(6, 6));
        assertEquals(12, Math.permutation(4, 2)); // The result we'd normally use in standard usage
        assertEquals(6, Math.permutation(6, 1));
        assertEquals(1, Math.permutation(1, 1));
        assertEquals(1, Math.permutation(0, 0));
        try
        {
            Math.permutation(1, 6);
            Math.permutation(6, -6);
            Math.permutation(-1, -6);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e)
        {
            // Tests pass
        }
    }

    /**
     * Test of combination method, of class Math.
     */
    @Test
    public void testCombination() {
        System.out.println("combination");
        assertEquals(1, Math.combination(6, 6));
        assertEquals(6, Math.combination(4, 2)); // The result we'd normally use in standard usage
        assertEquals(6, Math.combination(6, 1));
        assertEquals(1, Math.combination(1, 1));
        assertEquals(1, Math.combination(0, 0));
        try
        {
            Math.combination(1, 6);
            Math.combination(6, -6);
            Math.combination(-1, -6);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e)
        {
            // Tests pass
        }
    }
    
}
