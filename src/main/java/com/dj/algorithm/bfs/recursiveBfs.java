package com.dj.algorithm.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
    트리의 BFS 를 재귀로 도는 예제
*/
public class recursiveBfs {

    static class Node {
        List<Node> children = new ArrayList<>();
    }

    /*
     * n: 트리 노드 개수 tree: [[엣지시작노드, 엣지끝노드], ...]
     */
    public void example(int n, int[][] tree) {
        Map<Integer, Node> nodeMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            Node node = new Node();
            nodeMap.put(i, node);
        }

        for (int[] edge : tree) {
            Node cur = nodeMap.get(edge[0]);
            cur.children.add(nodeMap.get(edge[1]));
        }

        Node root = nodeMap.get(0);
        Queue<Node> qu = new ArrayDeque<>();
        qu.offer(root);

        recBfs(qu, 0);
    }

    public void recBfs(Queue<Node> qu, int level) {
        // System.out.println(level);
        int quSize = qu.size();

        List<Node> childrenList = new ArrayList<>();
        for (int i = 0; i < quSize; i++) {
            // 현재 돌면서
            Node cur = qu.poll();
            List<Node> children = cur.children;
            childrenList.addAll(children);
        }
        
        Queue<Node> newQu = new ArrayDeque<>(childrenList);

        recBfs(newQu, level + 1);
    }
}