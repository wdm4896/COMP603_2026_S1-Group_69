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
    private int[] claimedColumns = new int[Game.getWinCondition()];
    
    public Player(String name, Colour colour)
    {
        super(name, colour);
        for (int i = 0; i < this.claimedColumns.length; i++)
        {
            claimedColumns[i] = -1;
        }
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
                    board.getColumnSizes()[i] == posCurrent[i] &&
                    board.getColumnClaimed(i) == false
            ) {
                this.claimedColumns[claimedTotal++] = i;
                board.setColumnClaimed(i, true);
            }
        }
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
    
    // For blocking out columns
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
            System.out.print("Would you like to roll? [Y/n]:\n" + Game.userPrompt);
            input = kbinput.nextLine().strip();
            if (input.toLowerCase().equals("n"))
            {
                savePos(board);
                this.isMoving = false;
                return;
            } else if (!(input.toLowerCase().equals("y") || input.toLowerCase().equals("n"))) // invalid input
            {
                System.out.println("Invalid input. Please respond with either 'y' or 'n'...");
            }
        } while (!input.toLowerCase().equals("y"));
        
        
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
                0 <= posMoving[targetIndex] &&
                posMoving[targetIndex] < board.getColumnSizes()[targetIndex]
        ) {
            posMoving[targetIndex]++;
        }
        
    }
    
    public int[] getClaimedColumns()
    {
        return this.claimedColumns;
    }
}
