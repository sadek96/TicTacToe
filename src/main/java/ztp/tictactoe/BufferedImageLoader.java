/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Daniel
 */
public class BufferedImageLoader implements ImageLoader {

    public BufferedImage loadImage(String path) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
        }
        return image;
    }
}
