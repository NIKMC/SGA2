package ShortestPath.Algo;

import data.Edge;
import data.Point;
import main.MapScanner;

import java.io.IOException;
import java.util.LinkedList;

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


    public static boolean FindPath(String path) throws IOException{
        ImgProcessingResults preProcessedImage = new ImgProcessingResults(path);

        Point destPoint = FindLowestRadiationPathLimitedWith1300Moves(preProcessedImage);
        DrawResultImage(destPoint, "result.png");
        return false;
    }

    static Point FindLowestRadiationPathLimitedWith1300Moves(ImgProcessingResults preProcessedImage){
        double stepCostCft = .1;
        Point targetPoint = A_Star(stepCostCft, preProcessedImage);
        while(isPathLengthExceeding(STEPS_NUMBER_LIMIT, targetPoint)){

        }
        //A_star()
        return new Point();
    }

    static Point A_Star(double stepCostCft, ImgProcessingResults imgData){
        return new Point();
    }

    static int CountPathLength(Point trgPoint){
        return STEPS_NUMBER_LIMIT;
    }

    static void DrawResultImage(Point trgPoint, String path){

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
