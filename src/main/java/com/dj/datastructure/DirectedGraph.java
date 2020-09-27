package com.dj.datastructure;

import java.util.List;

public class DirectedGraph {

    private Node[] n;

    public static interface Node {
        List<Node> getNextNodes();
    }

    public static class Edge {

    }

    public static DirectedGraph newDirectedGraph() {
        return new DirectedGraph();
    }

    //public Node addNode


}