package ShortestPath.Algo;

import java.util.Comparator;

/**
 * Created by Konstantin on 21.11.2016.
 */
public class NodeCostComparator implements Comparator<NodeTotalCost> {
    @Override
    public int compare(NodeTotalCost Node1, NodeTotalCost Node2){
        if(Node1.getF() < Node2.getF()){
            return -1;
        }
        if(Node1.getF() == Node2.getF())
            return 0;
        else
            return 1;
    }
}
