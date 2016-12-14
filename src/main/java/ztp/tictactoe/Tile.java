/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.Graphics2D;
import java.awt.Image;
import static ztp.tictactoe.Board.ZEROX;
import static ztp.tictactoe.Board.ZEROY;

/**
 *
 * @author Daniel
 */
public class Tile implements ITile {

    public static final int TILESIZE = 128;
    private Image image;
    private int state;

    private int x, y;

    public Tile(int xx, int yy, Image image) {
        this.image = image;
        x = xx;
        y = yy;
        state = 0;//empty
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (x * TILESIZE) + ZEROX, (y * TILESIZE) + ZEROY, TILESIZE, TILESIZE, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile getDecoratedTile() {
        return this;
    }

    public int getState() {
        return this.state;
    }
}
