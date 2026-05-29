/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.awt.event.ActionEvent;
import java.util.Queue;
import java.util.Scanner;
import javax.swing.BoxLayout;
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
    private final DiceCup diceCup; // Dice Cup Model
    private final DiceCupUI diceCupUI; // Dice Cup View
    private final Queue<Player> players;
    
    private Player currentPlayer;
    private boolean winConditionMet = false;
    
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
            this.diceCupUI = new DiceCupGUI(diceCup);
        } 
        else
        {
            this.boardUI = new GameBoardCLI(board, players);
            this.diceCupUI = new DiceCupCLI(diceCup);
        }

        // Frame components
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.add(boardUI);
        this.add(diceCupUI);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        // Set up round beforehand
        boardUI.drawBoard();
        diceCupUI.askToRoll();
        ((DiceCupGUI) diceCupUI).updateTurnList(players);
        currentPlayer = players.peek();
        currentPlayer.setMoving(true);
        this.setVisible(true);
        
        ((DiceCupGUI) diceCupUI).getRollDice().addActionListener((ActionEvent e) -> {
            boardUI.drawBoard();
            haveTurn(getCurrentPlayer());
        });
        
        ((DiceCupGUI) diceCupUI).getEndTurn().addActionListener((ActionEvent e) -> {
            endTurn(getCurrentPlayer());
            diceCupUI.askToRoll();
        });
        
        ((DiceCupGUI) diceCupUI).getRollSubmit().addActionListener((ActionEvent e) -> {
            movePieces(1); // Hardcoded to 1 for testing purposes
            
            boardUI.drawBoard();
            diceCupUI.askToRoll();
        });
    }
    
    private void playCLI()
    {
        var kbinput = new Scanner(System.in);
        String input;
        
//        Player currentPlayer = null;
        currentPlayer = players.peek();
        currentPlayer.setMoving(true);
        boardUI.drawBoard();
        
        // Play game as long as someone hasn't won yet
        while (!winConditionMet)
        {
            do {
                // Ask to continue turn
                do {
                    diceCupUI.askToRoll();
                    input = kbinput.nextLine().strip().toLowerCase(); // normalise input
                    switch (input)
                    {
                        case "y" -> haveTurn(currentPlayer);
                        case "n" -> endTurn(currentPlayer);
                        default -> // invalid input
                            System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
                    }
                } while (!(input.equals("y") || input.equals("n")));
                
                
            } while (currentPlayer.isMoving());
        }
        
        System.out.println("\n" + currentPlayer.getColour().font() + currentPlayer.getName() + Colour.DEFAULT.font() + " wins!!!");
    }
    
    private void haveTurn(Player currentPlayer)
    {
        // Check if the user has any available choices after a roll
        boolean hasChoicesAvailable = diceCup.rollTurn(
                currentPlayer,
                this.board
        );
        
        diceCupUI.displayDice();
        
        if (!hasChoicesAvailable) {
            bust();
            return;
        }
        
        if (USE_GUI)
        {
            diceCupUI.askToSelect();
        }
        else
        {
            makeChoice();
        }
    }
    
    private void endTurn(Player currentPlayer)
    {
        currentPlayer.savePos(this.board);
        board.clearColumnsClaimed(players);
            
        if (currentPlayer.getClaimedTotal() >= Game.getWinCondition())
        {
            synchronized(Game.USER_PROMPT) {
                winConditionMet = true;
                currentPlayer.hasWon();
                Game.USER_PROMPT.notify();
            }
        }
        else
        {
            nextPlayer();
        }
        
        boardUI.drawBoard();
    }
    
    private void bust()
    {
        currentPlayer.bust();
        diceCupUI.bust();
        
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        
        nextPlayer();
    }
    
    private void nextPlayer()
    {
        this.players.add(this.players.poll());
        this.currentPlayer = this.players.peek();
        this.currentPlayer.setMoving(true);
        ((DiceCupGUI) this.diceCupUI).updateTurnList(this.players);
    }
    
    private void makeChoice() // For CLI
    {
        diceCupUI.displayChoices();
        
        // Select input
        var kbinput = new Scanner(System.in);

        int saveValue = -1;
        do
        {
            diceCupUI.askToSelect();
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
    }
    
    private void movePieces(int saveValue)
    {
        // Save the choice
        int[] diceChoice = diceCup.choiceToOutput(saveValue);
        currentPlayer.saveMoving(diceChoice, this.board);
    }
    
    public Player getCurrentPlayer()
    {
        return this.currentPlayer;
    }
    
    public GameBoard getBoard()
    {
        return this.board;
    }
    
    public boolean getWinConditionMet()
    {
        return this.winConditionMet;
    }
}
