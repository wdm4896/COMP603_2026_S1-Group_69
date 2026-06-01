/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class GameManagerCLI extends GameManagerUI {
    
    public GameManagerCLI(GameManager gameManager)
    {
        super(gameManager);
    }
    
    @Override
    public void gameStart()
    {
        // Play the game
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
            this.getGameManager().gameStart();
            
            // Play again
            var kbinput = new Scanner(System.in);
            String input;
            
            do
            {
                this.getGameManager().getScoreBoard().scoresDisplayCLI();
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                System.out.print("\nWould you like to play again? [y/n]:\n" + Game.USER_PROMPT);
                input = kbinput.nextLine().strip().toLowerCase(); // normalise input
                
                if (input.equals("y") || input.equals("n")) // valid input
                {
                    this.setPlay(input.equals("y"));
                } else
                {
                    System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
                }
            } while (!(input.equals("y") || input.equals("n")));
        } while (this.getPlay());
    }
    
    @Override
    public void gameEnd()
    {
        var kbinput = new Scanner(System.in);
        String input;
        
        System.out.println("\nWould you like to save all scores? [y/n]:");
        do {
            System.out.print(Game.USER_PROMPT);
            input = kbinput.nextLine().strip().toLowerCase(); // normalise input
            if (input.equals("y"))
            {
                System.out.println("\nSaving scores...");
                this.getGameManager().saveScores();
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
    
    @Override
    public void addPlayer()
    {
        String name;
        Colour colour;
        
        // Set player name
        Scanner kbinput = new Scanner(System.in);
        System.out.print("\nWhat is your name?\n" + Game.USER_PROMPT);
        name = kbinput.nextLine();
        
        // Prompt for colour input
        System.out.println("\nWhich colour would you like to play as?");
        System.out.println("Please choose a number from the following list:");
        Colour colourOption;
        Iterator iterColoursAvailable = this.getGameManager().getColoursAvailable().iterator();
        iterColoursAvailable.next(); // first colour is the default terminal one - skip
        
        int option = 1;
        while (iterColoursAvailable.hasNext())
        {
            colourOption = (Colour) iterColoursAvailable.next();
            System.out.println(option++ + ") " + colourOption.font() + colourOption.name() + Colour.DEFAULT.font());
        }
        
        // Set player colour
        int colourIndex = -1;
        do
        {
            System.out.print(Game.USER_PROMPT);
            try
            {
                colourIndex = kbinput.nextInt();
            } catch(java.util.InputMismatchException e)
            {
                kbinput.nextLine();
            }
            
            if (!(1 <= colourIndex && colourIndex < Colour.values().length))
            {
                System.out.println("Invalid input. Please input a value between 1 and " + (this.getGameManager().getColoursAvailable().size() - 1) + "...");
            }
        } while (!(1 <= colourIndex && colourIndex < this.getGameManager().getColoursAvailable().size()));
        colour = this.getGameManager().getColoursAvailable().get(colourIndex);
        
        System.out.println("\nAdding player " + colour.font() + name + Colour.DEFAULT.font() + " to the game...\n" );
        this.getGameManager().addPlayer(name, colour);
        
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public void sessionPrepare() {
        var kbinput = new Scanner(System.in);
        
        // Add players
        String input;
        do {
            System.out.print("Would you like to add a new player? [y/n]:\n" + Game.USER_PROMPT);
            input = kbinput.nextLine().strip().toLowerCase(); // normalise input
            if (input.equals("y"))
            {
                addPlayer();
            } else if (!(input.equals("y") || input.equals("n"))) // invalid input
            {
                System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
            }
        } while (!input.equals("n") && this.getGameManager().getPlayers().size() < Game.getPlayersMax());
        if (this.getGameManager().getPlayers().size() <= 0) {
            kbinput.close();
            System.exit(0);
        }
        
        gameStart();
        
        gameEnd();
        
        kbinput.close();
    }
}
