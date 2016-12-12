/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.Graphics2D;

/**
 *
 * @author Daniel
 */
public class TileDecorator implements ITile {

    private ITile tile;

    public TileDecorator(ITile tile) {
        this.tile = tile;
    }

    public void draw(Graphics2D g) {
        this.tile.draw(g);
    }

    public int getX() {
        return this.tile.getX();
    }

    public int getY() {
        return this.tile.getY();
    }

    public ITile getDecoratedTile() {
        return this.tile;
    }

    public ImageLoader getImageLoader() {
        return this.tile.getImageLoader();
    }

    public int getState() {
        return this.tile.getState();
    }
    
}
