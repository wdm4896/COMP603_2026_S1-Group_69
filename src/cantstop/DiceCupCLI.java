/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author admin
 */
public class DiceCupCLI extends DiceCupUI {
    
    public DiceCupCLI(DiceCup diceCup)
    {
        super(diceCup);
    }
    
    @Override
    public void displayChoices()
    {
        Iterator iterChoice = this.getDiceCup().getDiceChoicesFiltered().iterator();
        Iterator iterGroup;
        Integer[] pairing;
        
        int optionIndex = 1;
        while (iterChoice.hasNext())
        {
            System.out.print(optionIndex++ + ") ");
            iterGroup = ((List<Integer[]>) iterChoice.next()).iterator();
            
            // Prints each choice
            while (iterGroup.hasNext())
            {
                pairing = (Integer[]) iterGroup.next();
                System.out.print("(" + pairing[0]);
                for (int i = 1; i < pairing.length; i++)
                {
                    System.out.print(", " + pairing[i]);
                }
                System.out.print(") ");
            }
            System.out.println("");
        }
    }
    
    @Override
    public void displayDice(int[] diceRoll)
    {
        System.out.print("Dice Roll: " + diceRoll[0]);
        for (int dice = 1; dice < diceRoll.length; dice++)
        {
            System.out.print(", " + diceRoll[dice]);
        }
        System.out.println("");
    }
    
    @Override
    public void bust()
    {
        System.out.println("\nBUST!");
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    /*
     * These look like pathetically small methods, however they exist to fulfil an abstract made for DiceCupGUI
    */
    @Override
    public void askToRoll()
    {
        System.out.print("\nWould you like to roll? [Y/n]:\n" + Game.USER_PROMPT);
    }
    
    @Override
    public void askToSelect()
    {
        System.out.println("Which dice do you wish to select?\n" + Game.USER_PROMPT);
    }
}
