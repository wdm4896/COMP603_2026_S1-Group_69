/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author admin
 */
public class Game {
    public final static boolean USE_GUI = true;
    public final static int PLAYERS_MAX = 4;
    private final static int WIN_CONDITION = 3;
    public final static String USER_PROMPT = "> ";
    private final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private final static Queue<Player> players = new LinkedList<>();
    private final GameScore scoreBoard = new GameScore();
    private final GameManager gameManager;
    public final GameManagerUI gameManagerUI;
    
    public Game()
    {
        gameManager = new GameManager(scoreBoard);
        
        if (USE_GUI)
        {
            gameManagerUI = new GameManagerGUI(gameManager);
        }
        else
        {
            gameManagerUI = new GameManagerCLI(gameManager);
        }
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.gameManagerUI.sessionPrepare();
    }
    
    public static Queue<Player> getPlayers()
    {
        return players;
    }
        
    public static int getPlayersMax()
    {
        return PLAYERS_MAX;
    }
        
    public static int getWinCondition()
    {
        return WIN_CONDITION;
    }
    
    public static int getScreenWidth()
    {
        return (int) SCREEN_SIZE.getWidth();
    }
    
    public static int getScreenHeight()
    {
        return (int) SCREEN_SIZE.getHeight();
    }
}
