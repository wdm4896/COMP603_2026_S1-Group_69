/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author admin
 */
public class DiceCupGUI extends DiceCupUI {
    private final static int PANEL_WIDTH = Game.getScreenWidth() - Game.getScreenHeight();
    private final static int PANEL_HEIGHT = Game.getScreenHeight();
    private final static int DICE_SIZE = 120;
    private final static int DICE_FONT_SIZE = 50;
    private final static int DICE_MARGIN = 30;
    private final static int BUTTON_HEIGHT = 50;
    private final static int BUTTON_WIDTH = 150;
    private final static int SEPARATION_SIZE = 60;
    
    private final JLabel turnList;
    private final JButton[] diceSelection;
    private final JButton rollSubmit;
    private final JButton rollDice;
    private final JButton endTurn;
    
    public DiceCupGUI(DiceCup diceCup)
    {
        super(diceCup);
        
        // As this program is made for fullscreen, the size will be set
        // This may change in future development if we want to add scalable windows
        this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
        // Set Grid Layout
        this.setLayout(new GridBagLayout());
        
        // Player Label
        turnList = new JLabel("Loading Players...");
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 2;
        labelConstraints.anchor = GridBagConstraints.LINE_START;
        labelConstraints.insets = new Insets(0, 0, SEPARATION_SIZE, 0);
        this.add(turnList, labelConstraints);
        
        // Create Dice Buttons
        GridBagConstraints diceConstraints = new GridBagConstraints();
        diceSelection = new JButton[DiceCup.getDiceTotal()];
        for (int i = 0; i < diceSelection.length; i++)
        {
            diceSelection[i] = new JButton("0");
            diceSelection[i].setPreferredSize(new Dimension(DICE_SIZE, DICE_SIZE));
            diceSelection[i].setMaximumSize(new Dimension(DICE_SIZE, DICE_SIZE));
            diceSelection[i].setFont(new Font(diceSelection[i].getFont().getFontName(), Font.PLAIN, DICE_FONT_SIZE));
            
            diceConstraints.gridx = i % DiceCup.getDiceChosenMax(); // 0 1 0 1 0 1
            diceConstraints.gridy = i / DiceCup.getDiceChosenMax() + 1; // 0 0 1 1 2 2
            diceConstraints.insets = new Insets(DICE_MARGIN, DICE_MARGIN, DICE_MARGIN, DICE_MARGIN);
            
            this.add(diceSelection[i], diceConstraints);
        }
        
        // Create Interaction Buttons
        rollSubmit = new JButton("Submit Roll");
        rollSubmit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        rollSubmit.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        rollSubmit.setVisible(false);
        rollDice = new JButton("Roll Dice");
        rollDice.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        rollDice.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        endTurn = new JButton("End Turn");
        endTurn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        endTurn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = DiceCup.getDiceChosenMax() + 1;
        buttonConstraints.insets = new Insets(SEPARATION_SIZE, 0, 0, 0);
        this.add(rollDice, buttonConstraints);
        
        buttonConstraints.gridx = 1;
        this.add(endTurn, buttonConstraints);
        
        buttonConstraints.gridx = 0;
        buttonConstraints.gridwidth = 2;
        this.add(rollSubmit, buttonConstraints);
    }
    
    public void buttonsEnable()
    {
        rollDice.setEnabled(true);
        endTurn.setEnabled(true);
    }
    
    public void buttonsDisable()
    {
        rollDice.setEnabled(false);
        endTurn.setEnabled(false);
    }
    
    @Override
    public void displayChoices()
    {
        displayDice();
    }
    
    @Override
    public void displayDice()
    {
        int[] diceRoll = this.getDiceCup().getDiceRoll();
        
        for (int i = 0; i < diceRoll.length; i++)
        {
            this.diceSelection[i].setText(Integer.toString(diceRoll[i]));
        }
    }
    
    public void resetDice()
    {   
        for (int i = 0; i < DiceCup.getDiceTotal(); i++)
        {
            this.diceSelection[i].setText("0");
        }
    }
    
    public void displayPairings(List<Integer> dicePairings)
    {
        int[] diceRoll = this.getDiceCup().getDiceRoll();
        int dieRoll;
        int dieRollIndex;
        
        displayDice();
        
        Iterator iterDiceSelection = dicePairings.iterator();
        for (int i = 0; i < dicePairings.size(); i++) {
            dieRollIndex = (int) iterDiceSelection.next();
            dieRoll = diceRoll[dieRollIndex];
            switch (i / 2)
            {
                case 0 -> diceSelection[dieRollIndex].setText("(" + dieRoll + ")");
                case 1 -> diceSelection[dieRollIndex].setText("{" + dieRoll + "}");
                default -> diceSelection[dieRollIndex].setText("[" + dieRoll + "]");
            }
        }
    }
    
    @Override
    public void bust()
    {
        // Does nothing as figuring out how to make it work properly was too hard for either of us :/
        resetDice();
    }
    
    @Override
    public void askToRoll()
    {
        this.rollDice.setVisible(true);
        this.endTurn.setVisible(true);
        this.rollSubmit.setVisible(false);
        
        for (JButton diceButton : diceSelection)
        {
            diceButton.setEnabled(false);
        }
    }
    
    @Override
    public void askToSelect()
    {
        this.rollDice.setVisible(false);
        this.endTurn.setVisible(false);
        this.rollSubmit.setVisible(true);
        
        for (JButton diceButton : diceSelection)
        {
            diceButton.setEnabled(true);
        }
    }
    
    public void updateTurnList()
    {
        String label = "<html>";
        
        Iterator iterPlayers = Game.getPlayers().iterator();
        Player player = (Player) iterPlayers.next();

        label += "<h1>Current Player: <font color='" + player.getColour().name() + "'>" + player.getName() + "</font></h1>";
        label += "<ul>";
        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            label += "<li<font color='" + player.getColour().name() + "'>" + player.getName() + "</font></li>";
        }
        
        label += "</ul></html>";
        turnList.setText(label);
    }
    
    public void displayWinner(Player player)
    {
        String label = "<html><h1>Winner: <font color='" + player.getColour().name() 
                + "'>" + player.getName() + "</font></h1>";
        this.turnList.setText(label);
    }
    
    public void setRollSubmitEnabled(boolean enable)
    {
        this.rollSubmit.setEnabled(enable);
    }
    
    public JButton[] getDiceSelection()
    {
        return this.diceSelection;
    }
            
    public JButton getRollSubmit()
    {
        return this.rollSubmit;
    }
    
    public JButton getRollDice()
    {
        return this.rollDice;
    }
    
    public JButton getEndTurn()
    {
        return this.endTurn;
    }
}
