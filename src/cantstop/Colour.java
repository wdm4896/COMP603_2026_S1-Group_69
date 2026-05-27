/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cantstop;

import java.awt.Color;

/**
 *
 * @author admin
 */
public enum Colour {
    DEFAULT("\u001B[0m", Color.WHITE),
    RED("\u001B[31m", Color.ORANGE), // When implementing the GUI, red all of a sudden became a rubbish choice of colour
    YELLOW("\u001B[33m", Color.YELLOW),
    GREEN("\u001B[32m", Color.GREEN),
    CYAN("\u001B[36m", Color.CYAN),
    BLUE("\u001B[34m", Color.BLUE),
    PURPLE("\u001B[35m", Color.MAGENTA);
    
    private final String ansi;
    private final Color color;
    
    private Colour(String ansi, Color color)
    {
        this.ansi = ansi;
        this.color = color;
    }
    
    public String font()
    {
        return this.ansi;
    }
    
    public Color color()
    {
        return this.color;
    }
}
