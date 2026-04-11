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
        int factorial = 1;
        
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        
        return factorial;
    }
    
    public static int permutation(int n, int r)
    {
        return factorial(n) / factorial(n - r);
    }
    
    public static int combination(int n, int r)
    {
        return permutation(n, r) / factorial(r);
    }
}
