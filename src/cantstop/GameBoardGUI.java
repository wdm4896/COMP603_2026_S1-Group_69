/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.Stack;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class GameBoardGUI extends GameBoardUI {
    private final static int SCREEN_PADDING = 70;
    private final static int PANEL_WIDTH = Game.getScreenHeight() - SCREEN_PADDING;
    private final static int PANEL_HEIGHT = Game.getScreenHeight() - SCREEN_PADDING;
    private final static int BOARD_WIDTH = GameBoard.getBoardWidth();
    private final static int BOARD_HEIGHT = GameBoard.getLengthMax() + 2; // +2 to account for text
    
    private final static Image BG_IMAGE = new ImageIcon("./resources/board_background.png").getImage().getScaledInstance(PANEL_WIDTH, PANEL_HEIGHT, Image.SCALE_SMOOTH);
    private final static String[] LOGO_TEXT = {"C", "A", "N", "T", "", "", "", "S", "T", "O", "P"}; // Honestly just for a bit of fun
    private final int[] columnSizes = this.getBoard().getColumnSizes();
    
    private final static int PIECE_SIZE = 25;
    private final static Color COLOUR_EMPTY = Color.PINK;
    private final static Color COLOUR_MOVING = Color.WHITE;
    
    public GameBoardGUI(GameBoard board)
    {
        super(board);
        
        // As this program is made for fullscreen, the size will be set
        // This may change in future development if we want to add scalable windows
        this.setMinimumSize(new Dimension(PANEL_WIDTH + SCREEN_PADDING, PANEL_WIDTH + SCREEN_PADDING));
        this.setPreferredSize(new Dimension(PANEL_WIDTH + SCREEN_PADDING, PANEL_WIDTH + SCREEN_PADDING));
        this.setMaximumSize(new Dimension(PANEL_WIDTH + SCREEN_PADDING, PANEL_WIDTH + SCREEN_PADDING));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG_IMAGE, (SCREEN_PADDING - PIECE_SIZE) / 2, (SCREEN_PADDING - PIECE_SIZE) / 2, this);
        drawBoard(g);
              
        Iterator iterPlayers = Game.getPlayers().iterator();
        Player player;
        Player playerMoving = null;
        Stack<Player> playersNotMoving = new Stack<>();
        
        // Print player current positions
        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            if (player.isMoving()) {
                playerMoving = player;
            }
            playersNotMoving.push(player);
        }
        
        drawPosPlayers(g, playersNotMoving);
        
        // Print moving pieces for moving player
        if (playerMoving != null && 
                playerMoving.getMovingPiecesMax() - playerMoving.getMovingPiecesAvailable() > 0
        )
        {
            int[] movingPieces = playerMoving.getMovingPieces();

            drawPosMoving(g, playerMoving.getPosMoving(), movingPieces);
        }
    }
    
    private Color getClaimedColour(int columnClaimed)
    {
        Iterator iterPlayers = Game.getPlayers().iterator();
        Player player;
        
        while (iterPlayers.hasNext())
        {
            player = (Player) iterPlayers.next();
            for (int playerClaimed : player.getClaimedColumns())
            {
                if (playerClaimed == columnClaimed)
                {
                    return player.getColour().color();
                }
            }
        }
        
        return Colour.DEFAULT.color();
    }
    
    private void drawBoard(Graphics g)
    {
        int[] columnValues = this.getBoard().getColumnValues();
        
        int posX;
        int posY;
        float posYShift;
        
        g.setColor(Colour.DEFAULT.color());
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, PIECE_SIZE));
        for (int i = 0; i < BOARD_WIDTH; i++)
        {
            // Draw Text
            if (BOARD_WIDTH == 11 && BOARD_HEIGHT == 13 + 2)
            {
                g.setColor(Colour.DEFAULT.color());
                posYShift = ((float) (BOARD_HEIGHT - this.columnSizes[i]) / 2) * (PANEL_HEIGHT / (BOARD_HEIGHT));
                posX = i * (PANEL_WIDTH / BOARD_WIDTH) + ((PIECE_SIZE + SCREEN_PADDING) / 2);
                posY = (BOARD_HEIGHT + 2) * (PANEL_HEIGHT / BOARD_HEIGHT) + (SCREEN_PADDING / 2) - (int) posYShift - PIECE_SIZE;
                g.drawString(LOGO_TEXT[i], posX, posY);
            }
            
            // Colour claimed column to the colour of the player who claimed it
            if (this.getBoard().getColumnClaimed(i))
            {
                g.setColor(getClaimedColour(i));
            }
            else
            {
                g.setColor(COLOUR_EMPTY);
            }
            
            // Draw Spaces
            for (int j = 0; j < this.columnSizes[i]; j++)
            {
                posYShift = ((float) (BOARD_HEIGHT - this.columnSizes[i]) / 2) * (PANEL_HEIGHT / (BOARD_HEIGHT));
                posX = i * (PANEL_WIDTH / BOARD_WIDTH) + ((PIECE_SIZE + SCREEN_PADDING) / 2);
                posY = (BOARD_HEIGHT - j - 1) * (PANEL_HEIGHT / BOARD_HEIGHT) + (SCREEN_PADDING / 2) - (int) posYShift;
                g.fillRect(posX, posY, PIECE_SIZE, PIECE_SIZE);
            }
            
            // Draw Column Sizes
            if (!this.getBoard().getColumnClaimed(i))
            {
                g.setColor(Colour.DEFAULT.color());
            }
            posYShift = ((float) (BOARD_HEIGHT - this.columnSizes[i]) / 2) * (PANEL_HEIGHT / (BOARD_HEIGHT));
            posX = i * (PANEL_WIDTH / BOARD_WIDTH) + ((PIECE_SIZE + SCREEN_PADDING) / 2);
            posY = (BOARD_HEIGHT - this.columnSizes[i] - 1) * (PANEL_HEIGHT / BOARD_HEIGHT) + (SCREEN_PADDING / 2) - (int) posYShift + PIECE_SIZE;
            g.drawString(Integer.toString(columnValues[i]), posX, posY);
        }
    }
    
    private void drawPosPlayers(Graphics g, Stack<Player> players)
    {
        Player player;
        
        while (!players.isEmpty())
        {
            player = players.pop();
            g.setColor(player.getColour().color());
            drawPosPlayer(g, player.getPosCurrent(), player.getColour().color());
        }
    }
    
    private void drawPosPlayer(Graphics g, int[] playerPos, Color color)
    {
        int posX;
        int posY;
        float posYShift;
        
        g.setColor(color);
        for (int i = 0; i < BOARD_WIDTH; i++)
        {
            if (this.getBoard().getColumnClaimed(i)) { continue; }
            // Draw Player Positions
            for (int j = 0; j < this.columnSizes[i]; j++)
            {
                if (j != playerPos[i] - 1) { continue; }

                posYShift = ((float) (BOARD_HEIGHT - this.columnSizes[i]) / 2) * (PANEL_HEIGHT / (BOARD_HEIGHT));
                posX = i * (PANEL_WIDTH / BOARD_WIDTH) + ((PIECE_SIZE + SCREEN_PADDING) / 2);
                posY = (BOARD_HEIGHT - j - 1) * (PANEL_HEIGHT / BOARD_HEIGHT) + (SCREEN_PADDING / 2) - (int) posYShift;
                g.fillRect(posX, posY, PIECE_SIZE, PIECE_SIZE);
            }
        }
    }
    
    private void drawPosMoving(Graphics g, int[] movingPos, int[] movingPieces)
    {
        int posX;
        int posY;
        float posYShift;
        
        g.setColor(COLOUR_MOVING);
        for (int i : movingPieces)
        {
            // Ensure all values are actual moving piece values
            i = i - GameBoard.getColumnMin();
            if (i < 0) { continue; }
            
            // Draw Moving Positions
            for (int j = 0; j < this.columnSizes[i]; j++)
            {
                if (j != movingPos[i] - 1) { continue; }

                posYShift = ((float) (BOARD_HEIGHT - this.columnSizes[i]) / 2) * (PANEL_HEIGHT / (BOARD_HEIGHT));
                posX = i * (PANEL_WIDTH / BOARD_WIDTH) + ((PIECE_SIZE + SCREEN_PADDING) / 2);
                posY = (BOARD_HEIGHT - j - 1) * (PANEL_HEIGHT / BOARD_HEIGHT) + (SCREEN_PADDING / 2) - (int) posYShift;
                g.fillRect(posX, posY, PIECE_SIZE, PIECE_SIZE);
            }
        }
    }
    
    @Override
    public void drawBoard()
    {
        repaint();
    }
    
    public static int getScreenPadding()
    {
        return SCREEN_PADDING;
    }
}
