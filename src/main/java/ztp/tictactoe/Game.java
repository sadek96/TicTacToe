/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.Point;
import java.util.Map;

/**
 *
 * @author ukasz
 */
public class Game {

    boolean turn;
    private int moves = 0, x = 0, o = 0;
    private int[] horizontal = new int[2];
    private int[] vertical = new int[2];
    private int[] slant = new int[2];
    private int[] slant2 = new int[2];
    private Point p = new Point();
    private String winner;
    private boolean end=false;

    // private Board board;
    public void check(Map<Point, ITile> board) {
        if (moves <= 9 && (x >= 3)&& !end) {
            ITile tile;
            int x = 2, y = 0;
            for (int i = 0; i < 3; i++) {
                vertical[0] = 0;
                horizontal[0] = 0;
                vertical[1] = 0;
                horizontal[1] = 0;
                
                for (int j = 0; j < 3; j++) {

                    p.setLocation(i, j);
                    tile = board.get(p);
                    if (tile.getState() == 2) {
                        horizontal[0] += 2;
                        if (horizontal[0] == 6) {
                            winner = "host";
                            end=true;
                            System.out.println("DZIAŁA HORIZONTAL");
                        }
                    } else if (tile.getState() == 3) {
                        horizontal[1] += 3;
                        if (horizontal[1] == 9) {
                            winner = "other";                            end=true;

                        }
                    }

                    p.setLocation(j, i);
                    tile = board.get(p);
                    if (tile.getState() == 2) {
                        vertical[0] += 2;
                        if (vertical[0] == 6) {
                            System.out.println("DZIAŁA VERTICAL");
                            winner = "host";                            end=true;

                        }
                    } else if (tile.getState() == 3) {
                        vertical[1] += 3;
                        if (vertical[1] == 9) {
                            winner = "other";                            end=true;

                        }
                    }
                   

                }
            }
            
            
            slant[0] = 0;
                slant[1] = 0;
                slant2[0] = 0;
                for(int i=0;i<3;i++){
                p.setLocation(i, i);
                    tile = board.get(p);
                    if (tile.getState() == 2) {
                        slant[0] += 2;
                        if (slant[0] == 6) {
                            System.out.println("Slant");
                            winner = "host";                            end=true;

                        }
                }
        }
                  slant[0] = 0;
                slant[1] = 0;
                slant2[0] = 0;
                for(int i=2;i>=0;i--){
                p.setLocation(i, 2-i);
                    tile = board.get(p);
                    if (tile.getState() == 2) {
                        slant[0] += 2;
                        if (slant[0] == 6) {
                            System.out.println("Slant");
                            winner = "host";                            end=true;

                        }
                }
        }
                
                
                

    }}

    public void addX() {
        ++x;
        System.out.println("X");
    }

    public void addO() {
        ++o;
    }

    public void doMove() {
        ++moves;
    }
}
