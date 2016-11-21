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
        NodeTotalCost [][]nodesCosts = InitializeNodesCosts(preProcessedImage);
        int destPointNumber = FindLowestRadiationPathLimitedBy1300Moves(preProcessedImage, nodesCosts);
        DrawResultImage(destPointNumber, path, resultPath, preProcessedImage, nodesCosts);
        return false;
    }

    static int FindLowestRadiationPathLimitedBy1300Moves(ImgProcessingResults preProcessedImage, NodeTotalCost [][]nodesCosts){
        double stepCostCft = .1;
        int targetPointNumber = A_Star(stepCostCft, preProcessedImage, nodesCosts);
        int pathLength = CountPathLength(targetPointNumber, nodesCosts, preProcessedImage);
        while(pathLength > STEPS_NUMBER_LIMIT){
            stepCostCft = RedefineStepCostCft(stepCostCft, pathLength);
            nodesCosts = InitializeNodesCosts(preProcessedImage);
            targetPointNumber = A_Star(stepCostCft, preProcessedImage, nodesCosts);
            pathLength = CountPathLength(targetPointNumber, nodesCosts, preProcessedImage);
        }
        return targetPointNumber;
    }

    static int A_Star(double stepCostCfft, ImgProcessingResults imgData, NodeTotalCost [][]nodesCosts){
        int imgWidth = imgData.width;
        int imgHeight = imgData.height;
        int startPointNumber = imgData.startPoint.getNodeNumber();
        int finishPointNumber = imgData.finishPoint.getNodeNumber();
        Queue<NodeTotalCost> openList = new PriorityQueue<>(imgHeight * imgWidth / 2, new NodeCostComparator());
        ArrayList<NodeTotalCost> closedList = new ArrayList<>();
        openList.add(nodesCosts[startPointNumber / imgWidth][startPointNumber % imgWidth]);
        File f = new File("map.png");
        File resultFile = new File("map_dbg.png");
        int pixelColor = new Color(255,0,0).getRGB();
        try {
            BufferedImage image = ImageIO.read(f);
            while (!openList.isEmpty()) {
                NodeTotalCost parent = openList.poll();
                if (closedList.contains(parent))
                    continue;
                double prntDisTo = parent.getG();
                // for each child
                for (Edge iterEdge :
                        imgData.terrainGraph[parent.nodeNumber]) {
                    int childNodeNumber = iterEdge.getNode().getNodeNumber();
                    // set parent to current node
                    if (childNodeNumber == finishPointNumber) {
                        nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth].prntNumber = parent.nodeNumber;
                        ImageIO.write(image, "png", resultFile);
                        return childNodeNumber;
                    }
                    int childRadiation = imgData.radiationValues[childNodeNumber];
                    int childH = nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth].getH();
                    double stepCost = stepCostCfft * iterEdge.getWeight();
                    double childDistTo = prntDisTo + childRadiation + stepCost;
                    double childF = childDistTo + childH;
                    if (openList.contains(nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth]) &&
                            nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth].getF() < childF)
                        continue;
                    if (closedList.contains(nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth]) &&
                            nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth].getF() < childF)
                        continue;
                    nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth].setG(childDistTo);
                    nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth].prntNumber = parent.nodeNumber;
                    openList.add(nodesCosts[childNodeNumber / imgWidth][childNodeNumber % imgWidth]);
                }

                image.setRGB(parent.nodeNumber % imgWidth, parent.nodeNumber / imgWidth, pixelColor);



                closedList.add(nodesCosts[parent.nodeNumber / imgWidth][parent.nodeNumber % imgWidth]);
            }

        }
        catch (IOException e) {

        }

        return -2;
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

    static int CountPathLength(int targetPointNumber, NodeTotalCost [][]nodesCosts, ImgProcessingResults imgData){
        int pathLength = 0;
        int imgWidth = imgData.width;
        int prntNumber = nodesCosts[targetPointNumber / imgWidth][targetPointNumber % imgWidth].prntNumber;
        while (prntNumber != -1){
            prntNumber = nodesCosts[prntNumber / imgWidth][prntNumber % imgWidth].prntNumber;
            pathLength ++;
        }
        return pathLength;
    }

    static double RedefineStepCostCft(double crntCft, int pathLength){
        double newCostCft = ((double)pathLength / STEPS_NUMBER_LIMIT) * crntCft * 10;
        return newCostCft;
    }

    static void DrawResultImage(int trgPoint, String path, String resultPath, ImgProcessingResults imgData, NodeTotalCost [][]nodesCosts){
        File f = new File(path);
        File resultFile = new File(resultPath);
        int imgWidth = imgData.width;
        int prntNumber = nodesCosts[trgPoint / imgWidth][trgPoint % imgWidth].prntNumber;
        int pixelColor = new Color(255,0,0).getRGB();
        try {
            BufferedImage image = ImageIO.read(f);
            while (prntNumber != -1){
                image.setRGB(prntNumber % imgWidth, prntNumber / imgWidth, pixelColor);
                prntNumber = nodesCosts[prntNumber / imgWidth][prntNumber % imgWidth].prntNumber;
            }
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
