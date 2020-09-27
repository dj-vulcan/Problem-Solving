package com.dj.problem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MakeOne {
    public static void main(String[] args) {
        System.out.println(new MakeOne().solution(345673453));
    }

    public int[] solution(int n) {
        int[] answer = {};
        Queue<Integer> qu = new ArrayDeque<>();

        qu.add(n);
        int level = 0;
        while(!qu.isEmpty()) {
            int levelSize = qu.size();
            for(int i = 0; i < levelSize; i++) {
                int cur = qu.poll();
                if(cur < 10) {
                    answer = new int[]{level, cur};
                    return answer;
                }
                //쪼개서 넣어야함
                for(int c: getChildren(cur)) {
                    qu.add(c);
                }
            }
            level++;
        }
        return answer;
    }

    public List<Integer> getChildren(int n) {
        List<Integer> result = new ArrayList<>();
        if(n < 10) {
           return result; 
        }

        String target = String.valueOf(n);
        for(int i = 0; i < target.length() - 1; i++) {
           String left = target.substring(0, i + 1);
           String right = target.substring(i + 1, target.length());
           boolean leftValid = left.length()==1 || left.charAt(0) != '0';
           boolean rightValid = right.length()==1 || right.charAt(0) != '0';
           if(leftValid && rightValid) {
               result.add(Integer.parseInt(left) + Integer.parseInt(right));
           }
        }
        return result;
    }
}