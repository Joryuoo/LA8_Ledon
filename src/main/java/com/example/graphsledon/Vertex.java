package com.example.graphsledon;

import java.io.Serial;
import java.io.Serializable;

public class Vertex implements Serializable {
    String name;
    double x;
    double y;
    String color = "#ffc232"; // default color

    public Vertex(String name, double x, double y, String color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
