package com.PizzaDrone.ILPWebService.dataType;

public class Node {
    public Positions position;
    public Node parent;
    public double gscore;
    public double fscore;

    public Node(Positions position) {
        this(position, null, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public Node(Positions position, Node parent, double gScore, double fScore) {
        this.position = position;
        this.parent = parent;
        this.gscore = gScore;
        this.fscore = fScore;
    }
}
