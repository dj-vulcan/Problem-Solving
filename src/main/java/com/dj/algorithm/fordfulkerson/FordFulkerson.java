package com.dj.algorithm.fordfulkerson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class FordFulkerson {

    public static class Vertex {
        private int val;
        private Set<Edge> edges = new HashSet<>();

        public Vertex(int val) {
            this.val = val;
        }

        public Edge addEdge(Vertex v, int capacity) {
            Edge edge = Edge.setEdgeCapacity(this, v, capacity);
            Edge oposite = Edge.getEdge(v, this);
            edges.add(edge);
            v.edges.add(oposite);
            return edge;
        }
    }

    public static class Edge {
        public Vertex u;
        public Vertex v;
        public int capacity;
        public int flow;
        public int residualCapacity;

        private static Map<Edge, Edge> cache;
        static {
            cache = new HashMap<>();
        }

        private Edge(Vertex u, Vertex v, int capacity, int flow, int residualCapacity) {
            this.u = u;
            this.v = v;
        }

        public static Edge getEdge(Vertex u, Vertex v) {
            Edge edge = new Edge(u, v, 0, 0, 0);
            if (Edge.cache.containsKey(edge)) {
                edge = Edge.cache.get(edge);
            } else {
                Edge.cache.put(edge, edge);
            }
            return edge;
        }

        public static Edge setEdgeCapacity(Vertex u, Vertex v, int capacity) {
            Edge edge = getEdge(u, v);
            edge.capacity = capacity;
            return edge;
        }

        public static Edge setEdgeFlow(Vertex u, Vertex v, int flow) {
            Edge edge = getEdge(u, v);
            edge.flow = flow;
            return edge;
        }

        public static Edge setEdgeResidualCapacity(Vertex u, Vertex v, int residualCapacity) {
            Edge edge = getEdge(u, v);
            edge.residualCapacity = residualCapacity;
            return edge;
        }

        @Override
        public int hashCode() {
            return this.u.hashCode() | this.v.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Edge)) {
                return false;
            }
            Edge target = (Edge) obj;
            return target.v == this.v && target.u == this.u;
        }
    }

    public static void main(String[] args) {
        Vertex v0 = new Vertex(0);
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        Vertex v5 = new Vertex(5);

        v0.addEdge(v1, 16);
        v0.addEdge(v2, 13);
        v1.addEdge(v2, 10);
        v1.addEdge(v3, 12);
        v2.addEdge(v1, 4);
        v2.addEdge(v4, 14);
        v3.addEdge(v2, 9);
        v3.addEdge(v5, 20);
        v4.addEdge(v3, 7);
        v4.addEdge(v5, 4);

        int result = new FordFulkerson().fordFulkerson(v0, v5);
        System.out.println(result);
    }

    public int fordFulkerson(Vertex s, Vertex t) {
        List<Edge> path = new ArrayList<>();

        while(dfs(s, t, path)) {
            int minResidualCapacity = Integer.MAX_VALUE;

            //Argumenting Path의 최소 Residual Capacity 구하기
            for(Edge edge : path) {
                minResidualCapacity = Math.min(minResidualCapacity, edge.residualCapacity);
            };

            //Path 반대로 flow 더하기
            for(Edge edge : path) {
                edge.flow += minResidualCapacity;
                Edge.getEdge(edge.v, edge.u).flow -= minResidualCapacity;
            }

            path.forEach(edge -> {
                System.out.print(edge.v.val + " ");
            });
            System.out.println(minResidualCapacity);
            path = new ArrayList<>();
        }

        int result = 0;
        Iterator<Edge> iter = Edge.cache.keySet().iterator();
        while(iter.hasNext()) {
            Edge edge = iter.next();
            if(edge.v == t && edge.flow > 0) {
                result += edge.flow;
            }
        }

        return result;
    }

    public boolean dfs(Vertex s, Vertex t, List<Edge> path) {
        //Residual Capacity 계산
        calculateResidualNetwork(Edge.cache.keySet().iterator());

        //dfs
        Set<Vertex> visited = new HashSet<>();

        Stack<Vertex> stack = new Stack<>();
        Map<Vertex, Vertex> parentMap = new HashMap<>();


        stack.push(s);

        boolean meet = false;
        while (!stack.isEmpty()) {

            Vertex cur = stack.pop();
            if(cur == t) {
                meet = true;
                break;
            }
            visited.add(cur);

            Iterator<Edge> iter = cur.edges.iterator();
            while (iter.hasNext()) {
                Edge edge = iter.next();
                if(edge.residualCapacity > 0 && !visited.contains(edge.v)) {
                    parentMap.put(edge.v, cur);
                    stack.push(edge.v);
                }
            }
        }
        if(meet) {
            Vertex cur = t;
            while(parentMap.containsKey(cur)) {
                Vertex parent = parentMap.get(cur);
                path.add(Edge.getEdge(parent, cur));
                cur = parent;
            }
        }
        return meet;
    }

    public void calculateResidualNetwork(Iterator<Edge> edges) {
        while(edges.hasNext()) {
            Edge edge = edges.next();
            edge.residualCapacity = edge.capacity - edge.flow;
        }
    }
}