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
    private final static String userPrompt = "> ";
    
    public void gameStart()
    {
        
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
            System.out.print("Would you like to add a new player? [Y/n]:\n" + userPrompt);
            input = kbinput.nextLine().strip();
            if (input.toLowerCase().equals("y"))
            {
                players.add(addPlayer());
            } else if (!(input.toLowerCase().equals("y") || input.toLowerCase().equals("n"))) // invalid input
            {
                System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
            }
        } while (!input.toLowerCase().equals("n"));
        if (players.size() <= 0) { System.exit(0); };
        
        var board = new GameBoard();
        players.peek().setMoving(true);
        board.boardDraw(players);
    }
    
}
