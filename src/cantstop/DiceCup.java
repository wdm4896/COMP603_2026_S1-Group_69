/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
public class DiceCup {
    private final int totalDice = 4;
    
    private final Dice[] dice = new Dice[totalDice];
    
    public DiceCup()
    {
        for (int i = 0; i < totalDice; i++)
        { 
            dice[i] = new Dice(1, 6);
        }
    }
    
    public int[] rollDice()
    {
        int[] diceValues = new int[totalDice];
        
        for (int i = 0; i < totalDice; i++)
        {
            diceValues[i] = dice[i].roll();
        }
        
        return diceValues;
    }
}
