/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
public interface Turn {
    public int[] getPosCurrent();
    public void savePos(GameBoard board);
    public boolean isMoving();
    public void setMoving(boolean isMoving);
    public int[] getPosMoving();
    public void haveTurn(GameBoard board, DiceCup diceCup);
}
