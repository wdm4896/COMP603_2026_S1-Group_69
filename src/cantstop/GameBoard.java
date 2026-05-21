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
public class GameBoard extends Board {
    private final static int COLUMN_MIN = DiceCup.getDiceValueMin() * 2;
    private final static int COLUMN_MAX = DiceCup.getDiceValueMax() * 2;
    private final static int BOARD_WIDTH = COLUMN_MAX - COLUMN_MIN + 1; // include both ends of the board
    private final static int LENGTH_MIN = 3;
    private final static int LENGTH_MAX = 13;
    
    private final static int[] columnValues = new int[BOARD_WIDTH];
    private final static int[] columnSizes = new int[BOARD_WIDTH];
    private final boolean[] columnClaimed = new boolean[BOARD_WIDTH];
    
    public GameBoard()
    {
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
    
    @Override
    public void boardDraw(Queue<Player> players)
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // "clear" the screen...
        lineDraw("Value", columnValues, null);
        lineDraw("Size", columnSizes, null);
        
        Iterator iterPlayers = players.iterator();
        Player player;
        Player playerMoving = null;
        
        // Print player current positions
        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            if (player.isMoving()) { playerMoving = player; }
            System.out.print(player.getColour().font());
            lineDraw(player.getName(), player.getPosCurrent(), player);
        }
        
        // Print moving player position
        if (playerMoving != null)
        {
            System.out.println("Current moving player: " + playerMoving.getColour().font() + playerMoving.getName() + Colour.DEFAULT.font());
            lineDraw(
                    playerMoving.getName() + " (M)", 
                    playerMoving.getPosMoving(), 
                    playerMoving
            );
            
            // Print moving pieces for moving player
            if (playerMoving.getMovingPiecesMax() - playerMoving.getMovingPiecesAvailable() > 0)
            {
                int[] movingPieces = playerMoving.getMovingPieces();
                System.out.print("Moving piece values: " + movingPieces[0]);
                for (int piece = 1; piece < movingPieces.length; piece++)
                {
                    if (movingPieces[piece] == 0) { continue; }
                    System.out.print(", " + movingPieces[piece]);
                }
                System.out.println("");
            }
        }
    }
    
    private void lineDraw(String name, int[] columns, Player player)
    {
        System.out.print(String.format("%-15s", name));
        for (int column = 0; column < columns.length; column++)
        {
            if (columns[column] == -1) // claimed column
            {
                boolean claimed = false;
                
                for (int claimedColumn : player.getClaimedColumns())
                {
                    // Check if column is claimed by current player
                    if (column == claimedColumn) { claimed = true; }
                }
                
                System.out.print(((claimed) ? " CL" : " XX"));
            } else
            {
                System.out.print(" " + String.format("%02d", columns[column]));
            }
        }
        System.out.println(Colour.DEFAULT.font());
        
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
    
    public void setColumnClaimed(int index, boolean claimed)
    {
        this.columnClaimed[index] = claimed;
    }
    
    public boolean getColumnClaimed(int index)
    {
        return this.columnClaimed[index];
    }
}
