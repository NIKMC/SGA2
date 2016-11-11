import data.Edge;
import data.Point;

import java.util.*;

/**
 * Created by NIKMC on 11.11.16.
 */
public class Radiation {

    private LinkedList<Edge> adj[]; //список смежности
    private boolean used[]; //массив для хранения информации о пройденных и не пройденных вершинах
    private Queue<Integer> queue; //очередь для добавления вершин при обходе в ширину
    private int radiation[]; // задаем радиацию каждой вершине
    private void SetRadiation(int v, List<Point> radiations){
        if (used[v]) { //если вершина является пройденной, то не производим из нее вызов процедуры
            return;
        }
        queue.add(v); //начинаем обход из вершины v
        used[v] = true; //помечаем вершину как пройденную
        //ver[v] = (path);
        while (!queue.isEmpty()) {
            v = queue.poll(); //извлекаем вершину из очереди
           for (int i = 0; i < adj[v].size(); ++i) {
                int w = adj[v].get(i).getNode().getNode();
                //если вершина уже была посещена, то пропускаем ее
                if (used[w]) {
                    continue;
                }

                queue.add(w); //добавляем вершину в очередь обхода
                used[w] = true; //помечаем вершину как пройденную


               double Sumrad = 0;
               for(int x=0; x < radiations.size();x++){

                   //if(graph[i].get(j).getNode().getRadiation() != null)

                   double distance = Math.sqrt( Math.pow((adj[v].get(i).getNode().getX() - radiations.get(x).getX()),2)
                           + Math.pow((adj[v].get(i).getNode().getY() - radiations.get(x).getY()),2));
                   Sumrad += Math.pow(10,6) * Math.pow(Math.E, -distance/2);


               }
//               graph[i].get(j).getNode().setRadiation((int)Sumrad);

               radiation[w] = (int)Sumrad;
//               System.out.println("Vershina = " + w + "| radiation = " + radiation[w]);
            }

        }

//            adj[v].get(i).setPoint(path);

    }


    public int[] starCalculationRadiation(LinkedList<Edge>[] graph, List<Point> radiations){
        adj = graph;
        radiation = new int[adj.length];

        used = new boolean[adj.length];
        queue = new LinkedList<Integer>();
        Arrays.fill(used, false);
        for (int i = 0; i < adj.length; i++) {//graph.length-(480*4)
            SetRadiation(i, radiations);
        }

        return radiation;
    }

}
