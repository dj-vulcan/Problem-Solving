package com.dj.algorithm.dijkstra;

import java.util.Arrays;

public class Dijkstra {
    public static void main(String[] args) {
        int[][] graph = {
            {0, 0, 6, 3, 0},
            {3, 0, 0, 0, 0},
            {0, 0, 0, 2, 0},
            {0, 1, 1, 0, 0},
            {0, 4, 0, 2, 0},
        };
        int[] result = new Dijkstra().dijkstra(graph, 4);
        Arrays.stream(result).forEach(System.out::println);
    }

    public int[] dijkstra(int[][] graph, int s) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        int[] result = new int[n];
        Arrays.fill(result, Integer.MAX_VALUE);

        result[s] = 0;
        while(!isAllTrue(visited)) {
            //후보중 제일 거리짧은거 고르기
            int minDist = Integer.MAX_VALUE;
            int minIdx = s;
            for(int i = 0; i < n; i++) {
                if(!visited[i] && minDist > result[i]) {
                    minIdx = i;
                    minDist = result[i];
                }
            }

            //neighbor 갱신
            visited[minIdx] = true;
            for(int i = 0; i < n; i++){
                if(graph[minIdx][i] <= 0) {
                    continue;
                } 
                result[i] = Math.min(result[i], result[minIdx] + graph[minIdx][i]);
            }
        }

        return result;
    }

    public boolean isAllTrue(boolean[] arr) {
        for(boolean b: arr) {
            if(!b) {
                return false;
            }
        }
        return true;
    }
}