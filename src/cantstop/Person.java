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
    private int winsTotal;
    
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
    
    public Colour getColour()
    {
        return this.colour;
    }
    
    public void setColour()
    {
        this.colour = colour;
    }
}
