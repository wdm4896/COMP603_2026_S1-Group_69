/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author admin
 */
public class GameDialogPlayAgain extends JDialog {
    private final static int FONT_SIZE = 16;
    private final GameScore scoreBoard;
    private final JButton playYes;
    private final JButton playNo;
    private final JLabel playerScores;
    
    public GameDialogPlayAgain(JFrame frame, GameScore scoreBoard)
    {
        super(frame, "Play Again?");
        this.scoreBoard = scoreBoard;
        
        JLabel playAgainDialogue = new JLabel("Would you like to play again?");
        playAgainDialogue.setFont(new Font(playAgainDialogue.getFont().getFontName(), Font.PLAIN, FONT_SIZE));
        playAgainDialogue.setBorder(new EmptyBorder(10, 10, 10, 10));
        playAgainDialogue.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(playAgainDialogue, BorderLayout.NORTH);
        
        this.playerScores = new JLabel();
        this.add(this.playerScores, BorderLayout.CENTER);
        
        JPanel playAgainOptions = new JPanel();
        playYes = new JButton("Yes");
        playNo = new JButton("No");
        playAgainOptions.add(playYes);
        playAgainOptions.add(playNo);
        this.add(playAgainOptions, BorderLayout.SOUTH);
    }
    
    public JButton getButtonYes()
    {
        return this.playYes;
    }
    
    public JButton getButtonNo()
    {
        return this.playNo;
    }
    
    public void askDialogue()
    {
        this.playerScores.setText(scoreBoard.scoresDisplayGUI());
        this.setVisible(true);
    }
}
