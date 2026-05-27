/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Queue;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
abstract class GameBoardUI extends JPanel {
    private final GameBoard board;
    private final Queue<Player> players;
    
    public GameBoardUI(GameBoard board, Queue<Player> players)
    {
        this.board = board;
        this.players = players;
    }
    
    public abstract void drawBoard();
    
    public GameBoard getBoard()
    {
        return this.board;
    }
    
    public Queue<Player> getPlayers()
    {
        return this.players;
    }
}
