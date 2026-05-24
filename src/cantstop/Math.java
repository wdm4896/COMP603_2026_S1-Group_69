/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cantstop;

/**
 *
 * @author admin
 */
public class Math {
    public static int factorial(int n)
    {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid value. Please make sure all numbers are positive and try again...");
        }
        int factorial = 1;
        
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        
        return factorial;
    }
    
    public static int permutation(int n, int r)
    {
        if (n < r || n < 0 || r < 0) {
            throw new IllegalArgumentException("Invalid value. Please make sure all numbers are positive and that n >= r, and try again...");
        }
        return factorial(n) / factorial(n - r);
    }
    
    public static int combination(int n, int r)
    {
        if (n < r || n < 0 || r < 0) {
            throw new IllegalArgumentException("Invalid value. Please make sure all numbers are positive and that n >= r, and try again...");
        }
        return permutation(n, r) / factorial(r);
    }
}
