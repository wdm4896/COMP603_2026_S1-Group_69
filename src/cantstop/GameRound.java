/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Queue;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class GameRound extends JFrame {
    private final static boolean USE_GUI = false;
    private final static int FRAME_WIDTH = Game.getScreenHeight();
    private final static int FRAME_HEIGHT = Game.getScreenHeight(); // Square for now
    
    private final GameBoard board; // Board Model
    private final GameBoardUI boardUI; // Board View
    private final DiceCup diceCup; // Dice Cup Model
    private final DiceCupUI diceCupUI; // Dice Cup View
    private final Queue<Player> players;
    
    public GameRound(Queue<Player> players)
    {
        // Title
        super("Game Round");
        
        // Round attributes
        this.players = players;
        this.board = new GameBoard(players);
        this.diceCup = new DiceCup();
        if (USE_GUI)
        {
            this.boardUI = new GameBoardGUI(board, players);
            this.diceCupUI = null;
//            this.diceCupUI = new DiceCupGUI();
        } 
        else
        {
            this.boardUI = new GameBoardCLI(board, players);
            this.diceCupUI = new DiceCupCLI(diceCup);
        }
        
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
    
    private void playGUI() // Not functional yet
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
//                currentPlayer.rollTurn(board, diceCup);
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
        var kbinput = new Scanner(System.in);
        String input;
        
        Player currentPlayer = null;
        boolean winConditionMet = false;
        
        // Play game as long as someone hasn't won yet
        while (!winConditionMet)
        {
            currentPlayer = players.peek();
            currentPlayer.setMoving(true);
            
            do {
                boardUI.drawBoard();

                // Ask to continue turn
                do {
                    diceCupUI.askToRoll();
                    input = kbinput.nextLine().strip().toLowerCase(); // normalise input
                    switch (input)
                    {
                        case "y" -> haveTurn(currentPlayer);
                        case "n" -> currentPlayer.savePos(this.board);
                        default -> // invalid input
                            System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
                    }
                } while (!(input.equals("y") || input.equals("n")));
                
                
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
    
    private void haveTurn(Player currentPlayer)
    {
        // Check if the user has any available choices after a roll
        boolean hasChoicesAvailable = diceCup.rollTurn(
                currentPlayer,
                this.board
        );
        
        if (!hasChoicesAvailable) {
            currentPlayer.bust();
            diceCupUI.bust();
            return;
        }
        
        diceCupUI.displayChoices();
        
        // Select input
        var kbinput = new Scanner(System.in);
        
        int saveValue = -1;
        do
        {
            try
            {
                saveValue = kbinput.nextInt();
            } catch(java.util.InputMismatchException e)
            {
                kbinput.nextLine();
            }

            if (!(1 <= saveValue && saveValue <= diceCup.getDiceChoicesFiltered().size()))
            {
                System.out.println("Invalid input. Please input a value between 1 and " + (diceCup.getDiceChoicesFiltered().size()) + "...\n" + Game.USER_PROMPT);
            }
        } while (!(1 <= saveValue && saveValue <= diceCup.getDiceChoicesFiltered().size()));
        
        // Save the choice
        int[] diceChoice = diceCup.choiceToOutput(saveValue);
        currentPlayer.saveMoving(diceChoice, this.board);
    }
}
