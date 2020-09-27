package com.dj.problem;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SubTree {
    /*
        t2 가 t1의 서브트리인지 확인해라
        t의 i행 => i노드왼쪽자식, 오른쪽자식
    */

    public static void main(String[] args) {
        int[][] t1 = {
            {1,2},
            {3,4},
            {5,6},
            {-1,7},
            {8,9},
            {-1,-1},
            {-1,-1},
            {-1,-1},
            {-1,-1},
            {-1,-1}
        };
        int[][] t2 = {
            {1,2},
            {-1,-1},
            {-1,-1}
        };

        System.out.println(new SubTree().solution(t1, t2));

        int[][] t11 = {
            {1,2},
            {3,4},
            {5,6},
            {-1,7},
            {8,9},
            {-1,-1},
            {-1,-1},
            {-1,-1},
            {-1,-1},
            {-1,-1}
        };
        int[][] t22 = {
            {-1,1},
            {-1,-1}
        };
        
        System.out.println(new SubTree().solution(t11, t22));
    }

    Map<Integer, Boolean> dp = new HashMap<>();

    public int solution(int[][] t1, int[][] t2) {
        int count = 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        while(!stack.isEmpty()) {
            int curNode = stack.pop();
            if(isSub(curNode, t1, t2)) {
                count++;
            }


            int left = getLeft(curNode, t1);
            if(left != -1) {
                stack.push(left);
            }
            int right = getRight(curNode, t1);
            if(right != -1) {
                stack.push(right);
            }

        }
        return count;
    }

    //t1의 node가 t2의 sub인지
    public boolean isSub(int node, int[][] t1, int[][] t2) {
        if(dp.containsKey(node)) {
            return dp.get(node);
        }

        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        stack1.push(node);
        stack2.push(0);

        while(!stack1.isEmpty() && !stack2.isEmpty()) {
            int curNode1 = stack1.pop();
            int curNode2 = stack2.pop();

            int left1 = getLeft(curNode1, t1);
            int left2 = getLeft(curNode2, t2);
            if(left1 == -1 && left2 != -1 || left1 != -1 && left2 == -1) {
                dp.put(node, false);
                return false;
            }
            if(left1 != -1 && left2 != -1) {
                stack1.push(left1);
                stack2.push(left2);
            }

            int right1 = getRight(curNode1, t1);
            int right2 = getRight(curNode2, t2);
            if(right1 == -1 && right2 != -1 || right1 != -1 && right2 == -1) {
                dp.put(node, false);
                return false;
            }
            if(right1 != -1 && right2 != -1) {
                stack1.push(right1);
                stack2.push(right2);
            }
        }

        if(!stack1.isEmpty() || !stack2.isEmpty()) {
            dp.put(node, false);
            return false;
        }

        dp.put(node, true);
        return true;
    }

    public int getLeft(int node, int[][] t) {
        return t[node][0];
    }

    public int getRight(int node, int[][] t) {
        return t[node][1];
    }
}