package com.dj.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GemStore {
    public static void main(String[] args) {
        //String[] gems = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
        //String[] gems = {"XYZ", "XYZ", "XYZ"};
        String[] gems = {"XYZ"};

        int[] sol = new GemStore().solution(gems);
        System.out.println(sol[0] + ", " + sol[1]);
        
    }

    public int[] solution(String[] gems) {
        Map<String, Integer> map = new HashMap<>();
        for(String gem: gems) {
            map.put(gem, map.getOrDefault(gem, 0) + 1);
        }

        List<int[]> solList = new ArrayList<>();

        int s = 0, e = -1;
        Map<String, Integer> compMap = new HashMap<>();
        while(true) {
            //e늘리기, 못늘리면 안됨
            e++;
            while(e < gems.length) {
                compMap.put(gems[e], compMap.getOrDefault(gems[e], 0) + 1);
                if(compMap.size() == map.size()) {
                    break;
                }
                e++;
            }
            if(e >= gems.length) {
                break;
            }


            //s늘리기, 저장
            while(s < gems.length) {
                int prev = compMap.put(gems[s], compMap.get(gems[s]) - 1);
                if(prev == 1) {
                    compMap.remove(gems[s]);
                }
                s++;
                if(compMap.size() < map.size()) {
                    //저장
                    solList.add(new int[]{s, e+1});
                    break;
                }
            }
        }
        solList.sort((a,b) ->  {
            if(a[1] - a[0] == b[1] - b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - a[0] < b[1] - b[0] ? -1 : 1;
            }
        });
        return solList.get(0);
    }
}