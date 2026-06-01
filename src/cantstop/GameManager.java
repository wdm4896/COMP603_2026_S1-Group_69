/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author admin
 */
public class GameManager {
    private final List<Colour> coloursAvailable = new LinkedList<>(Arrays.asList(Colour.values()));
    private final Queue<Player> players;
    private final GameScore scoreBoard;
    public static GameRound roundCurrent;
    
    public GameManager(Queue<Player> players, GameScore scoreBoard)
    {
        this.players = players;
        this.scoreBoard = scoreBoard;
    }
    
    public void gameStart()
    {
        if (roundCurrent != null) { roundCurrent.dispose(); }
        roundCurrent = new GameRound(players);
        roundCurrent.play();
        
        try {
            synchronized(Game.USER_PROMPT) {
                while(!GameManager.roundCurrent.getWinConditionMet()) {
                    Game.USER_PROMPT.wait();
                }
            }
        } catch (InterruptedException e) {
            // Assume the round intentionally ended
        }        
    }
    
    public void saveScores()
    {
        this.scoreBoard.scoresSave();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    public void addPlayer(String name, Colour colour)
    {
        this.players.add(new Player(name, colour));
        this.coloursAvailable.remove(colour);
    }
    
    public Queue<Player> getPlayers()
    {
        return this.players;
    }
    
    public List<Colour> getColoursAvailable()
    {
        return this.coloursAvailable;
    }
    
    public GameScore getScoreBoard()
    {
        return this.scoreBoard;
    }
}
