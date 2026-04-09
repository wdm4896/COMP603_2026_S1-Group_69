/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Random;
/**
 *
 * @author admin
 */
public class Dice {
    private final int valueMin;
    private final int valueMax;
    private final Random r = new Random();
    
    public Dice(int valueMin, int valueMax)
    {
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }
    
    public int roll()
    {
     return r.nextInt(valueMin, valueMax + 1);   
    }
}
