/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

import java.util.*;

/**
 *
 * @author admin
 */
public class DiceCup {
    private final static int diceTotal = 4;
    private final static int diceChosenMax = 2; // dice that can be paired
    private final static int diceCombinations = Math.combination(diceTotal, diceChosenMax);// = diceTotal / (diceTotal - diceChosenMax);
    private Integer[][] diceCombinationsValues;
    
    private final Dice[] dice = new Dice[diceTotal];
    
    public DiceCup()
    {
        for (int i = 0; i < diceTotal; i++)
        { 
            dice[i] = new Dice(1, 6);
        }
    }
    
    public int[] rollDice()
    {
        int[] diceValues = new int[diceTotal];
        
        for (int i = 0; i < diceTotal; i++)
        {
            diceValues[i] = dice[i].roll();
        }
        
        return diceValues;
    }
    
    private void dicePairings(int[] diceRoll)
    {
        this.diceCombinationsValues = new Integer[diceCombinations][diceChosenMax];
        
        // Create a list of dice from the integer array
        List<Integer> diceList = new ArrayList<Integer>();
        
        for (int die : diceRoll)
        {
            diceList.add(die);
        }
        
        // Loop through all possible combinations of groupings
        // This implementation only works for a group size of 2
        // Anything higher is out of the scope of this project
        // And is too difficult...
        List<Integer> diceListCopy;
        for (int i = 0; i < diceCombinations; i++)
        {
            diceListCopy = new ArrayList<>(diceList);
            this.diceCombinationsValues[i] = new Integer[] {diceListCopy.remove(0), diceListCopy.remove(i / diceChosenMax)};
            i++;
            this.diceCombinationsValues[i] = new Integer[] {diceListCopy.remove(0), diceListCopy.remove(0)};
        }
    }
    
    private boolean dicePairingCheck(int[] movingPieces, int movingPiecesAvailable, List<Integer> movingPossibility, GameBoard board, int pairingSum)
    {
        // If the column on the board is already claimed
        if (board.getColumnClaimed(pairingSum - board.getColumnMin()))
        {
            return false;
        }

        // If there is no possibility for another moving piece
        boolean inMovingPieces = false;
        
        for (int piece : movingPieces)
        {
            inMovingPieces = (piece == pairingSum || inMovingPieces);
        }
        
        if (!inMovingPieces && movingPiecesAvailable - movingPossibility.size() <= 0)
        {
            return false;
        }
        
        // Passes checks (piece is either in movingPieces or can be added to movingPossibility)
        return true;
    }
    
    private void printChoices(List<List<Integer[]>> diceChoices)
    {
        Iterator iterChoice = diceChoices.iterator();
        Iterator iterGroup;
        Integer[] pairing;
        
        int optionIndex = 1;
        while (iterChoice.hasNext())
        {
            System.out.print(optionIndex++ + ") ");
            iterGroup = ((List<Integer[]>) iterChoice.next()).iterator();
            while (iterGroup.hasNext())
            {
                pairing = (Integer[]) iterGroup.next();
                System.out.print("(" + pairing[0]);
                for (int i = 1; i < pairing.length; i++)
                {
                    System.out.print(", " + pairing[i]);
                }
                System.out.print(") ");
            }
            System.out.println("");
        }
    }
    
    private List<List<Integer[]>> dicePairingChoice(int[] movingPieces, int movingPiecesAvailable, GameBoard board)
    {
        List<List<Integer[]>> diceChoices = new ArrayList<>();
        List<Integer[]> dicePairings = new ArrayList<Integer[]>();
        List<Integer> movingPossibility = new ArrayList<Integer>();
     
        int groupPairingSize = diceTotal / diceChosenMax;
        int sum;
        boolean isValidPairing;
        boolean inMovingPieces;
        
        for (int i = 0; i < this.diceCombinationsValues.length; i++)
        {
            sum = 0;
            for (int value : this.diceCombinationsValues[i])
            {
                sum += value;
            }

            // Check if the dice pairing is a valid one
            isValidPairing = dicePairingCheck(movingPieces, movingPiecesAvailable, movingPossibility, board, sum);
            
            if (!isValidPairing)
            {
                if (!dicePairings.isEmpty())
                {
                    // Fixes an edge case where the groups don't work properly
                    if (i % groupPairingSize == groupPairingSize - 1)
                    {
                        i--;
                    }
                    
                    diceChoices.add(dicePairings);
                    dicePairings = new ArrayList<Integer[]>();
                    movingPossibility = new ArrayList<Integer>();
                }
                continue;
            }
            
            dicePairings.add(this.diceCombinationsValues[i]);
            
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
            diceChoices.add(dicePairings);
            dicePairings = new ArrayList<Integer[]>();
            movingPossibility = new ArrayList<Integer>();
        }
        
        return diceChoices;
    }
    
    private int[] choiceToOutput(List<Integer[]> list)
    {
        int[] choiceOutput = new int[list.size()];
        int index = 0;
        Iterator iterList = list.iterator();
        Integer[] integerArray;
        
        while (iterList.hasNext())
        {
            integerArray = (Integer[]) iterList.next();
            
            for (int dice = 0; dice < diceChosenMax; dice++)
            {
                choiceOutput[index] += integerArray[dice];
            }
            
            index++;
        }
        
        return choiceOutput;
        
    }
    
    public int[] rollTurn(int[] movingPieces, int movingPiecesAvailable, GameBoard board)
    {
        var kbinput = new Scanner(System.in);
        int[] diceRoll = rollDice();
        
        // Print dice
        System.out.print("Dice Roll: " + diceRoll[0]);
        for (int dice = 1; dice < diceRoll.length; dice++)
        {
            System.out.print(", " + diceRoll[dice]);
        }
        System.out.println("");
        
        // Pair and display all possible pairings
        dicePairings(diceRoll);
        List<List<Integer[]>> diceChoices= dicePairingChoice(movingPieces, movingPiecesAvailable, board);
        printChoices(diceChoices);
        
        if (diceChoices.isEmpty())
        {
            System.out.println("\nBUST!");
            return null;
        }
        
        // Save values to movement position
        System.out.println("Which dice do you wish to select?");
        int saveValue = -1;
        do
        {
            System.out.print(Game.userPrompt);
            try
            {
                saveValue = kbinput.nextInt();
            } catch(java.util.InputMismatchException e)
            {
                kbinput.nextLine();
            }

            if (!(1 <= saveValue && saveValue <= diceChoices.size()))
            {
                System.out.println("Invalid input. Please input a value between 1 and " + (diceChoices.size()) + "...");
            }
        } while (!(1 <= saveValue && saveValue <= diceChoices.size()));
        
        return choiceToOutput(diceChoices.get(saveValue - 1));
    }
}
