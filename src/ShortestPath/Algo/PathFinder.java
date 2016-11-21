package ShortestPath.Algo;

import data.Edge;
import data.Point;
import main.MapScanner;

import javax.imageio.ImageIO;
import javax.xml.soap.Node;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Carioca on 11/11/2016.
 */
public class PathFinder {
    static final int STEPS_NUMBER_LIMIT = 1300;

    static class ImgProcessingResults{
        public int width;
        public int height;
        public LinkedList<Edge>[] terrainGraph ;
        public int [] radiationValues ;
        public Point startPoint;
        public Point finishPoint;

        public ImgProcessingResults(String path) throws IOException {
            MapScanner sc = new MapScanner();
            this.terrainGraph = sc.scan("map.png");
            this.radiationValues = sc.getRadiations();
            this.width = sc.getWidth();
            this.height = sc.getHeight();
            this.startPoint = sc.getStartFinishPlace().getKey();
            this.finishPoint = sc.getStartFinishPlace().getValue();
        }
    }


    public static boolean FindPath(String path, String resultPath) throws IOException{
        ImgProcessingResults preProcessedImage = new ImgProcessingResults(path);
        Point destPoint = FindLowestRadiationPathLimitedBy1300Moves(preProcessedImage);
        DrawResultImage(destPoint, path, resultPath, preProcessedImage);
        return false;
    }

    static Point FindLowestRadiationPathLimitedBy1300Moves(ImgProcessingResults preProcessedImage){
        double stepCostCft = .1;
        Point targetPoint = A_Star(stepCostCft, preProcessedImage);
        int pathLength = CountPathLength(targetPoint);
//        while(pathLength > STEPS_NUMBER_LIMIT){
//            stepCostCft = RedefineStepCostCft(stepCostCft, pathLength);
//            targetPoint = A_Star(stepCostCft, preProcessedImage);
//            pathLength = CountPathLength(targetPoint);
//        }
        return new Point();
    }

    static Point A_Star(double stepCostCft, ImgProcessingResults imgData){
        NodeTotalCost [][]nodesCosts = InitializeNodesCosts(imgData);
        Queue<NodeTotalCost> openList = new PriorityQueue<>();
        int imgWidth = imgData.width;
        int imgHeight = imgData.height;
        for(int i = 0; i < imgHeight; i++){
            for (int j = 0; j < imgWidth; j++) {
                openList.add(nodesCosts[i][j]);
            }
        }
        return new Point();
    }

    static NodeTotalCost [][]InitializeNodesCosts(ImgProcessingResults imgData){
        NodeTotalCost [][]nodesTotCostArray;
        int imgWidth = imgData.width;
        int imgHeight = imgData.height;
        int dstNmbr = imgData.finishPoint.getNodeNumber();
        nodesTotCostArray = new NodeTotalCost[imgHeight][];
        for(int i = 0; i < imgHeight; i ++){
            nodesTotCostArray[i] = new NodeTotalCost[imgWidth];
            for (int j = 0; j < imgWidth; j++) {
                nodesTotCostArray[i][j] = new NodeTotalCost(imgWidth, dstNmbr, i * imgWidth + j);
            }
        }
        return nodesTotCostArray;
    }

    static int CountPathLength(Point trgPoint){
        return STEPS_NUMBER_LIMIT;
    }

    static double RedefineStepCostCft(double crntCft, int pathLength){
        double newCostCft = (pathLength / STEPS_NUMBER_LIMIT) * crntCft;
        return newCostCft;
    }

    static void DrawResultImage(Point trgPoint, String path, String resultPath, ImgProcessingResults imgData){
        File f = new File(path);
        File resultFile = new File(resultPath);
        int imgHeight = imgData.height;
        int imgWidth = imgData.width;
        int pixelColor = new Color(255,0,0).getRGB();
        try {
            BufferedImage image = ImageIO.read(f);
//            Node bfrNode = finalNode;
//            while(bfrNode != null){
//                image.setRGB(bfrNode.getPosition().getX(), bfrNode.getPosition().getY(), pixelColor);
//                bfrNode = bfrNode.getParent();
//            }

            ImageIO.write(image, "png", resultFile);
        }
        catch (IOException e) {

        }
    }

    private static boolean InitializeRadioactiveGraph(LinkedList<Edge>[] terrainGrpah, int [] radiationValues) {
        MapScanner sc = new MapScanner();
        try {
            terrainGrpah = sc.scan("map.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        radiationValues = sc.getRadiations();
        return false;
    }


    // Do not use

    public PathFinder() {

        System.out.println("Path found!");
    }
}
