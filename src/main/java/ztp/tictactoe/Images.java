/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.Image;

/**
 *
 * @author Komputer
 */
public class Images {

    private Image emptyTile, xTile, oTile;

    public Images(ImageLoader il) {
        emptyTile = il.loadImage("/empty.png");
        xTile = il.loadImage("/x.png");
        oTile = il.loadImage("/o.png");
    }

    public Image getEmptyTile() {
        return emptyTile;
    }

    public Image getxTile() {
        return xTile;
    }

    public Image getoTile() {
        return oTile;
    }

}
