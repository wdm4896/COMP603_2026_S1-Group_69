/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cantstop;

import java.util.LinkedList;
import java.util.Queue;
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
public class GameBoardTest {
    
    GameBoard board;
    
    public GameBoardTest() {
    }
    
    @Before
    public void setUp() {
        board = new GameBoard(new LinkedList<Player>());
    }

    /**
     * Test of getColumnValues method, of class GameBoard.
     */
    @Test
    public void testGetColumnValues() {
        System.out.println("getColumnValues");
        int[] expResult = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] result = board.getColumnValues();
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of getColumnSizes method, of class GameBoard.
     */
    @Test
    public void testGetColumnSizes() {
        System.out.println("getColumnSizes");
        int[] expResult = {3, 5, 7, 9, 11, 13, 11, 9, 7, 5, 3};
        int[] result = board.getColumnSizes();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getColumnMin method, of class GameBoard.
     */
    @Test
    public void testGetColumnMin() {
        System.out.println("getColumnMin");
        assertEquals(2, board.getColumnMin());
    }

    /**
     * Test of getBoardWidth method, of class GameBoard.
     */
    @Test
    public void testGetBoardWidth() {
        System.out.println("getBoardWidth");
        assertEquals(11, GameBoard.getBoardWidth());
    }
    
    /**
     * Test of getLengthMax method, of class GameBoard.
     */
    @Test
    public void testGetLengthMax() {
        System.out.println("getLengthMax");
        assertEquals(13, GameBoard.getLengthMax());
    }
    
    /**
     * Test of getColumnClaimed method, of class GameBoard.
     */
    @Test
    public void testGetColumnClaimed() {
        System.out.println("getColumnClaimed");
        for (int i = 0; i < 11; i++)
        {
            assertFalse(board.getColumnClaimed(i));
        }
    }

    /**
     * Test of setColumnClaimed method, of class GameBoard.
     */
    @Test
    public void testColumnClaimed() {
        System.out.println("setColumnClaimed");
        
        int index_1 = 0;
        int index_2 = 10;
        // Set boundaries to true
        board.setColumnClaimed(index_1, true);
        board.setColumnClaimed(index_2, true);
        for (int i = 0; i < 11; i++)
        {
            if (i == index_1 || i == index_2) {
                assertTrue(board.getColumnClaimed(i));
            }
            else
            {
                assertFalse(board.getColumnClaimed(i));
            }
        }
        // Set boundaries to false
        board.setColumnClaimed(index_1, false);
        board.setColumnClaimed(index_2, false);
        for (int i = 0; i < 11; i++)
        {
            assertFalse(board.getColumnClaimed(i));
        }
    }
}
