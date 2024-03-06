/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.Sistema;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Catarina
 */
public class Arranque {
    private static Sistema sistema;

    public static void main(String[] args) {
        // TODO code application logic here
        sistema = new Sistema ();
        
        Menu xMenu = new Menu();
        xMenu.setLocationRelativeTo(null); 
        xMenu.setVisible(true);
    }
    
    public static Sistema getSistema() {
        return sistema;
    }

}
