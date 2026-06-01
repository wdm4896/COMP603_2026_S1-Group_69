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
abstract class GameBoardUI extends JPanel {
    private final GameBoard board;
    
    public GameBoardUI(GameBoard board)
    {
        this.board = board;
    }
    
    public abstract void drawBoard();
    
    public GameBoard getBoard()
    {
        return this.board;
    }
}
