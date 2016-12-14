/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.Point;
import javax.swing.JLabel;

/**
 *
 * @author Komputer
 */
class GameState{

}

public class Game{
    
    JLabel label;
    Board board;
    SocketProxy socket;
    boolean myTurn;
    boolean running;

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Game(JLabel label, Board board, SocketProxy socket) {
        this.label = label;
        this.board = board;
        this.socket = socket;
        myTurn = true;
        running = true;
    }

    public void run() {

        String in = null;
        while (running) {

            in = readLine();

            myTurn = false;

            if (myTurn == false) {

                String[] point = new String[2];
                point = in.split(",");

                int x = Integer.parseInt(point[0]);
                int y = Integer.parseInt(point[1]);

                board.putO(new Point(x, y));
                board.repaint();
                myTurn = true;
                setMessage("Twoja tura...");
            }
        }

        socket.dispose();
    }

    public String readLine() {
        return socket.readLine();
    }

    public void writeLine(String str) {
        socket.writeLine(str);
    }

    public void setMessage(String text) {
        this.label.setText(text);
    }

}
