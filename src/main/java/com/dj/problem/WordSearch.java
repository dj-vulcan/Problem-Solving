package com.dj.problem;

import java.util.HashMap;
import java.util.Map;

import com.dj.datastructure.Trie;

public class WordSearch {
    public static void main(String[] args) {
        String[] words = {"frodo", "front", "frost", "frozen", "frame", "kakao"};
        String[] queries = {"fro??", "????o", "fr???", "fro???", "pro?"};

        int[] result = new WordSearch().solution(words, queries);
        for(int r : result) {
            System.out.println(r);
        }
    }

    public int[] solution(String[] words, String[] queries) {
        int[] answer = new int[queries.length];

        Map<Integer, Trie[]> trieMap = new HashMap<>();
        
        for(String word: words) {
            int length = word.length();
            Trie[] tries = trieMap.computeIfAbsent(length, key -> {
                Trie[] arr = new Trie[2];
                for(int i=0; i < 2; i++) {
                    arr[i] = new Trie();
                }
                return arr;
            });

            tries[0].insert(word);
            tries[1].insert(new StringBuilder(word).reverse().toString());
        }

        for(int i = 0; i < answer.length; i++) {
            int length = queries[i].length();
            Trie[] tries = trieMap.get(length);
            if(tries == null) {
                answer[i] = 0;
            } else {
                if(queries[i].charAt(0) == '?') {
                    String s = queries[i].replaceAll("\\?", "");
                    answer[i] = tries[1].getCount(new StringBuilder(s).reverse().toString());
                } else {
                    answer[i] = tries[0].getCount(queries[i].replaceAll("\\?", ""));
                }
            }
        }
        return answer;
    }
}