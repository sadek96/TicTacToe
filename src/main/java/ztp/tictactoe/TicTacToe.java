/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import javax.swing.JFrame;

/**
 *
 * @author Daniel
 */

public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board board = new Board();
        
        
        frame.add(board);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
