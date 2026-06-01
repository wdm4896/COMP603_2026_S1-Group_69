/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Iterator;

/**
 *
 * @author admin
 */
public class GameBoardCLI extends GameBoardUI {
    public GameBoardCLI(GameBoard board)
    {
        super(board);
    }
    
    @Override
    public void drawBoard()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // "clear" the screen...
        drawLine("Value", this.getBoard().getColumnValues(), null);
        drawLine("Size", this.getBoard().getColumnSizes(), null);
        
        Iterator iterPlayers = Game.getPlayers().iterator();
        Player player;
        Player playerMoving = null;
        
        // Print player current positions
        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            if (player.isMoving()) { playerMoving = player; }
            System.out.print(player.getColour().font());
            drawLine(player.getName(), player.getPosCurrent(), player);
        }
        
        // Print moving player position
        if (playerMoving != null)
        {
            System.out.println("Current moving player: " + playerMoving.getColour().font() + playerMoving.getName() + Colour.DEFAULT.font());
            drawLine(
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
    
    private void drawLine(String name, int[] columns, Player player)
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
}
