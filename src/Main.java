import data.Edge;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by NIKMC on 11.11.16.
 */
public class Main {

    public static void main(String[] args){


        try {
            MapScanner.scan("map.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
