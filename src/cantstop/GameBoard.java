/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Iterator;
import java.util.Queue;
/**
 *
 * @author admin
 */
public class GameBoard extends Board {
    private final static int columnMin = DiceCup.getDiceValueMin() * 2;
    private final static int columnMax = DiceCup.getDiceValueMax() * 2;
    private final static int boardWidth = columnMax - columnMin + 1; // include both ends of the board
    private final static int lengthMin = 3;
    private final static int lengthMax = 13;
    
    private final static int[] columnValues = new int[boardWidth];
    private final static int[] columnSizes = new int[boardWidth];
    private final boolean[] columnClaimed = new boolean[boardWidth];
    
    public GameBoard()
    {
        // Generate game board
        int step = 2 * (lengthMax - lengthMin) / (columnMax - columnMin);
        int size = lengthMin;
        int value = columnMin;
        for (int i = 0; i < boardWidth; i++)
        {
            columnValues[i] = value++;
            columnSizes[i] = size;
            columnClaimed[i] = false;
            size += step;
            if (size >= lengthMax) { step *= -1; }
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
            for (int i = 0; i < boardWidth; i++)
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
        return columnMin;
    }
    
    public static int getBoardWidth()
    {
        return boardWidth;
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
