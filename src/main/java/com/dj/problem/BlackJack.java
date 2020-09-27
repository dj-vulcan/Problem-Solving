package com.dj.problem;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    public static void main(String[] args) {
        int[] card = {1, 4, 10, 6, 9, 1, 8, 13};

        System.out.println(new BlackJack().solution(card));
    }

    public int solution(int[] cards) {
        int n = cards.length;
        int idx = 0;
        int money = 0;

        while(true) {
            List<Integer> player = new ArrayList<>();
            List<Integer> dealer = new ArrayList<>();
            if(getReft(n, idx) < 4) {
                break;
            }

            int card = cards[idx++];
            player.add(card > 10 ? 10 : card);
            card = cards[idx++];
            dealer.add(card > 10 ? 10 : card);
            card = cards[idx++];
            player.add(card > 10 ? 10 : card);
            card = cards[idx++];
            dealer.add(card > 10 ? 10 : card);//보이는거

            //플레이어 블랙잭
            if(player.get(0) == 1 && player.get(1) == 10) {
                money += 3;
                continue;
            } else if(player.get(1) == 1 && player.get(0) == 10) {
                money += 3;
                continue;
            } else if(player.get(1) + player.get(0) == 21){
                money += 3;
                continue;
            }
            
            //보이는카드 1,7
            //보이는 카드 4,5,6
            //보이는 카드 2,3
            int openCard = dealer.get(1);
            if(openCard == 1 || openCard >= 7) {//17이상 받기
                int sum = 0;
                for(int c: player) {
                    sum += c;
                }
                while(sum < 17) {
                    if(getReft(n, idx) < 1) {
                        return money;
                    } 
                    card = cards[idx++];
                    card = card > 10 ? 10 : card;
                    player.add(card);
                    sum += card;
                }
            } else if(openCard == 2 || openCard == 3 ) {
                int sum = 0;
                for(int c: player) {
                    sum += c;
                }
                while(sum < 12) {
                    if(getReft(n, idx) < 1) {
                        return money;
                    } 
                    card = cards[idx++];
                    card = card > 10 ? 10 : card;
                    player.add(card);
                    sum += card;
                }
            }
            
            //플레이어 21이상?
            int sum = 0;
            for(int c: player) {
                sum += c;
            }
            if(sum >= 21) {
                money -= 2;
                continue;
            }

            //딜러 17이상받기 
            sum = 0;
            for(int c: dealer) {
                sum += c;
            }
            while(sum < 17) {
                if(getReft(n, idx) < 1) {
                    return money;
                } 
                card = cards[idx++];
                card = card > 10 ? 10 : card;
                dealer.add(card);
                sum += card;
            }
            //딜러 21이상?
            sum = 0;
            for(int c: dealer) {
                sum += c;
            }
            if(sum >= 21) {
                money += 2;
                continue;
            }

            //승부
            int pSum = 0;
            int dSum = 0;
            for(int c: dealer) {
                dSum += c;
            }
            for(int c: player) {
                pSum += c;
            }

            if(Math.abs(dSum - 21) > Math.abs(pSum - 21)) {
                money += 2;
            } else if(Math.abs(dSum - 21) < Math.abs(pSum - 21)) {
                money -= 2;
            }


        }
        return money;
    }

    public int getReft(int n, int idx) {
        return n - idx;
    }
}