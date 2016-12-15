/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import static ztp.tictactoe.Board.ZEROY;
import static ztp.tictactoe.Tile.TILESIZE;

/**
 *
 * @author Daniel
 */
public class Board extends JPanel {

    private Map<Point, ITile> board = new <Point, ITile>HashMap();
    public static int ZEROX = 0;
    public static int ZEROY = 0;
    private Images images;

    public void paint(Graphics g) {
        g.drawRect(0, 0, TILESIZE * 3, TILESIZE * 3);
        for (Map.Entry<Point, ITile> e : board.entrySet()) {
            Point pt = (Point) e.getKey();
            ITile pc = (ITile) e.getValue();
            pc.draw((Graphics2D) g);
        }
    }

    public void putX(Point p) {
        ITile tile;
        tile = board.get(p);
        board.put(p, new TileX(tile, images.getxTile()));
    }

    public void putO(Point p) {
        ITile tile;
        tile = board.get(p);
        board.put(p, new TileO(tile, images.getoTile()));
    }

    public void clearBoard() {
        for (Map.Entry<Point, ITile> e : board.entrySet()) {
            Point pt = (Point) e.getKey();
            ITile pc = (ITile) e.getValue().getDecoratedTile();
            board.put(pt, pc);
        }
        repaint();
    }

    public ITile getTile(Point p) {
        return board.get(p);
    }

    Board() {
        ImageLoader loader = new BufferedImageLoader();
        images = new Images(loader);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.put(new Point(i, j), new Tile(i, j, images.getEmptyTile()));
            }
        }

        setPreferredSize(new Dimension(TILESIZE * 3 - 2, TILESIZE * 3 - 2 + ZEROY));
    }

}
