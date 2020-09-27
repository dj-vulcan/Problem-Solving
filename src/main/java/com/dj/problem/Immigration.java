package com.dj.problem;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Immigration {
    public static void main(String[] args) {
        Immigration inst = new Immigration();
        System.out.println(inst.solution(3, new int[]{7, 10, 20, 15})); 
    }

    public long solution(int n, int[] times) {

        if(n < times.length) {
            Arrays.sort(times);
            return times[n-1];
        }
        
        long answer = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for(int i = 0; i < times.length; i++) {
            answer = Math.max(answer, times[i]);
            queue.add(new Node(i, 2*times[i]));
        }
        
        for(int i = 0; i < n - times.length; i++) {
            Node node = queue.poll();
            //걔를 answer에 넣음.
            answer = node.nextTime;
            //System.out.println(answer);
            node.nextTime += (long)times[node.index];
            queue.add(node);
        }

        return answer;
    }

    static class Node implements Comparable<Node>{
        int index;
        long nextTime;
        
        public Node(int index, long time) {
            this.index = index;
            this.nextTime = time;
        }

        @Override
        public int compareTo(Node o) {
            return (int)(this.nextTime - o.nextTime);
        }
    }
}