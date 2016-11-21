package ShortestPath.Algo;

import javafx.util.Pair;

/**
 * Created by Konstantin on 20.11.2016.
 */
public class NodeTotalCost {
    double h;
    double f;
    double g;
    int nodeNumber;
    int prntNumber;



    public NodeTotalCost(int img_dim, int dstNum, int myNum) {
        Pair<Integer, Integer> myCoords = OneDimToTwoDim(myNum, img_dim);
        Pair<Integer, Integer> dstCoords = OneDimToTwoDim(dstNum, img_dim);
        this.h = Math.abs(dstCoords.getKey() - myCoords.getKey()) + Math.abs(dstCoords.getValue() - myCoords.getValue());
        this.f = 0.0;
        this.g = 0.0;
        this.nodeNumber = myNum;
        this.prntNumber = -1;
    }

    public void setG(double g){
        this.g = g;
        this.f = this.h + g;
    }

    Pair<Integer, Integer> OneDimToTwoDim(int Pos, int img_dim){
        int xCoord = Pos % img_dim;
        int yCoord = Pos / img_dim;
        return new Pair<>(xCoord, yCoord);
    }

    public double getH() {
        return h;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

//    @Override
//    public int compareTo(NodeTotalCost that) {
//        if(this.h < that.h)
//            return -1;
//        if(this.h == that.h)
//            return 0;
//        return -1;
//    }
}

