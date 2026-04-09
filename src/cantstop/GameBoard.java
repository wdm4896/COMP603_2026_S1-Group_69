/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.*;
/**
 *
 * @author admin
 */
public class GameBoard extends Board {
    private final static int columnMin = 2;
    private final static int columnMax = 12;
    private final static int boardWidth = columnMax - columnMin + 1;
    private final static int lengthMin = 3;
    private final static int lengthMax = 13;
    
    private final static int[] columnValues = new int[boardWidth];
    private final static int[] columnSizes = new int[boardWidth]; // include both ends of the board
    
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
            size += step;
            if (size >= lengthMax) { step *= -1; }
        }
    }
    
    @Override
    public void boardDraw(Queue<Player> players)
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n"); // "clear" the screen...
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
            System.out.print(player.getColour().getAnsi());
            lineDraw(player.getName(), player.getPosCurrent(), player);
        }
        
        // Print moving player position
        if (playerMoving != null)
        {
            System.out.println("Current moving player: " + playerMoving.getColour().getAnsi() + playerMoving.getName() + Colour.DEFAULT.getAnsi());
            lineDraw(
                    playerMoving.getName() + " (M)", 
                    playerMoving.getPosMoving(), 
                    playerMoving
            );
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
                
                if (claimed)
                {
                    System.out.print(" CL");
                } else
                {
                    System.out.print(" XX");
                }
            } else
            {
                System.out.print(" " + String.format("%02d", columns[column]));
            }
        }
        System.out.println(Colour.DEFAULT.getAnsi());
        
    }
    
    public int[] getColumnSizes()
    {
        return this.columnSizes;
    }
    
    public static int getBoardWidth()
    {
        return boardWidth;
    }
}
