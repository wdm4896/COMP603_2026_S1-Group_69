/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author admin
 */
public class GameManager {
    private final List<Colour> coloursAvailable = new LinkedList<>(Arrays.asList(Colour.values()));
    public static GameRound roundCurrent;
    
    public void gameStart()
    {
        if (roundCurrent != null) { roundCurrent.dispose(); }
        roundCurrent = new GameRound();
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
        Game.getScoreBoard().scoresSaveFile();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    public void addPlayer(String name, String username, Colour colour)
    {
        if (username.equals("")) { username = null; }
        Game.getPlayers().add(new Player(name, username, colour));
        this.coloursAvailable.remove(colour);
    }
    
    public List<Colour> getColoursAvailable()
    {
        return this.coloursAvailable;
    }
}
