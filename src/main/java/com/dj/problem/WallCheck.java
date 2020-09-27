package com.dj.problem;

import java.util.Arrays;
import java.util.List;

import com.dj.utils.choice.Choice;

public class WallCheck {
    public static void main(String[] args) {
        int[] weak = { 0, 10 };
        int[] dist = { 2 };

        System.out.println(new WallCheck().solution(12, weak, dist));
        Integer[] hihi= {1,2,3};
        System.out.println(Arrays.asList(hihi).size());

    }

    public int solution(int n, int[] weak, int[] dist) {
        int friendsNum = dist.length;

        for (int num = 1; num <= friendsNum; num++) {
            if (tryNPeople(num, n, weak, dist)) {
                return num;
            }
        }
        return -1;
    }

    public boolean tryNPeople(int num, int n, int[] weak, int[] dist) {

        List<Integer> friendCandidates = Choice.newSerialIntegerList(0, dist.length - 1);
        List<List<Integer>> distPermus = Choice.getPermutation(friendCandidates, num);

        for (List<Integer> distPermu : distPermus) {
            for (int startIdx = 0; startIdx < weak.length; startIdx++) {
                boolean[] covered = new boolean[weak.length];

                int friendIdx = startIdx;
                for(int friend = 0; friend < num; friend++) {
                    friendIdx = go(covered, weak, dist, n, friendIdx, distPermu.get(friend));
                }
                
                boolean isAllTrue = true;
                for (boolean c : covered) {
                    if (!c) {
                        isAllTrue = false;
                        break;
                    }
                }
                if (isAllTrue) {
                    return true;
                }
            }
        }
        return false;
    }

    private int go(boolean[] covered, int[] weak, int[] dist, int n, int friendIdx, int distIdx) {
        int startNum = weak[friendIdx];
        int length = dist[distIdx];
        int lastIdx = friendIdx;

        if (startNum + length >= n) {
            for (int i = friendIdx; i < weak.length + friendIdx; i++) {
                int idx = i % weak.length;
                if (weak[idx] >= startNum && weak[idx] <= n || weak[idx] <= startNum + length - n) {
                    covered[idx] = true;
                    lastIdx = idx;
                }
            }
        } else {
            for (int i = friendIdx; i < weak.length + friendIdx; i++) {
                int idx = i % weak.length;
                if (weak[idx] >= startNum && weak[idx] <= startNum + length) {
                    covered[idx] = true;
                    lastIdx = idx;
                }
            }
        }
        return (lastIdx + 1) % weak.length;
    }
}