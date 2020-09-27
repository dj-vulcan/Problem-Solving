package com.dj.problem;

import java.util.HashMap;
import java.util.Map;

public class BoxWrap {
    public static void main(String[] args) {
        

    }

    public int solution(int[][] boxes) {
        int totalNum = 0;
        Map<Integer, Integer> countMap = new HashMap<>();

        for(int[] box: boxes) {
            for(int num: box) {
                totalNum++;
                countMap.putIfAbsent(num, 0);
                countMap.put(num, countMap.get(num) + 1);
            }
        }

        int boxNum = boxes.length;
        int doneBox = 0;
        for(int key: countMap.keySet()) {
            int count = countMap.get(key);
            doneBox += count / 2;
        }

        int restBox = boxNum - doneBox;
        int restItem = totalNum - doneBox * 2;

        if(restBox <= 0) {
            return 0;
        }

        if(restItem >= restBox) {
            return restBox;
        } else {
            return (restBox - restItem) * 2 + restItem;
        }
    }
}