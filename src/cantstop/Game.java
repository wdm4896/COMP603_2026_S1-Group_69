/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author admin
 */
public class Game {
    private final static int PLAYERS_MAX = 4;
    private final static int WIN_CONDITION = 3;
    public final static String USER_PROMPT = "> ";
    private final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
    private static Queue<Player> players;
    private static GameRound roundCurrent;
    
    private static void gameStart()
    {
        roundCurrent = new GameRound(players);
        roundCurrent.play();        
    }
    
    private static void gameEnd(GameScore scoreBoard)
    {
        var kbinput = new Scanner(System.in);
        String input;
        
        System.out.println("\nWould you like to save all scores? [y/n]:");
        do {
            System.out.print(USER_PROMPT);
            input = kbinput.nextLine().strip().toLowerCase(); // normalise input
            if (input.equals("y"))
            {
                System.out.println("\nSaving scores...");
                scoreBoard.scoresSave(players);
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            } else if (!(input.equals("y") || input.equals("n"))) // invalid input
            {
                System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
            }
        } while (!(input.equals("y") || input.equals("n")));
        
        System.out.println("\nThanks for playing!");
    }
    
    private static Player addPlayer()
    {
        String name;
        Colour colour;
        
        // Set player name
        Scanner kbinput = new Scanner(System.in);
        System.out.print("\nWhat is your name?\n" + USER_PROMPT);
        name = kbinput.nextLine();
        
        // Prompt for colour input
        System.out.println("\nWhich colour would you like to play as?");
        System.out.println("Please choose a number from the following list:");
        Colour colourOption;
        for (int i = 1; i < Colour.values().length; i++) // first colour is the default terminal one - skip
        {
            colourOption = Colour.values()[i];
            System.out.println(i + ") " + colourOption.font() + colourOption.name() + Colour.DEFAULT.font());
        }
        
        // Set player colour
        int colourIndex = -1;
        do
        {
            System.out.print(USER_PROMPT);
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
        System.out.println("\nAdding player " + colour.font() + name + Colour.DEFAULT.font() + " to the game...\n" );
        
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        
        Player player = new Player(name, colour);
        return player;
    }
    
    public static void main(String[] args) {
        players = new LinkedList<Player>();
        var kbinput = new Scanner(System.in);
        var scoreBoard = new GameScore();
        
        // Add players
        String input = "";
        do {
            System.out.print("Would you like to add a new player? [y/n]:\n" + USER_PROMPT);
            input = kbinput.nextLine().strip().toLowerCase(); // normalise input
            if (input.equals("y"))
            {
                players.add(addPlayer());
            } else if (!(input.equals("y") || input.equals("n"))) // invalid input
            {
                System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
            }
        } while (!input.equals("n") && players.size() < PLAYERS_MAX);
        if (players.size() <= 0) {
            kbinput.close();
            System.exit(0);
        }
        
        // Play the game
        boolean play = true;
        do
        {
            System.out.println("\nStarting game...");
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            gameStart();
            
            // Play again
            do
            {
                scoreBoard.scoresDisplay(players);
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                System.out.print("\nWould you like to play again? [y/n]:\n" + USER_PROMPT);
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
        
        gameEnd(scoreBoard);
        
        kbinput.close();
    }
        
    public static int getWinCondition()
    {
        return WIN_CONDITION;
    }
    
    public static int getScreenWidth()
    {
        return (int) SCREEN_SIZE.getWidth();
    }
    
    public static int getScreenHeight()
    {
        return (int) SCREEN_SIZE.getHeight();
    }
}
