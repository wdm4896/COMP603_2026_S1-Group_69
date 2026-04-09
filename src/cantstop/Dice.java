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
    private final int valueMin = 1;
    private final int valueMax = 6;
    private Random r = new Random(); 
    
    public int roll()
    {
     return r.nextInt(valueMin, valueMax + 1);   
    }
}
