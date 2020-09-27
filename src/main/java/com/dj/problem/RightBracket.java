package com.dj.problem;

import java.util.HashMap;
import java.util.Map;
/**
 * 프로그래머스 Lv4. 올바른 괄호
 */
public class RightBracket {
    public static void main(String[] args) {
        
    }

    
    public int solution(int n) {
        Map<Integer, Integer> cache = new HashMap<>();
        cache.put(1, 1);
        cache.put(2, 2);

        for(int i = 3; i <= n; i++) {
            getVal(i, cache);
        }
        return getVal(n, cache);
    }

    public int getVal(int n, Map<Integer, Integer> cache) {
        if(cache.containsKey(n)) {
            return cache.get(n);
        }

        int result = 0;
        for(int i = 1; i <= n; i++) {
            result += r(n - i, i, cache);
        }

        cache.put(n, result);
        return result;
    }

    public int r(int remain, int nextNum, Map<Integer, Integer> cache) {
        if(remain == 0) {
            return 1;
        }

        if(nextNum == 1) {
            return getVal(remain, cache);
        }

        int result = 0;
        for(int i = 0; i <= remain; i++) {
            if(i == 0) {
                result += r(remain - i, nextNum - 1, cache);
                continue;
            }
            result += getVal(i, cache) * r(remain - i, nextNum - 1, cache);
        }
        return result;
    }
}