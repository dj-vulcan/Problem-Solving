package com.dj.datastructure;

import java.util.HashMap;
import java.util.Map;

public class Trie {
    private TrieNode root = new TrieNode();

    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord = false;
        String content;
        int count = 0;
    }

    public void insert(String s) {
        TrieNode cur = root;
        cur.count++;
        for(char charactor: s.toCharArray()) {
            cur = cur.children.computeIfAbsent(charactor, c -> new TrieNode());
            cur.count++;
        }
        cur.isWord = true;
        cur.content = s;
    }

    public boolean contains(String s) {
        TrieNode cur = root;
        for(char charactor: s.toCharArray()) {
            cur = cur.children.get(charactor);
            if(cur == null) {
                return false;
            }
        }
        return cur.isWord;
    }

    public int getCount(String s) {
        TrieNode cur = root;
        for(char charactor: s.toCharArray()) {
            cur = cur.children.get(charactor);
            if(cur == null) {
                return 0;
            }
        }
        return cur.count;
    }

    //count없다고 가정
    public void delete(String s) {
        delete(this.root, s, 0);
    }

    //@return 자식이 없는지 여부
    private boolean delete(TrieNode current, String s, int index) {
        if(index == s.length()){
            if(!current.isWord) {
                return false;
            }
            current.isWord = false;
            return current.children.isEmpty();
        }
        char ch = s.charAt(index);
        TrieNode next = current.children.get(ch);
        boolean shouldDelete = delete(next, s, index + 1) && !next.isWord;
        if(shouldDelete) {
            current.children.remove(ch);
            return current.children.isEmpty();
        }
        return false;
    }

    
}