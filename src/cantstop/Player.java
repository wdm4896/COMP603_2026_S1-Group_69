/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
public class Player extends Person implements Turn {
    private final static int MOVING_PIECES_MAX = 3;
    private int[] posCurrent;
    private int[] posMoving;
    private int movingPiecesAvailable = MOVING_PIECES_MAX;
    private int[] movingPieces = new int[MOVING_PIECES_MAX];
    private boolean isMoving = false;
    private int claimedTotal = 0;
    private int[] claimedColumns;
    
    public Player(String name, Colour colour)
    {
        super(name, null, colour);
    }
    
    public Player(String name, String username, Colour colour)
    {
        super(name, username, colour);
    }
    
    @Override
    public void savePos(GameBoard board)
    {
        this.posCurrent = this.posMoving.clone();
        
        for (int i = 0; i < GameBoard.getBoardWidth(); i++)
        {
            // Check for any claimed columns not yet tracked
            if (
                    board.getColumnSizes()[i] == this.posCurrent[i] &&
                    board.getColumnClaimed(i) == false
            ) {
                this.claimedColumns[this.claimedTotal++] = i;
                board.setColumnClaimed(i, true);
            }
        }
        
        movingPieces = new int[MOVING_PIECES_MAX];
        this.movingPiecesAvailable = MOVING_PIECES_MAX;
        
        this.isMoving = false;
    }
    
    public void bust()
    {
        this.posMoving = this.posCurrent.clone();
        movingPieces = new int[MOVING_PIECES_MAX];
        this.movingPiecesAvailable = MOVING_PIECES_MAX;
        this.isMoving = false;
    }
    
    public void resetColumns()
    {
        int claimedColumnsTotal = Game.getWinCondition() + MOVING_PIECES_MAX - 1;
        this.claimedColumns = new int[claimedColumnsTotal];
        for (int i = 0; i < this.claimedColumns.length; i++)
        {
            this.claimedColumns[i] = -1;
        }
        this.claimedTotal = 0;
        
        this.posCurrent = new int[GameBoard.getBoardWidth()];
        this.posMoving = new int[GameBoard.getBoardWidth()];
    }
    
    @Override
    public void saveMoving(int[] diceChoice, GameBoard board)
    {
        // Save moving pieces
        boolean inMovingPieces;
        for (int choice : diceChoice)
        {
            // Increases moving value
            int targetIndex = choice - board.getColumnMin();
            if (
                    0 <= this.posMoving[targetIndex] &&
                    this.posMoving[targetIndex] < board.getColumnSizes()[targetIndex]
            ) {
                this.posMoving[targetIndex]++;
            }
            
            // Add to the current moving pieces if they don't already exist
            inMovingPieces = false;
            
            for (int piece : this.movingPieces)
            {
                inMovingPieces = (piece == choice || inMovingPieces);
            }
            
            if (!inMovingPieces && this.movingPiecesAvailable > 0)
            {
                this.movingPieces[MOVING_PIECES_MAX - movingPiecesAvailable--] = choice;
            }
        }
    }
        
    @Override
    public boolean isMoving()
    {
        return this.isMoving;
    }
    
    @Override
    public void setMoving(boolean isMoving)
    {
        this.isMoving = isMoving;
    }
    
    @Override
    public int[] getPosMoving()
    {
        return this.posMoving;
    }
    
    @Override
    public int[] getPosCurrent()
    {
        return this.posCurrent;
    }
    
    public void blockColumn(int index)
    {
        this.posCurrent[index] = -1;
        this.posMoving[index] = -1;
    }
    
    public int[] getClaimedColumns()
    {
        return this.claimedColumns;
    }
    
    public int getClaimedTotal()
    {
        return this.claimedTotal;
    }
    
    public int[] getMovingPieces()
    {
        return this.movingPieces;
    }
    
    public int getMovingPiecesAvailable()
    {
        return this.movingPiecesAvailable;
    }
    
    public int getMovingPiecesMax()
    {
        return MOVING_PIECES_MAX;
    }
}
