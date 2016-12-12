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
import static ztp.tictactoe.Tile.TILESIZE;

/**
 *
 * @author Daniel
 */
public class TileX extends TileDecorator {

    private Image image;
    private int state;

    public TileX(ITile tile) {
        super(tile);
        state=2;
        this.image = getImageLoader().loadImage("/x.png");
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawImage(this.image, (getX() * TILESIZE) + ZEROX, (getY() * TILESIZE) + ZEROY, TILESIZE, TILESIZE, null);

    }

    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }
    
    public int getState(){
        return this.state;}

}
