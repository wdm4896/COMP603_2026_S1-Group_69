/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author admin
 */
public class DiceCup {
    private final static int DICE_VALUE_MIN = 1;
    private final static int DICE_VALUE_MAX = 6;
    
    private final static int DICE_TOTAL = 4;
    private final static int DICE_CHOSEN_MAX = 2; // dice that can be paired
    private final static int DICE_COMBINATIONS = Math.combination(DICE_TOTAL, DICE_CHOSEN_MAX);// = diceTotal / (diceTotal - diceChosenMax);
    private Integer[][] diceChoicesAll;
    private List<List<Integer[]>> diceChoicesFiltered;
    
    private final Dice[] dice = new Dice[DICE_TOTAL];
    private int[] diceRoll;
    
    public DiceCup()
    {
        for (int i = 0; i < DICE_TOTAL; i++)
        { 
            dice[i] = new Dice(DICE_VALUE_MIN, DICE_VALUE_MAX);
        }
    }
    
    public int[] rollDice()
    {
        int[] diceValues = new int[DICE_TOTAL];
        
        for (int i = 0; i < DICE_TOTAL; i++)
        {
            diceValues[i] = dice[i].roll();
        }
        
        return diceValues;
    }
    
    private void dicePairings(int[] diceRoll)
    {
        this.diceChoicesAll = new Integer[DICE_COMBINATIONS][DICE_CHOSEN_MAX];
        
        // Create a list of dice from the integer array
        List<Integer> diceList = new ArrayList<>();
        
        for (int die : diceRoll)
        {
            diceList.add(die);
        }
        
        // Loop through all possible combinations of groupings
        // This implementation only works for a group size of 2
        // Anything higher is out of the scope of this project
        // And is too difficult...
        List<Integer> diceListCopy;
        for (int i = 0; i < DICE_COMBINATIONS; i++)
        {
            diceListCopy = new ArrayList<>(diceList);
            this.diceChoicesAll[i] = new Integer[] {
                diceListCopy.remove(0),
                diceListCopy.remove(i / DICE_CHOSEN_MAX)
            };
            i++;
            this.diceChoicesAll[i] = new Integer[] {
                diceListCopy.remove(0),
                diceListCopy.remove(0)
            };
        }
    }
    
    private boolean dicePairingCheck(
            int[] movingPos,
            int[] movingPieces,
            int movingPiecesAvailable,
            List<Integer> movingPossibility,
            GameBoard board,
            int pairingSum
    )
    {
        // If the column on the board is already claimed
        if (board.getColumnClaimed(pairingSum - board.getColumnMin()))
        {
            return false;
        }

        // Check if the piece is a moving piece
        boolean inMovingPieces = false;
        
        for (int piece : movingPieces)
        {
            inMovingPieces = (piece == pairingSum || inMovingPieces);
        }
        
        Iterator iterMoving = movingPossibility.iterator();
        while (iterMoving.hasNext())
        {
            inMovingPieces = ((int) iterMoving.next() == pairingSum || inMovingPieces);
        }
        
        // If the moving piece can't move anymore
        if (inMovingPieces)
        {
            int targetIndex = pairingSum - board.getColumnMin();
            
            if (movingPos[targetIndex] >= board.getColumnSizes()[targetIndex])
            {
                return false;
            }
        }
        
        // If there is no possibility for another moving piece to be added
        if (!inMovingPieces && movingPiecesAvailable <= 0)
        {
            return false;
        }
        
        // Passes checks (piece is a valid movable piece)
        return true;
    }
    
    public void dicePairingChoice(
            int[] movingPos,
            int[] movingPieces,
            int movingPiecesAvailable,
            int[] diceRoll,
            GameBoard board
    )
    {
        this.diceChoicesFiltered = new ArrayList<>();
        List<Integer[]> dicePairings = new ArrayList<>();
        List<Integer> movingPossibility = new ArrayList<>();
     
        int groupPairingSize = DICE_TOTAL / DICE_CHOSEN_MAX;
        int sum;
        boolean isValidPairing;
        boolean inMovingPieces;
        
        dicePairings(diceRoll); // Grab all possible pairings with no validation
        
        // Check each pairing to see if it is valid
        for (int i = 0; i < this.diceChoicesAll.length; i++)
        {
            sum = 0;
            for (int value : this.diceChoicesAll[i])
            {
                sum += value;
            }

            // Check if the dice pairing is a valid one
            isValidPairing = dicePairingCheck(
                    movingPos,
                    movingPieces,
                    movingPiecesAvailable - movingPossibility.size(),
                    movingPossibility,
                    board,
                    sum
            );
            
            if (!isValidPairing)
            {
                if (!dicePairings.isEmpty())
                {
                    // Fixes an edge case where the groups don't work properly
                    if (i % groupPairingSize == groupPairingSize - 1)
                    {
                        i--;
                    }
                    
                    this.diceChoicesFiltered.add(dicePairings);
                    dicePairings = new ArrayList<>();
                    movingPossibility = new ArrayList<>();
                }
                continue;
            }
            
            dicePairings.add(this.diceChoicesAll[i]);
            
            // If the dice pairing is not the last pairing in a group
            if (i % groupPairingSize != groupPairingSize - 1)
            {
                // Add to movingPossibility if isn't yet a moving piece
                inMovingPieces = false;

                for (int piece : movingPieces)
                {
                    inMovingPieces = (piece == sum || inMovingPieces);
                }

                if (!inMovingPieces)
                {
                    movingPossibility.add(sum);
                }
                
                continue;
            }
            
            // Add the pairings to the list of possible options
            this.diceChoicesFiltered.add(dicePairings);
            dicePairings = new ArrayList<>();
            movingPossibility = new ArrayList<>();
        }
    }
    
    public int[] choiceToOutput(int choice)
    {
        List<Integer[]> list = this.diceChoicesFiltered.get(choice - 1);
        int[] choiceOutput = new int[list.size()];
        int index = 0;
        Iterator iterList = list.iterator();
        Integer[] integerArray;
        
        // Sum all pairings and stores them in int array
        while (iterList.hasNext())
        {
            integerArray = (Integer[]) iterList.next();
            
            for (int diceIndex = 0; diceIndex < DICE_CHOSEN_MAX; diceIndex++)
            {
                choiceOutput[index] += integerArray[diceIndex];
            }
            
            index++;
        }
        
        return choiceOutput;
        
    }
    
    public boolean rollTurn(
            Player player,
            GameBoard board
    )
    {
        diceRoll = rollDice();
        
        // Pair and keep all possible pairings
        dicePairingChoice(
                player.getPosMoving(),
                player.getMovingPieces(),
                player.getMovingPiecesAvailable(),
                diceRoll,
                board
        );

        // Return whether the player can make a move or not
        return !this.diceChoicesFiltered.isEmpty();
    }

    public List<List<Integer[]>> getDiceChoicesFiltered()
    {
        return this.diceChoicesFiltered;
    }
    
    public static int getDiceValueMin()
    {
        return DICE_VALUE_MIN;
    }
    
    public static int getDiceValueMax()
    {
        return DICE_VALUE_MAX;
    }
    
    public static int getDiceTotal()
    {
        return DICE_TOTAL;
    }
    
    public static int getDiceChosenMax()
    {
        return DICE_CHOSEN_MAX;
    }
    
    public int[] getDiceRoll()
    {
        return this.diceRoll;
    }
}
