package main;

import java.io.IOException;

/**
 * Created by NIKMC on 11.11.16.
 */
public class Main {

    public static void main(String[] args){


        try {
            MapScanner.scan("main/map.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
