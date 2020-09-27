package com.dj.algorithm.fordfulkerson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class FordFulkersonArr {
    public static void main(String[] args) {
        int[][] graph = {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };

        int fordfulkerson = new FordFulkersonArr().fordfulkerson(graph, 0, 5);
        System.out.println(fordfulkerson);

        
    }

    public int fordfulkerson(int[][] graph, int s, int t) {

        List<Integer> path = new ArrayList<>();
        while(dfs(graph, s, t, path)) {
            int minResidualCapacity = Integer.MAX_VALUE;

            for(int i = path.size() - 1; i > 0; i--) {
                int u = path.get(i);
                int v = path.get(i-1);
                minResidualCapacity = Math.min(graph[u][v], minResidualCapacity);
            }

            for(int i = 0; i < path.size() - 1; i++) {
                int u = path.get(i);
                int v = path.get(i+1);
                graph[u][v] += minResidualCapacity;
                graph[v][u] -= minResidualCapacity;
            }
            path.forEach(v -> {
                System.out.print(v + "->");
            });
            System.out.println(s + " cap: " + minResidualCapacity);
            path = new ArrayList<>();
        }

        int result = 0;
        for(int flow : graph[t]) {
            result += flow;
        }
        return result;
    }

    //그래프에서 s에서 t로의 길이 있는지 여부와, path
    public boolean dfs(int[][] graph, int s, int t, List<Integer> path) {
        int n = graph.length;
        Stack<Integer> stack = new Stack<>(); //dfs
        boolean[] visitied = new boolean[n]; //방문
        int[] parent = new int[n]; //어디에서 search됐는지 기록
        Arrays.fill(parent, -1);

        boolean meet = false;
        stack.push(s);
        while(!stack.isEmpty()) {
            int cur = stack.pop();
            visitied[cur] = true;

            if(cur == t) {
                meet = true;
                break;
            }

            for(int adj = 0; adj < n; adj++) {
                if(!visitied[adj] && graph[cur][adj] > 0) {
                    stack.push(adj);
                    parent[adj] = cur;
                }
            }
        }

        //path
        int cur = t;
        while(parent[cur] >= 0) {
            path.add(cur);
            cur = parent[cur];
        }

        return meet;
    }
    
}