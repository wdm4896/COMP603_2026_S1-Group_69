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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 *
 * @author admin
 */
public class GameScore implements Score {
    private static GameScore instance; // Singleton
    private final DBManager dbManager;
    private final Connection conn;
    
    public static GameScore getInstance()
    {
        if (instance == null)
        {
            instance = new GameScore();
        }
        
        return instance;
    }
    
    private GameScore()
    {
        this.dbManager = new DBManager();
        this.conn = this.dbManager.getConnection();
    }
    
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
    public void scoresDisplayCLI_DB()
    {
        try {
            Statement statement = this.conn.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM APP.SCORES ORDER BY SCORE DESC FETCH NEXT 10 ROWS ONLY"
            );
            
            String username;
            int score;
            int position = 1;
            while (rs.next())
            {
                username = rs.getString("USERNAME");
                score = rs.getInt("SCORE");
                System.out.println(position++ + ") " + username + ": "
                        + score + " win" + ((score == 1) ? "" : "s")
                );
            }
        } catch (SQLException ex) {
            System.getLogger(GameScore.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    @Override
    public String scoresDisplayGUI_DB()
    {
        String label = "<html> <font size=4>";
        
        try {
            Statement statement = this.conn.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM APP.SCORES ORDER BY SCORE DESC FETCH NEXT 10 ROWS ONLY"
            );
            
            String username;
            int score;
            int position = 1;
            while (rs.next())
            {
                username = rs.getString("USERNAME");
                score = rs.getInt("SCORE");
                label += position++ + ") " + username + ": "
                        + score + " win" + ((score == 1) ? "" : "s") + "<br>";
            }
        } catch (SQLException ex) {
            System.getLogger(GameScore.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        label += "</font></html>";
        return label;
    }
    
    @Override
    public void scoresSaveFile()
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
    
    @Override
    public void scoresSaveDB()
    {
        Iterator iterPlayers = Game.getPlayers().iterator();
        Player player;
        String username;
        int winsTotal;
        
        try {
            Statement statement = this.conn.createStatement();
            ResultSet rs;
            
            while (iterPlayers.hasNext())
            {
                player = (Player) iterPlayers.next();
                username = player.getUsername();
                winsTotal = player.getWinsTotal();
                
                // Skip to next loop if player has no username
                if (username == null || winsTotal <= 0) { continue; }
                
                // Add score to database
                rs = statement.executeQuery(
                        "SELECT SCORE FROM APP.SCORES WHERE USERNAME = '" + username + "'"
                );
                
                if (rs.next()) // If username is in database
                {
                    winsTotal += rs.getInt("SCORE");
                    statement.executeUpdate(
                        "UPDATE APP.SCORES SET SCORE = " + winsTotal 
                        + " WHERE USERNAME = '" + username + "'"
                    );
                }
                else // If username isn't in database yet
                {
                    statement.executeUpdate(
                        "INSERT INTO APP.SCORES VALUES ('" + username 
                        + "', " + winsTotal + ")"
                    );
                }
            }
        } catch (SQLException ex) {
            System.getLogger(GameScore.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
