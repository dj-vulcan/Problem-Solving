package com.dj.problem;

import java.util.Stack;

public class BracketTrans {
    public static void main(String[] args) {
        
    }

    public String solution(String p) {
        if(isRight(p) || p.equals("")) {
            return p;
        }

        
        int leftCnt = 0, rightCnt = 0;
        int splitIdx = 0;
        for(int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);
            if(ch == '(') {
                leftCnt++;
            } else {
                rightCnt++;
            }
            if(leftCnt == rightCnt) {
                splitIdx = i;
                break;
            }
        }

        String u = p.substring(0, splitIdx + 1);
        String v = splitIdx + 1 == p.length() ? "" : p.substring(splitIdx + 1, p.length());

        if(isRight(u)) {
            return u + solution(v);
        } else {
            return "(" + solution(v) + ")" + reversed(u.substring(1, u.length() - 1));
        }
    }

    public boolean isBalanced(String s) {
        int leftCnt = 0, rightCnt = 0;

        for(char ch: s.toCharArray()) {
            if(ch == '(') {
                leftCnt++;
            } else {
                rightCnt++;
            }
        }

        return leftCnt == rightCnt;
    }

    public boolean isRight(String s) {
        Stack<Character> stack = new Stack<>();

        for(char ch: s.toCharArray()) {
            if(ch == '(') {
                stack.push(ch);
            } else {
                if(stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

    public String reversed(String s) {
        StringBuilder b = new StringBuilder();
        for(char ch: s.toCharArray()) {
            if(ch == '(') {
                b.append(")");
            } else {
                b.append("(");
            }
        }
        return b.toString();
    }
}