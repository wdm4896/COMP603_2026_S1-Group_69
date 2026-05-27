/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Queue;
import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class GameRound extends JFrame {
    private final static boolean USE_GUI = true;
    private final static int FRAME_WIDTH = Game.getScreenHeight();
    private final static int FRAME_HEIGHT = Game.getScreenHeight(); // Square for now
    
    private final GameBoard board; // Board Model
    private final GameBoardUI boardUI; // Board View
    private final Queue<Player> players;
    private final DiceCup diceCup;
    
    public GameRound(Queue<Player> players)
    {
        // Title
        super("Game Round");
        
        // Round attributes
        this.players = players;
        this.board = new GameBoard(players);
        if (USE_GUI)
        {
            this.boardUI = new GameBoardGUI(board, players);
        } 
        else
        {
            this.boardUI = new GameBoardCLI(board, players);
        }
        this.diceCup = new DiceCup();
        
        // Frame components
        this.add(boardUI);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void play()
    {
        if (USE_GUI)
        {
            playGUI();
        }
        else
        {
            playCLI(); // Fallback
        }
    }
    
    private void playGUI()
    {
        Player currentPlayer = null;
        boolean winConditionMet = false;
        this.setVisible(true);
        
        // Play game as long as someone hasn't won yet
        while (!winConditionMet)
        {
            currentPlayer = players.peek();
            currentPlayer.setMoving(true);
            
            do {
                boardUI.drawBoard();
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
        
        boardUI.drawBoard();
        System.out.println("\n" + currentPlayer.getColour().font() + currentPlayer.getName() + Colour.DEFAULT.font() + " wins!!!");
    }
    
    private void playCLI()
    {
        Player currentPlayer = null;
        boolean winConditionMet = false;
        
        // Play game as long as someone hasn't won yet
        while (!winConditionMet)
        {
            currentPlayer = players.peek();
            currentPlayer.setMoving(true);
            
            do {
                boardUI.drawBoard();
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
        
        boardUI.drawBoard();
        System.out.println("\n" + currentPlayer.getColour().font() + currentPlayer.getName() + Colour.DEFAULT.font() + " wins!!!");
    }
}
