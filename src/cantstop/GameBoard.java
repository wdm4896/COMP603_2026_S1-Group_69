/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Iterator;
import java.util.Queue;
import java.lang.Math;
/**
 *
 * @author admin
 */
public class GameBoard {
    private final static int COLUMN_MIN = DiceCup.getDiceValueMin() * 2;
    private final static int COLUMN_MAX = DiceCup.getDiceValueMax() * 2;
    private final static int BOARD_WIDTH = COLUMN_MAX - COLUMN_MIN + 1; // include both ends of the board
    private final static int LENGTH_MIN = 3;
    private final static int LENGTH_MAX = 13;
    
    private final static int[] columnValues = new int[BOARD_WIDTH];
    private final static int[] columnSizes = new int[BOARD_WIDTH];
    private final boolean[] columnClaimed = new boolean[BOARD_WIDTH];
    
    private final Queue<Player> players;
    
    public GameBoard(Queue<Player> players)
    {
        this.players = players;
        resetPlayers();
        
        // Generate game board
        int valueMiddle = (COLUMN_MAX + COLUMN_MIN) / 2;
        float size;
        int value = COLUMN_MIN;
        for (int i = 0; i < BOARD_WIDTH; i++)
        {
            // Set sizes based on equation https://www.desmos.com/calculator/ynwigw058k
            if (i <= BOARD_WIDTH / 2)
            {
                size = i * ((float) (LENGTH_MAX - LENGTH_MIN) / (valueMiddle - COLUMN_MIN)) + LENGTH_MIN;
            } else
            {
                size = (i - BOARD_WIDTH / 2) * -((float) (LENGTH_MAX - LENGTH_MIN) / (valueMiddle - COLUMN_MIN)) + LENGTH_MAX;
            }
            
            columnValues[i] = value++;
            columnSizes[i] = Math.round(size);
            columnClaimed[i] = false;
        }
    }
    
    private void resetPlayers()
    {
        Iterator iterPlayers = players.iterator();
        Player player;
        
        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            player.resetColumns();
        }
    }
    
    public void clearColumnsClaimed(Queue<Player> players)
    {
        Iterator iterPlayers = players.iterator();
        Player player;

        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            
            // Forcibly set all claimed column values to -1
            for (int i = 0; i < BOARD_WIDTH; i++)
            {
                if (this.columnClaimed[i])
                {
                    player.blockColumn(i);
                }
            }
        }
    }
    
    public int[] getColumnValues()
    {
        return columnValues;
    }
    
    public int[] getColumnSizes()
    {
        return columnSizes;
    }
    
    public int getColumnMin()
    {
        return COLUMN_MIN;
    }
    
    public static int getBoardWidth()
    {
        return BOARD_WIDTH;
    }
    
    public static int getLengthMax()
    {
        return LENGTH_MAX;
    }
    
    public void setColumnClaimed(int index, boolean claimed)
    {
        this.columnClaimed[index] = claimed;
    }
    
    public boolean getColumnClaimed(int index)
    {
        return this.columnClaimed[index];
    }
}
