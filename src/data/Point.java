package data;

/**
 * Created by NIKMC on 11.11.16.
 */
public class Point {

    private int node;   //номер ячейки
    private int x;
    private int y;
    private int path;   //не использовал
    private int radiation; // не использовал
    private static final int width = 700;
    public Point(){}
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.node = y * width + x;
    }

    public Point(int x, int y, int radiation) {
        this.x = x;
        this.y = y;
        this.radiation = radiation;
    }

    public Point(int x, int y, int path, int radiation) {
        this.x = x;
        this.y = y;
        this.path = path;
        this.radiation = radiation;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadiation() {
        return radiation;
    }

    public void setRadiation(int radiation) {
        this.radiation = radiation;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }
}
