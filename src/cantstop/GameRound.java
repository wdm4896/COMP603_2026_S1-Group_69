/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class GameRound extends JFrame {
    private final static boolean USE_GUI = true;
    private final static int FRAME_WIDTH = Game.getScreenHeight();
    private final static int FRAME_HEIGHT = Game.getScreenHeight();
    
    private final GameBoard board; // Board Model
    private final GameBoardUI boardUI; // Board View
    private final DiceCup diceCup; // Dice Cup Model
    private final DiceCupUI diceCupUI; // Dice Cup View
    private final Queue<Player> players;
    
    private boolean winConditionMet = false;
    private Player currentPlayer;
    private List<Integer> diceSelection;
    
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
        diceSelection = new LinkedList<>();
        this.setVisible(true);
        
        ((DiceCupGUI) diceCupUI).getRollDice().addActionListener((ActionEvent e) -> {
            boardUI.drawBoard();
            ((DiceCupGUI) diceCupUI).setRollSubmitEnabled(false);
            haveTurn(getCurrentPlayer());
        });
        
        ((DiceCupGUI) diceCupUI).getEndTurn().addActionListener((ActionEvent e) -> {
            ((DiceCupGUI) diceCupUI).resetDice();
            endTurn(getCurrentPlayer());
            diceCupUI.askToRoll();
        });
        
        ((DiceCupGUI) diceCupUI).getRollSubmit().addActionListener((ActionEvent e) -> {
            movePieces(getPairings());
            diceSelection = new LinkedList<>();
            boardUI.drawBoard();
            diceCupUI.askToRoll();
        });
        
        for (JButton die : ((DiceCupGUI) diceCupUI).getDiceSelection())
        {
            die.addActionListener((ActionEvent e) -> {
                // Find die index in dice list
                JButton[] dice = ((DiceCupGUI) diceCupUI).getDiceSelection();
                int index = 0;
                
                // The list is short, so theoretically the O(n^2) check each time doesn't add up to much
                for (int i = 0; i < dice.length; i++)
                {
                    if (dice[i] == die)
                    {
                        index = i;
                    }
                }
                
                modifyDiceSelection(index);
            });
        }
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
    
        
    private List<Integer[]> getPairings()
    {
        // Get pairings and visually display them
        int[] diceRoll = diceCupUI.getDiceCup().getDiceRoll();
        List<Integer[]> pairings = new ArrayList<>();
        Integer[] pairing;
        
        Iterator iterDiceSelection = diceSelection.iterator();
        
        while (iterDiceSelection.hasNext())
        {
            pairing = new Integer[DiceCup.getDiceChosenMax()];
            
            for (int i = 0; i < DiceCup.getDiceChosenMax(); i++)
            {
                pairing[i] = (Integer) diceRoll[(int) iterDiceSelection.next()];
            }
            Arrays.sort(pairing); // dicePairingsExists assumes sorted Integer arrays
            pairings.add(pairing);
        }
        
        return pairings;
    }
    
    private void modifyDiceSelection(int index)
    {
        Iterator iterDiceSelection = diceSelection.iterator();
        Integer diceSelectionIndex;
        boolean diceSelectionContainsIndex = false;
        
        // Check if dice selection contains index provided (toggle)
        while (!diceSelectionContainsIndex && iterDiceSelection.hasNext())
        {
            diceSelectionIndex = (Integer) iterDiceSelection.next();
            if (diceSelectionIndex.equals(index))
            {
                diceSelectionContainsIndex = true;
            }
        }
        
        // Adds or removes index from diceSelection
        if (diceSelectionContainsIndex)
        {
            diceSelection.remove((Integer) index);
        }
        else
        {
            diceSelection.add((Integer) index);
        }
        
        ((DiceCupGUI) diceCupUI).displayPairings(diceSelection);
        
        checkPairings();
    }
    
    private void checkPairings()
    {
        // Immediately end if dice selection pairing isn't even or is empty
        if (diceSelection.size() % DiceCup.getDiceChosenMax() != 0 || diceSelection.isEmpty())
        {
            ((DiceCupGUI) diceCupUI).setRollSubmitEnabled(false);
            return;
        }
        
        // Check with only valid pairings
        ((DiceCupGUI) diceCupUI).setRollSubmitEnabled(diceCup.dicePairingsExists(getPairings()));
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
        
        boardUI.drawBoard();
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
        boardUI.drawBoard();
    }
    
    private void nextPlayer()
    {
        this.players.add(this.players.poll());
        this.currentPlayer = this.players.peek();
        this.currentPlayer.setMoving(true);
        if (USE_GUI)
        {
            ((DiceCupGUI) this.diceCupUI).updateTurnList(this.players);
        }
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
        
        movePieces(saveValue);
    }
    
    private void movePieces(int saveValue)
    {
        // Save the choice
        int[] diceChoice = diceCup.choiceToOutput(saveValue);
        currentPlayer.saveMoving(diceChoice, this.board);
    }
    
    private void movePieces(List<Integer[]> saveChosen)
    {
        // Save the choice
        int[] diceChoice = diceCup.choiceToOutput(saveChosen);
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
