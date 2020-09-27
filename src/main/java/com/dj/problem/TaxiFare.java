package com.dj.problem;

import java.util.Arrays;

public class TaxiFare {
    public static void main(String[] args) {
        //int[][] fares = {{4, 1, 10}, {3, 5, 24}, {5, 6, 2}, {3, 1, 41}, {5, 1, 24}, {4, 6, 50}, {2, 4, 66}, {2, 3, 22}, {1, 6, 25}};
        int[][] fares = {{5, 7, 9}, {4, 6, 4}, {3, 6, 1}, {3, 2, 3}, {2, 1, 6}};
        System.out.println(new TaxiFare().solution(7, 3, 4, 1, fares));
        
    }

    public int solution(int n, int s, int a, int b, int[][] fares) {
        int[][] graph = new int[n][n];
        for(int[] fare: fares) {
            int i = fare[0] - 1;
            int j = fare[1] - 1;
            int weight = fare[2];

            graph[i][j] = weight;
            graph[j][i] = weight;
        }


        int[] sToAll = dijkstra(graph, s-1);
        int[] aToAll = dijkstra(graph, a-1);
        int[] bToAll = dijkstra(graph, b-1);

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            int fare = sToAll[i] + aToAll[i] + bToAll[i];
            min = Math.min(min, fare);
        }

        return min;
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

            if(minDist == Integer.MAX_VALUE) {
                break;
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