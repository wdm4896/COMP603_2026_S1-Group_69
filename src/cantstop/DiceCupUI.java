/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
abstract class DiceCupUI {
    private final DiceCup diceCup;
    
    public DiceCupUI(DiceCup diceCup)
    {
        this.diceCup = diceCup;
    }
    
    public abstract void askToRoll();
    public abstract void askToSelect();
    public abstract void displayChoices();
    public abstract void displayDice(int[] diceRoll);
    public abstract void bust();
    
    public DiceCup getDiceCup()
    {
        return this.diceCup;
    }
}
