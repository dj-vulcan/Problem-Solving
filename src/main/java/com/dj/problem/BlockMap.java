package com.dj.problem;

import java.util.HashMap;
import java.util.Map;
/**
 * 프로그래머스 Lv4. 
 */
public class BlockMap {
    public static void main(String[] args) {
        int[][] land = {
            {4, 4, 3}, {3, 2, 2}, { 2, 1, 0 }
        };

        //System.out.println(new BlockMap().solution(land, 5, 3));
        for(int i = 0; i < 5; i++) {
            System.out.println(new BlockMap().cost(land, 5, 3, i));
        }
    }

    Map<Integer, Long> costCache = new HashMap<>();

    public long solution(int[][] land, int P, int Q) {
        int maxHeight = 0;
        int minHeight = Integer.MAX_VALUE;
        for(int[] vals: land) {
            for(int val: vals) {
                maxHeight = Math.max(maxHeight, val);
                minHeight = Math.min(minHeight, val);
            }
        }


        int low = minHeight, high = maxHeight;
        while(low < high) {
            int mid = low + (high - low) / 2;
            if(predicate(land, P, Q, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        //System.out.println(low);
        return cost(land, P, Q, low);
    }

    public boolean predicate(int[][] land, int P, int Q, int height) {

        long curCost = cost(land, P, Q, height);
        long nextCost = cost(land, P, Q, height + 1);

        return curCost < nextCost;
    }

    public long cost(int[][] land, int P, int Q, int height) {
        if(costCache.containsKey(height)) {
            return costCache.get(height);
        }

        long result = 0L;
        for(int[] vals: land) {
            for(int val: vals) {
                long dh = height - val;
                if(dh > 0) {
                    result += dh*P;
                } else {
                    result += -dh*Q;
                }
            }
        }
        costCache.put(height, result);
        return result;
    }
}