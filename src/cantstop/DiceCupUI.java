/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import javax.swing.JPanel;

/**
 *
 * @author admin
 */
abstract class DiceCupUI extends JPanel {
    private final DiceCup diceCup;
    
    public DiceCupUI(DiceCup diceCup)
    {
        this.diceCup = diceCup;
    }
    
    public abstract void askToRoll();
    public abstract void askToSelect();
    public abstract void displayChoices();
    public abstract void displayDice();
    public abstract void bust();
    
    public DiceCup getDiceCup()
    {
        return this.diceCup;
    }
}
