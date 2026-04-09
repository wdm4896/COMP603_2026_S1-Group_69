/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.*;
/**
 *
 * @author admin
 */
public class Player extends Person implements Turn {
    private int[] posCurrent = new int[GameBoard.getBoardWidth()];
    private int[] posMoving = new int[GameBoard.getBoardWidth()];
    private boolean isMoving = false;
    private int claimedTotal = 0;
    private final int[] claimedColumns = new int[Game.getWinCondition()];
    
    public Player(String name, Colour colour)
    {
        super(name, colour);
    }
    
    @Override
    public int[] getPosCurrent()
    {
        return this.posCurrent;
    }
    
    @Override
    public void savePos(GameBoard board)
    {
        this.posCurrent = this.posMoving.clone();
        for (int i = 0; i < GameBoard.getBoardWidth(); i++)
        {
            // Check for any claimed columns not yet tracked
            if (
                    board.getColumnSizes()[i] == this.posCurrent[i] &&
                    board.getColumnClaimed(i) == false
            ) {
                this.claimedColumns[this.claimedTotal++] = i;
                board.setColumnClaimed(i, true);
            }
        }
    }
    
    public void resetColumns()
    {
        for (int i = 0; i < this.claimedColumns.length; i++)
        {
            this.claimedColumns[i] = -1;
        }
        this.claimedTotal = 0;
        
        this.posCurrent = new int[GameBoard.getBoardWidth()];
        this.posMoving = new int[GameBoard.getBoardWidth()];
    }
    
    @Override
    public boolean isMoving()
    {
        return this.isMoving;
    }
    
    @Override
    public void setMoving(boolean isMoving)
    {
        this.isMoving = isMoving;
    }
    
    @Override
    public int[] getPosMoving()
    {
        return this.posMoving;
    }
    
    public void blockColumn(int index)
    {
        this.posCurrent[index] = -1;
        this.posMoving[index] = -1;
    }
    
    @Override
    public void haveTurn(GameBoard board, int[] diceRoll)
    {
        var kbinput = new Scanner(System.in);
        
        // Ask to continue turn
        String input = "";
        do {
            System.out.print("\nWould you like to roll? [Y/n]:\n" + Game.userPrompt);
            input = kbinput.nextLine().strip().toLowerCase(); // normalise input
            if (input.equals("n"))
            {
                savePos(board);
                this.isMoving = false;
                return;
            } else if (!(input.equals("y") || input.equals("n"))) // invalid input
            {
                System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
            }
        } while (!input.equals("y"));
        
        
        // Roll dice
        for (int dice = 0; dice < diceRoll.length; dice++)
        {
            System.out.println((dice + 1) + ") " + diceRoll[dice]);
        }
        
        // Save values to movement position - will need to be changed entirely
        System.out.println("Placeholder text...");
        int saveValue = -1;
        do
        {
            System.out.print(Game.userPrompt);
            try
            {
                saveValue = kbinput.nextInt();
            } catch(java.util.InputMismatchException e)
            {
                kbinput.nextLine();
            }
            
            if (!(1 <= saveValue && saveValue <= diceRoll.length))
            {
                System.out.println("Invalid input. Please input a value between 1 and " + (diceRoll.length) + "...");
            }
        } while (!(1 <= saveValue && saveValue <= diceRoll.length));
        
        // Increases value
        int targetIndex = (2 * diceRoll[saveValue - 1]) - board.getColumnMin();
        if (
                0 <= this.posMoving[targetIndex] &&
                this.posMoving[targetIndex] < board.getColumnSizes()[targetIndex]
        ) {
            this.posMoving[targetIndex]++;
        }
        
    }
    
    public int[] getClaimedColumns()
    {
        return this.claimedColumns;
    }
    
    public int getClaimedTotal()
    {
        return this.claimedTotal;
    }
}
