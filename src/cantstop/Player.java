/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
public class Player extends Person implements Turn {
    private int[] posCurrent = new int[GameBoard.getBoardWidth()];
    private int[] posMoving = new int[GameBoard.getBoardWidth()];
    private boolean isMoving = false;
    private int claimedTotal = 0;
    private int[] claimedColumns = new int[Game.getWinCondition()];
    
    public Player(String name, Colour colour)
    {
        super(name, colour);
    }
    
    @Override
    public int[] getPosCurrent()
    {
        return this.posCurrent;
    }
    
    @Override
    public void savePos()
    {
        
    }
    
    @Override
    public boolean isMoving()
    {
        return this.isMoving;
    }
    
    @Override
    public void setMoving(boolean isMoving)
    {
        this.isMoving = isMoving;
    }
    
    @Override
    public int[] getPosMoving()
    {
        return this.posMoving;
    }
    
    @Override
    public void haveTurn()
    {
        
    }
}
