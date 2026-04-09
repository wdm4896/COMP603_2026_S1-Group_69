/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
public enum Colour {
    DEFAULT("\u001B[0m"),
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    CYAN("\u001B[36m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m");

    
    private String ansi;
    
    private Colour(String ansi)
    {
        this.ansi = ansi;
    }
    
    public String getAnsi()
    {
        return this.ansi;
    }
}
