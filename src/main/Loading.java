package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by NIKMC on 11.11.16.
 */
public class Loading {

    public int[][] loadImage(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("main/map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println(w);
        System.out.println(h);
        int[][] masPixel = new int[w][h];
        for(int i=0;i<700;i++){
            for(int j=0;j<200;j++) {
                masPixel[j][i] = image.getRGB(j, i);
                System.out.print(masPixel[j][i] + " ");
            }
            System.out.println("");
        }
        return masPixel;
    }

}
