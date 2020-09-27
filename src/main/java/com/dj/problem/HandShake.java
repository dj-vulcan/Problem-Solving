package com.dj.problem;

public class HandShake {
    public static void main(String[] args) {
        System.out.println(new HandShake().solution(2));
    }

    public int solution(int N) {
        int[] window = {2, 1};

        for(int i = 3; i <= N; i++) {
            window[i%2] = window[0] + window[1];
        }

        return window[N%2];
    }
}