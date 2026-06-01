/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author admin
 */
public class GamePanelStart extends JPanel {
    private final static int FONT_SIZE = 24;
    private final List<Colour> coloursAvailable;
    private final JLabel playersDisplay = new JLabel("Loading Players...");
    private final JTextField fieldName = new JTextField("", 10);
    private final JComboBox<Colour> fieldColour = new JComboBox<>();
    private final JButton fieldSubmit = new JButton("Add Player");
    private final JButton startGame = new JButton("Start Game");
    
    public GamePanelStart(List<Colour> coloursAvailable)
    {
        this.coloursAvailable = coloursAvailable;
        this.setLayout(new BorderLayout());
        
        
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.PAGE_AXIS));
        
        JPanel playerPanel = new JPanel();
        playerPanel.add(new JLabel("Name: "));
        playerPanel.add(fieldName);
        
        updateColours();
        playerPanel.add(new JLabel("Colour: "));
        playerPanel.add(fieldColour);
        
        playerPanel.add(fieldSubmit);
        this.add(playerPanel, BorderLayout.NORTH);
        
        
        JLabel playersHeader = new JLabel("Players:");
        playersHeader.setFont(new Font(playersHeader.getFont().getFontName(), Font.PLAIN, FONT_SIZE));
        playersHeader.setAlignmentX(CENTER_ALIGNMENT);
        
        this.playersDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        this.playersDisplay.setAlignmentX(CENTER_ALIGNMENT);
        playersPanel.add(playersHeader);
        playersPanel.add(playersDisplay);
        this.add(playersPanel, BorderLayout.CENTER);
        updateLabel();
        
        
        this.startGame.setPreferredSize(new Dimension(150, 50)); // Only applies the width
        this.startGame.setEnabled(false);
        this.add(this.startGame, BorderLayout.SOUTH);
    }
    
    public final void updateColours()
    {
        this.fieldColour.removeAllItems();
        
        Iterator iterColoursAvailable = this.coloursAvailable.iterator();
        iterColoursAvailable.next(); // Skip first one
        while (iterColoursAvailable.hasNext())
        {
            this.fieldColour.addItem((Colour) iterColoursAvailable.next());
        }
    }
    
    public final void updateLabel()
    {
        String label = "<html><ul>";
        
        Iterator iterPlayers = Game.getPlayers().iterator();
        
        Player player;

        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            label += "<li><font size=5><font color='" + player.getColour().name() + "'>" + player.getName() + "</font></font></li>";
        }
        
        label += "</ul></html>";
        
        this.playersDisplay.setText(label);
    }
    
    public JTextField getFieldName()
    {
        return this.fieldName;
    }
    
    public JComboBox getFieldColour()
    {
        return this.fieldColour;
    }
    
    public JButton getFieldSubmit()
    {
        return this.fieldSubmit;
    }
    
    public JButton getStartGame()
    {
        return this.startGame;
    }
}
