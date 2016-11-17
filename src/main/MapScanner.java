package main;

import data.Edge;
import data.Point;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by NIKMC on 11.11.16.
 */
public class MapScanner {

    private static final int width = 700;
    private static final int height = 700;
    private static final int _START = -1237980;
    private static final int _FINISH = -14503604;
    private static final int _RADIATION = -32985;
    private static final int _BLACK = -16777216;
    private static final int _WHITE = -1;
    private static final int MAX_RADIATION = 100_000_000;
    private static final int MAX_PATH = 100_000_000;
    private static Point start;
    private static Point finish;
    private static int[] radiat ;


    public static LinkedList<Edge>[] scan(String path) throws IOException {

        LinkedList<Edge>[] graph = null;
        File f = new File(path);
        BufferedImage image = ImageIO.read(f);
        graph = new LinkedList[height*width];  // graphh is a matrix -> array, matrix[i,j] = graph[i*width + j]
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new LinkedList<Edge>();
        }

        List<Point> radiations = new ArrayList<>();

        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){

                if(image.getRGB(j, i) == _RADIATION){
                    radiations.add(new Point(j,i,MAX_RADIATION));
                }
                if(image.getRGB(j, i) == _START) {
                    System.out.println("find start");

                    start = new Point(j,i);
                }
                if(image.getRGB(j, i) == _FINISH) {
                    System.out.println("find finish");

                    finish = new Point(j,i);
                }
                if(image.getRGB(j, i) != _BLACK && image.getRGB(j, i) != _RADIATION) {
                    if (i > 0 && image.getRGB(j, i - 1) != _BLACK && i > 0 && image.getRGB(j, i - 1) != _RADIATION) { // top pixel
                        if(image.getRGB(j, i) == _START) {
                            System.out.println("find start");
                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");

                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge(new Point(j, (i-1) ),1));
                    }
                    if (j < width - 1 && i  > 0 && image.getRGB(j + 1, i - 1) > _BLACK) { // top right pixel
                        if(image.getRGB(j, i) == _START) {
                            System.out.println("find start");
                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");
                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge( new Point( (j + 1), ((i - 1)) ), Math.sqrt(2)));
                    }
                    if (j < width - 1 && image.getRGB(j + 1, i) > _BLACK) { // right pixel
                        if(image.getRGB(j, i) == _START) {
                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");
                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge( new Point( (j + 1), ((i )) ), 1));
                    }
                    if (j < width - 1 && i < height - 1 && image.getRGB(j + 1, i + 1) > _BLACK) { // right lower pixel
                        if(image.getRGB(j, i) == _START) {
                            System.out.println("find start");

                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");
                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge( new Point( (j + 1), ((i + 1)) ), Math.sqrt(2)));
                    }
                    if (i < height - 1 && image.getRGB(j, i + 1) > _BLACK) { // lower pixel
                        if(image.getRGB(j, i) == _START) {
                            System.out.println("find start");

                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");
                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge(new Point( (j), ((i + 1)) ), 1));
                    }
                    if (i < height - 1 && j > 0 && image.getRGB(j - 1, i + 1) > _BLACK) { // lower left pixel
                        if(image.getRGB(j, i) == _START) {
                            System.out.println("find start");

                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");
                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge(new Point( (j - 1), ((i + 1)) ), Math.sqrt(2)));
                    }
                    if (j > 0 && image.getRGB(j - 1, i) > _BLACK) { // left pixel
                        if(image.getRGB(j, i) == _START) {
                            System.out.println("find start");

                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");
                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge(new Point( (j - 1), ((i)) ), 1));
                    }
                    if (j > 0 && i > 0 && image.getRGB(j - 1, i - 1) > _BLACK) { // left top pixel
                        if(image.getRGB(j, i) == _START) {
                            System.out.println("find start");

                            start = new Point(j,i);
                        }
                        if(image.getRGB(j, i) == _FINISH) {
                            System.out.println("find finish");
                            finish = new Point(j,i);
                        }
                            graph[i * width + j].add(new Edge(new Point( (j - 1), ((i - 1)) ), Math.sqrt(2)));
                    }

                }

            }
        }

       /* for(int i =0; i<graph.length-1;i++){
            System.out.print(graph[i].size() + "| " + i + "= Ver | ");
            for (int j = 0; j<graph[i].size();j++) {
                        System.out.print(graph[i].get(j).getNode().getNode() + " ( "
                                + graph[i].get(j).getNode().getX() + "-|-"
                                + graph[i].get(j).getNode().getY()
                                + ")" + " - ");
            }
            System.out.println();
    }*/

        System.out.println("start" + /*start.getNode() +*/ "|" + start.getX() + "|" + start.getY());
        System.out.println("finish" + /*finish.getNode() +*/ "|" + finish.getX() + "|" + finish.getY());

        example(radiations);
        //DrawRadiationOnMap(graph,radiations);

        //graph[0].get(0);

        return graph;

    }

    private static void example(List<Point> radiations){
        File f = new File("mapnew.png");
        int pixelRedColor = new Color(255,0,0).getRGB();
        try {
            BufferedImage image = ImageIO.read(f);
            for (int i =0; i< radiations.size(); i++)
            image.setRGB(radiations.get(i).getX(), radiations.get(i).getY(), pixelRedColor);

            ImageIO.write(image, "png", f);
        } catch (IOException e) {
        }
    }

    private static void example2(LinkedList<Edge>[] graph){
        File f = new File("mapnew.png");
        int pixelPhColor = new Color(190, 190, 190).getRGB();

        try {
            BufferedImage image = ImageIO.read(f);
            for(int i=0;i<100; i++) {
                for (int j = 0; j < graph[i].size();j++) {
                    image.setRGB(graph[i].get(j).getNode().getNodeNumber() % 700, graph[i].get(j).getNode().getNodeNumber() / 700, pixelPhColor);
                }
            }
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
        }
    }


    private static LinkedList<Edge>[] findRadiation(LinkedList<Edge>[] graph, List<Point> radiations){


        for(int i =0; i<graph.length-1;i++){
            for (int j = 0; j<graph[i].size();j++){

                double Sumrad = 0;
                for(int x=0; x < radiations.size();x++){

                    //if(graph[i].get(j).getNode().getRadiation() != null)

                    double distance = Math.sqrt( Math.pow((graph[i].get(j).getNode().getX() - radiations.get(x).getX()),2)
                            + Math.pow((graph[i].get(j).getNode().getY() - radiations.get(x).getY()),2));
                    Sumrad += Math.pow(10,6) * Math.pow(Math.E, -distance/2);


                }
                //graph[i].get(j).getNode().setRadiation((int)Sumrad);
            }
        }
        return graph;
    }




    public Pair<Point, Point> getStartFinishPlace(){
        return new Pair<>(start, finish);
    }

    public int[] getRadiations(){
        return loadRadiationFromFile();
    }

    private static void DrawRadiationOnMap(LinkedList<Edge>[] graph, List<Point> radiations){
        radiat = new int[graph.length];
        Radiation radiation = new Radiation();
        radiat = radiation.starCalculationRadiation(graph, radiations);
        int max = 0;
        for(int i = 0; i< radiat.length; i++){
            if (radiat[i]>max) max = radiat[i];
        }
        saveRadiationInFile(radiat);

        /*for (int i =0 ; i < graph.length; i++){
            System.out.println("Vershina = " + i + "| radiation = " + radiat[i]);
        }*/

        File f = new File("mapnew.png");
        int pixelRedColor = new Color(200,0,0).getRGB();
        int pixelOrangeColor = new Color(255,165,0).getRGB();
        int pixelYellowColor = new Color(255,255,0).getRGB();
        int pixelGreenColor = new Color(0,255,0).getRGB();
        int pixelBlueColor = new Color(0,255,255).getRGB();
        int pixelBlue2Color = new Color(0,0, 255).getRGB();
        int pixelPhColor = new Color(128, 128, 128).getRGB();
        int pixelNuleColor = new Color(190, 190, 190).getRGB();

        try {
            BufferedImage image = ImageIO.read(f);

            for(int i=0;i<radiat.length; i++){
                /*if(radiat[i]==0 )
                    image.setRGB(i%700, i/700, pixelNuleColor);
                */
                if(radiat[i]>0 && radiat[i]<(max/7))
                image.setRGB(i%700, i/700, pixelPhColor);
                if(radiat[i]>(max/7) && radiat[i]<(2*max/7))
                    image.setRGB(i%700, i/700, pixelBlue2Color);
                if(radiat[i]>(2*max/7) && radiat[i]<(3*max/7))
                    image.setRGB(i%700, i/700, pixelBlueColor);
                if(radiat[i]>(3*max/7) && radiat[i]<(4*max/7))
                    image.setRGB(i%700, i/700, pixelGreenColor);
                if(radiat[i]>(4*max/7) && radiat[i]<(5*max/7))
                    image.setRGB(i%700, i/700, pixelYellowColor);
                if(radiat[i]>(5*max/7) && radiat[i]<(6*max/7))
                    image.setRGB(i%700, i/700, pixelOrangeColor);
                if(radiat[i]>(6*max/7) && radiat[i]<max)
                    image.setRGB(i%700, i/700, pixelRedColor);
            }
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
        }
    }

    private static void saveRadiationInFile(int[] radiation){
        String fileName = "radiation.txt";
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                for(int i=0;i<radiation.length; i++) {
                    out.println(radiation[i]);
                }
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int[] loadRadiationFromFile(){
        String fileName = "radiation.txt";
        int[] radiattion = new int[height*width];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            int i=0;
            while ((line = br.readLine()) != null) {
                radiattion[i] = Integer.parseInt(line);
                System.out.println(line);
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return radiattion;
    }

}
