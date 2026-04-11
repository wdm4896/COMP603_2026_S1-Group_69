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
    private String name;
    private Colour colour;
    private int winsTotal = 0;
    
    public Person(String name, Colour colour)
    {
        this.name = name;
        this.colour = colour;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
