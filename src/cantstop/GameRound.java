/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Iterator;
import java.util.Queue;
import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class GameRound extends JFrame {
    private final GameBoard board; // Board Model
    private final GameBoardUI boardUI; // Board View
    private final Queue<Player> players;
    private final DiceCup diceCup;
    
    public GameRound(Queue<Player> players)
    {
        this.players = players;
        this.board = new GameBoard();
        this.boardUI = new GameBoardCLI(board, players);
        this.diceCup = new DiceCup();
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
    
    public void play()
    {
        resetPlayers();
        Player currentPlayer = null;
        boolean winConditionMet = false;
        
        // Play game as long as someone hasn't won yet
        while (!winConditionMet)
        {
            currentPlayer = players.peek();
            currentPlayer.setMoving(true);
            
            do {
                boardUI.boardDraw();
                currentPlayer.haveTurn(board, diceCup);
            } while (currentPlayer.isMoving());
            
            board.clearColumnsClaimed(players);
            
            if (currentPlayer.getClaimedTotal() >= Game.getWinCondition())
            {
                winConditionMet = true;
                currentPlayer.hasWon();
            } else
            {
                players.add(players.poll());
            }
        }
        
        boardUI.boardDraw();
        System.out.println("\n" + currentPlayer.getColour().font() + currentPlayer.getName() + Colour.DEFAULT.font() + " wins!!!");
    }
    
}
