/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

import java.util.*;
/**
 *
 * @author admin
 */
public class Game {

    private final static int winCondition = 3;
    public final static String userPrompt = "> ";
    
    public static void gameStart(Queue<Player> players)
    {
        var board = new GameBoard();
        var diceCup = new DiceCup();
        
        Player currentPlayer = null;
        boolean winConditionMet = false;
        
        gameReset(players);
        
        // Play game as long as someone hasn't won yet
        while (!winConditionMet)
        {
            currentPlayer = players.peek();
            currentPlayer.setMoving(true);
            
            do {
                board.boardDraw(players);
                currentPlayer.haveTurn(board, diceCup);
            } while (currentPlayer.isMoving());
            
            board.clearColumnsClaimed(players);
            
            if (currentPlayer.getClaimedTotal() == winCondition)
            {
                winConditionMet = true;
            } else
            {
                players.add(players.poll());
            }
        }
        
        board.boardDraw(players);
        System.out.println("\n" + currentPlayer.getColour().getAnsi() + currentPlayer.getName() + Colour.DEFAULT.getAnsi() + " wins!!!");
    }
    
    public static void gameReset(Queue<Player> players)
    {
        Iterator iterPlayers = players.iterator();
        Player player;
        
        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            player.resetColumns();
        }
    }
    
    public void gameEnd()
    {
        
    }
    
    public static Player addPlayer()
    {
        String name;
        Colour colour;
        
        // Set player name
        Scanner kbinput = new Scanner(System.in);
        System.out.print("\nWhat is your name?\n" + userPrompt);
        name = kbinput.nextLine();
        
        // Prompt for colour input
        System.out.println("\nWhich colour would you like to play as?");
        System.out.println("Please choose a number from the following list:");
        Colour colourOption;
        for (int i = 1; i < Colour.values().length; i++) // first colour is the default terminal one - skip
        {
            colourOption = Colour.values()[i];
            System.out.println(i + ") " + colourOption.name());
        }
        
        // Set player colour
        int colourIndex = -1;
        do
        {
            System.out.print(userPrompt);
            try
            {
                colourIndex = kbinput.nextInt();
            } catch(java.util.InputMismatchException e)
            {
                kbinput.nextLine();
            }
            
            if (!(1 <= colourIndex && colourIndex < Colour.values().length))
            {
                System.out.println("Invalid input. Please input a value between 1 and " + (Colour.values().length - 1) + "...");
            }
        } while (!(1 <= colourIndex && colourIndex < Colour.values().length));
        colour = Colour.values()[colourIndex];
        System.out.println("\nAdding player " + colour.getAnsi() + name + Colour.DEFAULT.getAnsi() + " to the game...\n" );

        
        Player player = new Player(name, colour);
        return player;
    }
    
    public static int getWinCondition()
    {
        return winCondition;
    }
    
    public static void main(String[] args) {
        Queue<Player> players = new LinkedList<Player>();
        var kbinput = new Scanner(System.in);
        
        // Add players
        String input = "";
        do {
            System.out.print("Would you like to add a new player? [y/n]:\n" + userPrompt);
            input = kbinput.nextLine().strip().toLowerCase(); // normalise input
            if (input.equals("y"))
            {
                players.add(addPlayer());
            } else if (!(input.equals("y") || input.equals("n"))) // invalid input
            {
                System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
            }
        } while (!input.equals("n"));
        if (players.size() <= 0) { System.exit(0); }
        
        // Play the game
        boolean play = true;
        do
        {
            gameStart(players);
            do
            {    
                System.out.print("\nWould you like to play again? [y/n]:\n" + userPrompt);
                input = kbinput.nextLine().strip().toLowerCase(); // normalise input
                
                if (input.equals("y") || input.equals("n")) // valid input
                {
                    play = (input.equals("y"));
                } else
                {
                    System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
                }
            } while (!(input.equals("y") || input.equals("n")));
        } while (play);
    }
}
