package data;

/**
 * Created by NIKMC on 11.11.16.
 */
public class Edge {

    private Point to;   //не использовал
    private Point from;     //не использовал
    private Point Node;         //узел
    private double weight;      //вес ребра 1 или sqrt(2)


    public Edge(Point node, double weight) {
        Node = node;
        this.weight = weight;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getNode() {
        return Node;
    }

    public void setNode(Point node) {
        Node = node;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
