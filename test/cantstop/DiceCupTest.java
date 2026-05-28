/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cantstop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
public class DiceCupTest {
    
    private final static int MOVING_PIECES_MAX = 3;
    private final DiceCup diceCup = new DiceCup();
    private GameBoard board;
    private int[] movingPos;
    private int[] movingPieces;
    private int[] diceRoll;
    private int movingPiecesAvailable;
    private Player player;
    
    public DiceCupTest() {
    }
    
    public void dicePairingChoiceListCheck(List<List<Integer[]>> result, Integer[][] pairings) {
        Iterator resultOuter = result.iterator();
        Iterator resultInner;
        Integer[] resultPairing;
        int pairingCheck = 0;
        while (resultOuter.hasNext())
        {
            resultInner = ((List<Integer[]>) resultOuter.next()).iterator();
            while (resultInner.hasNext())
            {
                resultPairing = (Integer[]) resultInner.next();
                for (int i = 0; i < resultPairing.length; i++)
                {
                    assertEquals(pairings[pairingCheck][i], resultPairing[i]);
                }
                pairingCheck++;
            }
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        board = new GameBoard(new LinkedList<Player>());
        player = new Player("", Colour.RED);
        movingPos = new int[GameBoard.getBoardWidth()];
        movingPieces = new int[MOVING_PIECES_MAX];
        diceRoll = new int[]{1, 2, 3, 4};
        movingPiecesAvailable = MOVING_PIECES_MAX;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * rollDice method has too much randomness
     * a unit test is not the right kind of test in this situation
     */

    /**
     * Test of rollTurn method, of class DiceCup.
     * REFACTOR SO ALL TESTS ARE IN SEPARATE METHODS
     */
    @Test
    public void testDicePairingChoice_1() {
        System.out.println("dicePairingChoice - no moves filtered");
        
        Integer[][] pairings = {
            {1, 2}, 
            {3, 4},
            {1, 3},
            {2, 4},
            {1, 4},
            {2, 3}
        };
        int rows = 3;
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        List<List<Integer[]>> result = diceCup.getDiceChoicesFiltered();
        assertEquals(rows, result.size());
        
        dicePairingChoiceListCheck(result, pairings);
    }
    
    @Test
    public void testDicePairingChoice_2() {
        System.out.println("dicePairingChoice - some moving pieces filter moves");
        
        movingPieces = new int[]{3, 7};
        movingPiecesAvailable = MOVING_PIECES_MAX - movingPieces.length;
        
        Integer[][] pairings = {
            {1, 2}, 
            {3, 4},
            {1, 3},
            {2, 4},
            {1, 4},
            {2, 3}
        };
        int rows = 4;
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        List<List<Integer[]>> result = diceCup.getDiceChoicesFiltered();
        assertEquals(rows, result.size());
        
        dicePairingChoiceListCheck(result, pairings);
    }
    
    @Test
    public void testDicePairingChoice_3() {
        System.out.println("dicePairingChoice - maxed moving pieces filter moves");
        
        movingPieces = new int[]{3, 4, 7};
        movingPiecesAvailable = MOVING_PIECES_MAX - movingPieces.length;
        
        Integer[][] pairings = {
            {1, 2}, 
            {3, 4},
            {1, 3},
        };
        int rows = 2;
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        List<List<Integer[]>> result = diceCup.getDiceChoicesFiltered();
        assertEquals(rows, result.size());
        
        dicePairingChoiceListCheck(result, pairings);
    }
    
    @Test
    public void testDicePairingChoice_4() {
        System.out.println("dicePairingChoice - maxed out moving position filters moves");
        
        movingPos = new int[]{99, 0, 0, 99, 0, 99, 99, 99, 99, 99, 99};
        movingPieces = new int[]{5, 7};
        
        Integer[][] pairings = {
            {1, 2},
            {1, 3},
            {2, 4},
        };
        int row = 2;
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        List<List<Integer[]>> result = diceCup.getDiceChoicesFiltered();
        assertEquals(row, result.size());
        
        dicePairingChoiceListCheck(result, pairings);
    }
    
    @Test
    public void testDicePairingChoice_5() {
        System.out.println("dicePairingChoice - claimed columns filter moves");
        
        board.setColumnClaimed(7 - 2, true);
        board.setColumnClaimed(5 - 2, true);
        
        Integer[][] pairings = {
            {1, 2}, 
            {1, 3},
            {2, 4},
        };
        int row = 2;
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        List<List<Integer[]>> result = diceCup.getDiceChoicesFiltered();
        assertEquals(row, result.size());
        
        dicePairingChoiceListCheck(result, pairings);
    }
    
    @Test
    public void testDicePairingChoice_6() {
        System.out.println("dicePairingChoice - everything filters moves and busts");
        
        board.setColumnClaimed(7 - 2, true);
        board.setColumnClaimed(5 - 2, true);
        movingPos = new int[]{99, 99, 0, 99, 0, 99, 99, 99, 99, 99, 99};
        movingPieces = new int[]{3, 3, 3};
        movingPiecesAvailable = MOVING_PIECES_MAX - movingPieces.length;
        
        Integer[][] pairings = {};
        int rows = 0;
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        List<List<Integer[]>> result = diceCup.getDiceChoicesFiltered();
        assertEquals(rows, result.size());
        
        dicePairingChoiceListCheck(result, pairings);
    }
    
    @Test
    public void testDicePairingChoice_7() {
        System.out.println("dicePairingChoice - same numbers stay on the same line");
        
        movingPieces = new int[]{5, 7};
        diceRoll = new int[]{1, 2, 1, 2};
        movingPiecesAvailable = MOVING_PIECES_MAX - movingPieces.length;
        
        Integer[][] pairings = {
            {1, 2}, 
            {1, 2},
            {1, 1},
            {2, 2},
            {1, 2},
            {2, 1}
        };
        int rows = 4;
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        List<List<Integer[]>> result = diceCup.getDiceChoicesFiltered();
        assertEquals(rows, result.size());
        
        dicePairingChoiceListCheck(result, pairings);
    }
    
    /**
     * Test of choiceToOutput method, of class DiceCup.
     */
    @Test
    public void testChoiceToOutput_1() {
        System.out.println("choiceToOutput - 1 value");
        
        board.setColumnClaimed(7 - 2, true);
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        
        int selection = 1;
        int[] result = diceCup.choiceToOutput(selection);
        int[] resultExp = {3};
        
        for (int i = 0; i < result.length; i++)
        {
            assertEquals(resultExp[i], result[i]);
        }
    }
    
    @Test
    public void testChoiceToOutput_2() {
        System.out.println("choiceToOutput - 2 values");
        
        diceCup.dicePairingChoice(
                movingPos,
                movingPieces,
                movingPiecesAvailable,
                diceRoll,
                board
        );
        
        int selection = 1;
        int[] result = diceCup.choiceToOutput(selection);
        int[] resultExp = {3, 7};
        
        for (int i = 0; i < result.length; i++)
        {
            assertEquals(resultExp[i], result[i]);
        }
    }

    /**
     * Test of getDiceValueMin method, of class DiceCup.
     */
    @Test
    public void testGetDiceValueMin() {
        System.out.println("getDiceValueMin");
        int expResult = 1;
        int result = DiceCup.getDiceValueMin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDiceValueMax method, of class DiceCup.
     */
    @Test
    public void testGetDiceValueMax() {
        System.out.println("getDiceValueMax");
        int expResult = 6;
        int result = DiceCup.getDiceValueMax();
        assertEquals(expResult, result);
    }
}
