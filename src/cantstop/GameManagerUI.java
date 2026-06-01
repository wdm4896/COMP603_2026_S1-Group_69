/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
abstract class GameManagerUI {
    private final GameManager gameManager;
    private boolean play = true;
    
    public GameManagerUI(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }
    
    abstract void gameStart();
    abstract void gameEnd();
    abstract void addPlayer();
    abstract void sessionPrepare();
    
    public GameManager getGameManager()
    {
        return this.gameManager;
    }
    
    public void setPlay(boolean play)
    {
        this.play = play;
    }
    
    public boolean getPlay()
    {
        return this.play;
    }
}
