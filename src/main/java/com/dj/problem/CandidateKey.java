package com.dj.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dj.utils.choice.Choice;

public class CandidateKey {
    
    public static void main(String[] args) {
        String[][] relation = {
            {"100","ryan","music","2"},
            {"200","apeach","math","2"},
            {"300","tube","computer","3"},
            {"400","con","computer","4"},
            {"500","muzi","music","3"},
            {"600","apeach","music","2"}
        };
        System.out.println(new CandidateKey().solution(relation));
    }

    public int solution(String[][] relation) {
        int C = relation[0].length;

        Map<List<Integer>, Boolean> records = new HashMap<>();
        int resultCount = 0;
        List<Integer> candidates = new ArrayList<>();
        for(int i = 0; i < C; i++) {
            candidates.add(i);
        }

        for(int n = 1; n <= C; n++) {
            List<List<Integer>> combinations = Choice.getCombination(candidates, n);
            for(List<Integer> combination: combinations) {
                if(records.containsKey(combination)) {
                    continue;
                }
                boolean isKey = isKey(combination, relation);
                records.put(combination, isKey);
                if(isKey) {
                    resultCount++;
                    //상위꺼 모두 false;
                    for(List<Integer> upper: getUpperCombinations(combination, C)) {
                        records.put(upper, false);
                    }
                }
            }
        }
        return resultCount;
    }

    public boolean isKey(List<Integer> choice, String[][] relation) {
        
        Set<List<String>> set = new HashSet<>();
        boolean isKey = true;
        for(String[] tuple : relation) {
            List<String> tup = new ArrayList<>();
            for(int idx: choice) {
                tup.add(tuple[idx]);
            }
            if(set.contains(tup)) {
                isKey = false;
                break;
            }
            set.add(tup);
        }

        return isKey;
    }

    public List<List<Integer>> getUpperCombinations(List<Integer> combination, int C) {
        List<Integer> rest = new ArrayList<>();
        int combIdx = 0;
        for(int i = 0; i < C; i++) {
            if(combIdx < combination.size() && i == combination.get(combIdx)) {
                combIdx++;
            } else {
                rest.add(i);
            }
        }
        List<List<Integer>> restCombs = new ArrayList<>();

        for(int n = 1; n <= rest.size(); n++) {
            restCombs.addAll(Choice.getCombination(rest, n));
        }

        List<List<Integer>> result = new ArrayList<>();
        for(List<Integer> restComb : restCombs) {
            boolean[] mark = new boolean[C];
            List<Integer> resultComb = new ArrayList<>();

            combination.forEach(e -> mark[e] = true);
            restComb.forEach(e -> mark[e] = true);

            for(int i = 0; i < C; i++) {
                if(mark[i]) {
                    resultComb.add(i);
                }
            }
            result.add(resultComb);
        }
        return result;
    }
}