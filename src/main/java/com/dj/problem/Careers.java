package com.dj.problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Careers {
    public static void main(String[] args) {
        String[] info = {"java backend junior pizza 150","python frontend senior chicken 210","python frontend senior chicken 150","cpp backend senior pizza 260","java backend junior chicken 80","python backend senior chicken 50"};
        String[] query = {"java and backend and junior and pizza 100","python and frontend and senior and chicken 200","cpp and - and senior and pizza 250","- and backend and senior and - 150","- and - and - and chicken 100","- and - and - and - 150"};

        System.out.println("sef sef sef ".replaceAll("f ", "XXX"));
        System.out.println(new Careers().solution(info, query));
    }
    
    Map<String, List<People>> combMap = new HashMap<>(); 

    public int[] solution(String[] info, String[] query) {

        int[] answer = new int[query.length];

        for(String inf: info) {
            People p = new People(inf);
            p.putSelf(combMap);
        }

        for(String key: combMap.keySet()){
            List<People> list = combMap.get(key);
            Collections.sort(list);
        }

        //쿼리시작

        for(int i = 0; i < answer.length; i++) {
            String q = query[i];
            q = q.replaceAll("and ", "");
            String[] split = q.split(" ");
            String combKey = "";
            for(int j = 0; j < split.length - 1; j++) {
                combKey += split[j];
                if(j != split.length - 2) {
                    combKey += " ";
                }
            }
            int score = Integer.parseInt(split[split.length - 1]);

            List<People> list = combMap.get(combKey);
            if(list == null) {
                answer[i] = 0;
                continue;
            }

            //binary 서치로 
            int lo = 0, hi = list.size() - 1;
            while(lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if(list.get(mid).score >= score) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            if(list.get(lo).score < score) {
                answer[i] = 0;
            } else {
                answer[i] = list.size() - lo;
            }
        }

        
        return answer;
    }

    static class People implements Comparable<People>{
        String lang;
        String field;
        String career;
        String food;
        int score;

        public People(String info) {
            String[] split = info.split(" ");
            this.lang = split[0];
            this.field = split[1];
            this.career = split[2];
            this.food = split[3];
            this.score = Integer.parseInt(split[4]);
        }

        public void putSelf(Map<String, List<People>> combMap) {
            String key = "";
            key = this.lang + " " + this.field + " " + this.career + " " + this.food;
            this.putMap(combMap, key);
            key = this.lang + " " + this.field + " " + this.career + " " + "-";
            this.putMap(combMap, key);
            key = this.lang + " " + this.field + " " + "-" + " " + this.food;
            this.putMap(combMap, key);
            key = this.lang + " " + this.field + " " + "-" + " " + "-";
            this.putMap(combMap, key);
            
            key = this.lang + " " + "-" + " " + this.career + " " + this.food;
            this.putMap(combMap, key);
            key = this.lang + " " + "-" + " " + this.career + " " + "-";
            this.putMap(combMap, key);
            key = this.lang + " " + "-" + " " + "-" + " " + this.food;
            this.putMap(combMap, key);
            key = this.lang + " " + "-" + " " + "-" + " " + "-";
            this.putMap(combMap, key);

            key = "-" + " " + this.field + " " + this.career + " " + this.food;
            this.putMap(combMap, key);
            key = "-" + " " + this.field + " " + this.career + " " + "-";
            this.putMap(combMap, key);
            key = "-" + " " + this.field + " " + "-" + " " + this.food;
            this.putMap(combMap, key);
            key = "-" + " " + this.field + " " + "-" + " " + "-";
            this.putMap(combMap, key);

            key = "-" + " " + "-" + " " + this.career + " " + this.food;
            this.putMap(combMap, key);
            key = "-" + " " + "-" + " " + this.career + " " + "-";
            this.putMap(combMap, key);
            key = "-" + " " + "-" + " " + "-" + " " + this.food;
            this.putMap(combMap, key);
            key = "-" + " " + "-" + " " + "-" + " " + "-";
            this.putMap(combMap, key);
        }

        private void putMap(Map<String, List<People>> combMap, String key) {
            if(combMap.containsKey(key)) {
                combMap.get(key).add(this);
            } else {
                combMap.put(key, new ArrayList<>());
                combMap.get(key).add(this);
            }
        }

        @Override
        public int compareTo(People o) {
            return Integer.compare(this.score, o.score);
        }
    }


}