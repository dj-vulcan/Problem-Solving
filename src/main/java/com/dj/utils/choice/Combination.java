package com.dj.utils.choice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Combination {
    public static <T> List<List<T>> getCombination(List<T> candidates, int n) {
        List<List<T>> result = new ArrayList<>();

        Stack<Node<T>> stack = new Stack<>();
        stack.add(Node.newNode(new ArrayList<>(), 0, -1));

        while(!stack.isEmpty()) {
            Node<T> cur = stack.pop();
            if(cur.depth == n) {
                result.add((ArrayList<T>)cur);
                continue;
            }

            Set<T> visited = new HashSet<>(cur);
            for(int i = 0; i < candidates.size(); i++) {
                if(!visited.contains(candidates.get(i)) && i > cur.recentIdx) {
                    List<T> newPath = new ArrayList<>(cur);
                    newPath.add(candidates.get(i));
                    stack.push(Node.newNode(newPath, cur.depth + 1, i));
                }
            }

        }
        
        return result;
    }

    private static class Node<T> extends ArrayList<T>{
        int depth;
        int recentIdx;

        private Node(List<T> path, int depth, int recentIdx) {
            super(path);
            this.depth = depth;
            this.recentIdx = recentIdx;
        }

        public static <T> Node<T> newNode(List<T> path, int depth, int recentIdx) {
            return new Node<T>(path, depth, recentIdx);
        }
    }
}