package com.example.graphsledon;

import java.io.Serializable;

public class Vertex implements Serializable {
    String name;
    double x;
    double y;

    public Vertex(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
