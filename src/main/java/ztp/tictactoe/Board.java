/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import static ztp.tictactoe.Tile.TILESIZE;

/**
 *
 * @author Daniel
 */
abstract class Click {

    public abstract boolean isLegal(Point p);

    public abstract void apply(Point p);
}

public class Board extends JPanel {

    private Map<Point, ITile> board = new <Point, ITile>HashMap();
    private ImageLoader loader = new BufferedImageLoader();
    public static int ZEROX = 0;
    public static int ZEROY = 0;
    JButton clearBtn;

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
        board.put(p, new TileX(tile));
    }

    public void putO(Point p) {
        ITile tile;
        tile = board.get(p);
        board.put(p, new TileO(tile));
    }

    public void clearBoard() {
        for (Map.Entry<Point, ITile> e : board.entrySet()) {
            Point pt = (Point) e.getKey();
            ITile pc = (ITile) e.getValue().getDecoratedTile();
            board.put(pt, pc);
        }
        repaint();
    }

    Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.put(new Point(i, j), new Tile(i, j, loader));
            }
        }

        setPreferredSize(new Dimension(TILESIZE * 3 - 2, TILESIZE * 3 - 2 + ZEROY));

        clearBtn = new JButton("CLEAR");
        clearBtn.setBounds(64 - 24, 0, 24, 20);
        clearBtn.addActionListener(new ClearButtonAction(this));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Point p = new Point((me.getX() - ZEROX) / TILESIZE, (me.getY() - ZEROY) / TILESIZE);
                Click click = new Click() {
                    @Override
                    public boolean isLegal(Point pt) {
                        int s = board.get(pt).getState();
                        if (s > 1) {
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public void apply(Point pt) {
                        ITile tile = board.get(pt);
                        board.put(pt, new TileX(tile));
                        repaint();
                    }
                };

                if (click.isLegal(p)) {
                    click.apply(p);
                }

            }
        });

    }

}
