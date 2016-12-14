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
public interface ITile {

    void draw(Graphics2D g);

    int getX();

    int getY();

    //Zwracamy obiekt dekorowany, potrzebny do metody mouseRealeased
    ITile getDecoratedTile();

    public int getState();
}
