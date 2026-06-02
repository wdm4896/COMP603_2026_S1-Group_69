/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cantstop;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class GameManagerGUI extends GameManagerUI {
    private final static int FRAME_WIDTH_START = 700;
    private final static int FRAME_HEIGHT_START = 700;
    private final static int FRAME_WIDTH_END = 500;
    private final static int FRAME_HEIGHT_END = 500;
    private JFrame gameManagementFrame;
    private GamePanelStart panelStart;
    private GamePanelEnd panelEnd;
    
    public GameManagerGUI(GameManager gameManager)
    {
        super(gameManager);
    }
    
    @Override
    public void gameStart()
    {
        synchronized(Game.USER_PROMPT)
        {
            Game.USER_PROMPT.notify();
        }
    }
    
    @Override
    public void gameEnd()
    {
        panelEnd = new GamePanelEnd(this.getGameManager().getScoreBoard());
        this.gameManagementFrame = new JFrame("Save Scores");
        this.gameManagementFrame.add(this.panelEnd);
        this.gameManagementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameManagementFrame.setSize(FRAME_WIDTH_END, FRAME_HEIGHT_END);
        this.gameManagementFrame.setLocationRelativeTo(null);
        this.gameManagementFrame.setVisible(true);
        
        this.panelEnd.getButtonYes().addActionListener((ActionEvent e) -> {
            this.getGameManager().saveScores();
            this.panelEnd.end();
        });
        
        this.panelEnd.getButtonNo().addActionListener((ActionEvent e) -> {
            this.panelEnd.end();
        });
    }
    
    @Override
    public void addPlayer()
    {
        String name = panelStart.getFieldName().getText();
        String username = panelStart.getFieldUsername().getText();
        Colour colour = (Colour) panelStart.getFieldColour().getSelectedItem();
        
        // Validate input
        if (name.equals("")) { return; }
        
        // Submit input
        this.getGameManager().addPlayer(name, username, colour);
        
        switch (Game.getPlayers().size())
        {
            case 1 -> this.panelStart.getStartGame().setEnabled(true);
            case Game.PLAYERS_MAX -> this.panelStart.getFieldSubmit().setEnabled(false);
        }
    }
    
    @Override
    public void sessionPrepare() {
        this.panelStart = new GamePanelStart(this.getGameManager().getColoursAvailable());
        this.gameManagementFrame = new JFrame("Add Players");
        this.gameManagementFrame.add(panelStart);
        this.gameManagementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameManagementFrame.setSize(FRAME_WIDTH_START, FRAME_HEIGHT_START);
        this.gameManagementFrame.setLocationRelativeTo(null);
        this.gameManagementFrame.setVisible(true);
        
        this.panelStart.getFieldSubmit().addActionListener((ActionEvent e) -> {
            addPlayer();
            this.panelStart.updateLabel();
            this.panelStart.updateColours();
        });
        
        this.panelStart.getStartGame().addActionListener((ActionEvent e) -> {
            this.gameManagementFrame.setVisible(false);
            this.gameManagementFrame.remove(panelStart);           
            gameStart();
        });
        
        GameDialogPlayAgain playAgain = new GameDialogPlayAgain(gameManagementFrame, this.getGameManager().getScoreBoard());
        playAgain.setSize(300, 250);
        playAgain.setLocationRelativeTo(null);
        
        playAgain.getButtonYes().addActionListener((ActionEvent e) -> {
            this.setPlay(true);
            gameStart();
            playAgain.setVisible(false);
        });
        
        playAgain.getButtonNo().addActionListener((ActionEvent e) -> {
            this.setPlay(false);
            playAgain.setVisible(false);
            gameEnd();
        });
        
        try { // Workaround as running through ActionEvent puts gameStart on EDT - which breaks the game
            synchronized(Game.USER_PROMPT) {
                Game.USER_PROMPT.wait();
            }
        } catch (InterruptedException e) {
            // Assume the round intentionally ended
        }
        
        do 
        {
            this.getGameManager().gameStart();
            playAgain.askDialogue();
            try {
            synchronized(Game.USER_PROMPT) {
                Game.USER_PROMPT.wait();
            }
            } catch (InterruptedException e) {
                // Assume the round intentionally ended
            }
        } while (this.getPlay());
    }
}
