package ShortestPath.Test;

import ShortestPath.Algo.PathFinder;

import java.io.IOException;

/**
 * Created by Carioca on 11/11/2016.
 */
public class PF_Testbench {
    public static void main(String []args){
        PathFinder PF = new PathFinder();
        try {
            PathFinder.FindPath("map.png", "result_map.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
