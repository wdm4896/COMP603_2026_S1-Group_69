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
public class PlayerTest {
    
    Player player;
    
    public PlayerTest() {
    }
    
    @Before
    public void setUp() {
        player = new Player("", Colour.RED);
    }

    /**
     * Test of resetColumns method, of class Player.
     */
    @Test
    public void testResetColumns() {
        System.out.println("resetColumns");
        player.resetColumns();
        assertEquals(5, player.getClaimedColumns().length);
        for (int claimedColumn : player.getClaimedColumns())
        {
            assertEquals(-1, claimedColumn);
        }
        assertEquals(0, player.getClaimedTotal());
        assertEquals(11, player.getPosCurrent().length);
        assertEquals(11, player.getPosMoving().length);
    }
    
    /**
     * Test of isMoving method, of class Player.
     */
    @Test
    public void testIsMoving() {
        System.out.println("isMoving");
        assertEquals(false, player.isMoving());
    }
    
    /**
     * Test of setMoving method, of class Player.
     */
    @Test
    public void testMoving() {
        System.out.println("setMoving + isMoving");
        player.setMoving(true);
        assertEquals(true, player.isMoving());
        player.setMoving(false);
        assertEquals(false, player.isMoving());
    }

    /**
     * Test of blockColumn method, of class Player.
     */
    @Test
    public void testBlockColumn() {
        System.out.println("blockColumn");
        
        // Min boundary
        player.resetColumns();
        int index = 0;
        player.blockColumn(index);
        
        int[] posCurrent = player.getPosCurrent();
        int[] posMoving = player.getPosMoving();
        for (int i = 0; i < posCurrent.length; i++)
        {
            if (i == index)
            {
                assertEquals(-1, posCurrent[i]);
                assertEquals(-1, posMoving[i]);
            }
            else
            {
                assertEquals(0, posCurrent[i]);
                assertEquals(0, posMoving[i]);
            }
        }
        
        // Max boundary
        player.resetColumns();
        index = player.getPosCurrent().length - 1;
        player.blockColumn(index);
        
        posCurrent = player.getPosCurrent();
        posMoving = player.getPosMoving();
        for (int i = 0; i < posCurrent.length; i++)
        {
            if (i == index)
            {
                assertEquals(-1, posCurrent[i]);
                assertEquals(-1, posMoving[i]);
            }
            else
            {
                assertEquals(0, posCurrent[i]);
                assertEquals(0, posMoving[i]);
            }
        }
    }

    /**
     * Test of getMovingPieces method, of class Player.
     */
    @Test
    public void testGetMovingPieces() {
        System.out.println("getMovingPieces");
        assertEquals(3, player.getMovingPieces().length);
    }

    /**
     * Test of getMovingPiecesAvailable method, of class Player.
     */
    @Test
    public void testGetMovingPiecesAvailable() {
        System.out.println("getMovingPiecesAvailable");
        assertEquals(3, player.getMovingPiecesAvailable());
    }

    /**
     * Test of getMovingPiecesMax method, of class Player.
     */
    @Test
    public void testGetMovingPiecesMax() {
        System.out.println("getMovingPiecesMax");
        assertEquals(3, player.getMovingPiecesMax());
    }
    
}
