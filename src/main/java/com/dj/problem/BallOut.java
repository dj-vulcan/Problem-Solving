package com.dj.problem;

import java.util.ArrayDeque;
import java.util.Deque;

public class BallOut {
    public static void main(String[] args) {
        int[] ball = {11, 2, 9, 13, 24};
        int[] order = {9, 2, 13, 24, 11};
        System.out.println(new BallOut().solution(ball, order));
    }

    public int[] solution(int[] ball, int[] order) {
        int[] answer = new int[ball.length];
        int idx = 0;

        Deque<Integer> deque = new ArrayDeque<>();
        for(int ballItem: ball) {
            deque.offer(ballItem);
        }

        Deque<Integer> queue = new ArrayDeque<>();

        for(int orderItem: order) {

            while(true) {
                boolean isOut = false;
                for(int i = 0; i < queue.size(); i++) {
                    int cur = queue.poll();
                    int out = outBall(deque, cur);
                    if(out == -1) {
                        queue.offer(cur);
                    } else {
                        answer[idx++] = out;
                        isOut = true;
                    }
                }
                if(!isOut) {
                    break;
                }
            }

            int out = outBall(deque, orderItem);
            if(out == -1) {
                queue.offer(orderItem);
            } else {
                answer[idx++] = out;
            }
        }



        return answer;
    }

    public int outBall(Deque<Integer> qu, int ball) {
        int first = qu.peekFirst();
        int last = qu.peekLast();

        if(first == ball) {
            return qu.pollFirst();
        } else if(last == ball) {
            return qu.pollLast();
        } else {
            return -1;
        }
    }
}