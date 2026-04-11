/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author admin
 */
public class GameScore extends Score implements ScoreSave {
    @Override
    public void scoresDisplay(Queue<Player> players)
    {
        players = new LinkedList<>(players);
        int playersTotal = players.size();
        
        Iterator iterPlayers;
        Player pHighest;
        Player pCurrent;
        
        System.out.println("\nCurrent scores:");
        
        // Order scores from largest to smallest
        for (int i = 0; i < playersTotal; i++)
        {
            iterPlayers = players.iterator();
            pHighest = (Player) iterPlayers.next();
            
            while (iterPlayers.hasNext())
            {
                pCurrent = (Player) iterPlayers.next();
                if (pCurrent.getWinsTotal() > pHighest.getWinsTotal())
                {
                    pHighest = pCurrent;
                }
            }
            
            System.out.println(pHighest.getColour().getAnsi() + pHighest.getName() + Colour.DEFAULT.getAnsi() + ": " + pHighest.getWinsTotal());
            players.remove(pHighest);
        }
    }
    
    @Override
    public void scoresSave(Queue<Player> players)
    {
        PrintWriter pw;
        
        players = new LinkedList<Player>(players);
        int playersTotal = players.size();
        
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
                iterPlayers = players.iterator();
                playerHighest = (Player) iterPlayers.next();

                while (iterPlayers.hasNext())
                {
                    playerCurrent = (Player) iterPlayers.next();
                    if (playerCurrent.getWinsTotal() > playerHighest.getWinsTotal())
                    {
                        playerHighest = playerCurrent;
                    }
                }

                pw.println(playerHighest.getName() + ": " + playerHighest.getWinsTotal());
                players.remove(playerHighest);
            }
            
            pw.close();
        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
