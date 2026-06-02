/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
abstract class Person {
    private final String name;
    private final String username;
    private final Colour colour;
    private int winsTotal = 0;
    
    public Person(String name, String username, Colour colour)
    {
        this.name = name;
        this.username = username;
        this.colour = colour;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public int getWinsTotal()
    {
        return this.winsTotal;
    }
    
    public void hasWon()
    {
        this.winsTotal++;
    }
    
    public Colour getColour()
    {
        return this.colour;
    }
}
