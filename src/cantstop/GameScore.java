/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.Iterator;
import java.util.LinkedList;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 *
 * @author admin
 */
public class GameScore implements Score {
    
    @Override
    public void scoresDisplayCLI()
    {
        List<Player> playersScores = new LinkedList<>(Game.getPlayers());
        int playersTotal = playersScores.size();
        
        Iterator iterPlayers;
        Player pHighest;
        Player pCurrent;
        
        System.out.println("\nCurrent scores:");
        
        // Order scores from largest to smallest
        for (int i = 0; i < playersTotal; i++)
        {
            iterPlayers = playersScores.iterator();
            pHighest = (Player) iterPlayers.next();
            
            while (iterPlayers.hasNext())
            {
                pCurrent = (Player) iterPlayers.next();
                if (pCurrent.getWinsTotal() > pHighest.getWinsTotal())
                {
                    pHighest = pCurrent;
                }
            }
            
            System.out.println(pHighest.getColour().font() + pHighest.getName() + Colour.DEFAULT.font() + ": " + pHighest.getWinsTotal());
            playersScores.remove(pHighest);
        }
    }
    
    @Override
    public String scoresDisplayGUI()
    {
        String label = "<html><ul>";
        
        List<Player> playersScores = new LinkedList<>(Game.getPlayers());
        int playersTotal = playersScores.size();
        
        Iterator iterPlayers;
        Player pHighest;
        Player pCurrent;
        
        // Order scores from largest to smallest
        for (int i = 0; i < playersTotal; i++)
        {
            iterPlayers = playersScores.iterator();
            pHighest = (Player) iterPlayers.next();
            
            while (iterPlayers.hasNext())
            {
                pCurrent = (Player) iterPlayers.next();
                if (pCurrent.getWinsTotal() > pHighest.getWinsTotal())
                {
                    pHighest = pCurrent;
                }
            }
            
            label += "<li><font size=5><font color='" + pHighest.getColour().name() + "'>" 
                    + pHighest.getName() + "</font>" + ": " + pHighest.getWinsTotal() + "</font></li>";
            playersScores.remove(pHighest);
        }
        
        label += "</ul></html>";
        
        return label;
    }
    
    @Override
    public void scoresSave()
    {
        PrintWriter pw;
        
        List<Player> playersScores = new LinkedList<>(Game.getPlayers());
        int playersTotal = playersScores.size();
        
        Iterator iterPlayers;
        Player playerHighest;
        Player playerCurrent;
        try
        {
            // Create and name the file
            var dateTimeFormat = DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss");
            String fileName = "Scores_" +  LocalDateTime.now().format(dateTimeFormat) + ".txt";
            pw = new PrintWriter(new FileOutputStream(fileName));
            
            // Order scores from largest to smallest and store them
            pw.println("Final scores:");
            for (int i = 0; i < playersTotal; i++)
            {
                iterPlayers = playersScores.iterator();
                playerHighest = (Player) iterPlayers.next();

                while (iterPlayers.hasNext())
                {
                    playerCurrent = (Player) iterPlayers.next();
                    if (playerCurrent.getWinsTotal() > playerHighest.getWinsTotal())
                    {
                        playerHighest = playerCurrent;
                    }
                }

                pw.println(playerHighest.getName() + " (" + playerHighest.getColour().name() + "): " + playerHighest.getWinsTotal());
                playersScores.remove(playerHighest);
            }
            
            pw.close();
        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
