/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author admin
 */
public class GamePanelEnd extends JPanel {
    private final static int FONT_SIZE = 16;
    private final JButton saveYes;
    private final JButton saveNo;
    private final JLabel dialogue;
    private final JLabel playerScores;
    private final GameScore scoreBoard;
    
    public GamePanelEnd(GameScore scoreBoard)
    {
        this.scoreBoard = scoreBoard;
        
        this.setLayout(new BorderLayout());
        
        this.dialogue = new JLabel("Would you like to save your scores?");
        this.dialogue.setFont(new Font(this.dialogue.getFont().getFontName(), Font.PLAIN, FONT_SIZE));
        this.dialogue.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.dialogue.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(this.dialogue, BorderLayout.NORTH);
        
        this.playerScores = new JLabel();
        this.playerScores.setHorizontalAlignment(SwingConstants.CENTER);
        this.playerScores.setVerticalAlignment(SwingConstants.NORTH);
        displayScores();
        this.add(this.playerScores, BorderLayout.CENTER);
        
        JPanel saveOptions = new JPanel();
        this.saveYes = new JButton("Yes");
        this.saveNo = new JButton("No");
        saveOptions.add(this.saveYes);
        saveOptions.add(this.saveNo);
        this.add(saveOptions, BorderLayout.SOUTH);
    }
    
    public final void displayScores()
    {
        String label = this.scoreBoard.scoresDisplayGUI();
        this.playerScores.setText(label);
    }
    
    public JButton getButtonYes()
    {
        return this.saveYes;
    }
    
    public JButton getButtonNo()
    {
        return this.saveNo;
    }
    
    public void end()
    {
        this.dialogue.setText("Thanks for playing!");
        this.saveYes.setVisible(false);
        this.saveNo.setVisible(false);
    }
}
