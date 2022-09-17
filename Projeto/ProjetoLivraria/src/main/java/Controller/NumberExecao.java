/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author EDUARDO
 */
public class NumberExecao {
 
    public static boolean verNum(String campo){
        try{
            Integer.parseInt(campo);
        }catch(NumberFormatException erro){
            return true;
        }
        return false;
    }
}
