package com.dj.algorithm.dijkstra;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra {

    public static void main(String[] args) {
        int[][] graph = {
            {0, 0, 6, 3, 0},
            {0, 0, 0, 1, 2},
            {6, 0, 0, 1, 0},
            {3, 1, 1, 0, 1},
            {0, 2, 0, 1, 0},
        };
        int[] result = new Dijkstra().dijkstra(graph, 4);
        Arrays.stream(result).forEach(System.out::println);
    }

    public int[] dijkstra(int[][] graph, int s) {
        int n = graph.length;

        int[] result = new int[n];
        Arrays.fill(result, Integer.MAX_VALUE);
        result[s] = 0;

        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(n, Comparator.comparingInt(a -> result[a]));
        pq.add(s);
        while(visited.size() < n) {
            //후보중 제일 거리짧은거 고르기
            int minIdx = pq.remove();

            //neighbor 갱신
            visited.add(minIdx);
            for(int i = 0; i < n; i++){
                if(graph[minIdx][i] <= 0) {
                    continue;
                }

                int newDist = result[minIdx] + graph[minIdx][i];
                if(newDist < result[i]) {
                    result[i] = newDist;
                    pq.add(i);
                }
            }
        }

        return result;
    }
}
