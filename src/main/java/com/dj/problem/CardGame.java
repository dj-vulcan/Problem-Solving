package com.dj.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dj.utils.choice.Choice;

public class CardGame {
    public static void main(String[] args) {
        

    }


    public int solution(int[][] board, int r, int c) {
        Map<Integer, List<Posi>> posiMap = new HashMap<>();

        //카드종류 모음
        Set<Integer> cardSet = new HashSet<>();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 0) {
                    continue;
                }
                int card = board[i][j];
                cardSet.add(card);
                if(!posiMap.containsKey(card)) {
                    posiMap.put(card, new ArrayList<>());
                }
                posiMap.get(card).add(new Posi(i, j));
            }
        }
        List<Integer> cardList = new ArrayList<>(cardSet);


        //방문할 카드 순서
        List<List<Integer>> permu = Choice.getPermutation(cardList, cardList.size());
        for(List<Integer> per: permu) {
            int[][] newBoard = new int[4][4];
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    newBoard[i][j] = board[i][j];
                }
            }

            List<List<Integer>> perper = Choice.getPermutation(per, 2);
            for(List<Integer> order: perper) {
                int cost = 0;
                for(int card: order) {
                    
                }
            }
        }


        //보드 복사


        //둘중에 하나로 간다
        //남은데로 간다


        int answer = 0;
        return answer;
    }

    public int minCost(int[][] board, int curY, int curX, int tarY, int tarX) {
        return 0;
    }

    public static class Posi{
        int y;
        int x;
        public Posi(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}