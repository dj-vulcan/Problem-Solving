package com.dj.utils.choice;

import java.util.ArrayList;
import java.util.List;

public class Choice {
    
    public static <T> List<List<T>> getCombination(List<T> list, int n) {
        List<List<T>> result = new ArrayList<>();
        recurComb(0, n, list, new ArrayList<T>(), result);
        return result;
    }

    private static <T> void recurComb(int startIdx, int n, List<T> list, List<T> thisComb, List<List<T>> result) {
        if(n == 0) {
            result.add(new ArrayList<>(thisComb));
            return;
        }

        for(int i = startIdx; i < list.size(); i++) {
            thisComb.add(list.get(i));
            recurComb(i + 1, n - 1, list, thisComb, result);
            thisComb.remove(thisComb.size() - 1);
        }
    }
    
    public static List<Integer> newSerialIntegerList(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for(int i = start; i <= end; i++) {
            result.add(i);
        }
        return result;
    }

    public static <T> List<List<T>> getPermutation(List<T> list, int n) {
        List<List<T>> result = new ArrayList<>();
        boolean[] visited = new boolean[list.size()];
        recurPermu(visited, n, list, new ArrayList<>(), result);
        return result;
    }

    private static <T> void recurPermu(boolean[] visited, int n, List<T> list, List<T> thisPermu, List<List<T>> result) {
        if(n == 0) {
            result.add(new ArrayList<>(thisPermu));
            return;
        }

        for(int i = 0; i < visited.length; i++) {
            if(visited[i]) {
                continue;
            }

            thisPermu.add(list.get(i));
            visited[i] = true;
            recurPermu(visited, n-1, list, thisPermu, result);
            visited[i] = false;
            thisPermu.remove(thisPermu.size() - 1);
        }
    }
}