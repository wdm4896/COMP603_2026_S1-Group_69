/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.*;
/**
 *
 * @author admin
 */
public class GameBoard extends Board {
    private final static int columnFirst = 2;
    private final static int columnLast = 12;
    private final static int boardWidth = columnLast - columnFirst + 1;
    private int[] columnSizes = new int[boardWidth]; // include both ends of the board
    
    @Override
    public void boardDraw(Queue<Player> players)
    {
        
    }
    
    public int[] getColumnSizes()
    {
        return this.columnSizes;
    }
    
    public static int getBoardWidth()
    {
        return boardWidth;
    }
}
