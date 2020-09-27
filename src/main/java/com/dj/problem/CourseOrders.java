package com.dj.problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseOrders {
 
    public static void main(String[] args) {
        String[] orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
        int[] course = {2,3,4};

        System.out.println(new CourseOrders().solution(orders, course));
    }

    Map<Integer, Integer> maxOrder = new HashMap<>();
    Map<Character, Set<String>> charOrderMap = new HashMap<>();

    public String[] solution(String[] orders, int[] course) {
        List<String> courseList = new ArrayList<>();

        Set<Character> alphabets = new HashSet<>();
        //orders -> 알파벳 후보들 List
        for(String order: orders) {
            for(char ch: order.toCharArray()) {
                if(!charOrderMap.containsKey(ch)) {
                    charOrderMap.put(ch, new HashSet<>());
                }
                charOrderMap.get(ch).add(order);
                alphabets.add(ch);
            }
        }

        List<Character> alphabetCandidates = new ArrayList<>(alphabets);
        Collections.sort(alphabetCandidates);


        for(int courseNum: course) {
            List<List<Character>> candidates = Choice.getCombination(alphabetCandidates, courseNum);
            maxOrder.put(courseNum, 0);

            Set<String> tempCourseList = new HashSet<>();
            Map<String, Integer> orderCount = new HashMap<>();

            for(List<Character> candi: candidates) {
                //orders에서 두번이상 나오는지 -> courseList추가
                int count = twoMoreAppear(candi, orders);
                if(count >= 2) {
                    //candi -> String
                    StringBuilder sb = new StringBuilder();
                    for(char ch: candi) {
                        sb.append(ch);
                    }
                    tempCourseList.add(sb.toString());
                    orderCount.put(sb.toString(), count);
                    maxOrder.put(courseNum, Math.max(maxOrder.get(courseNum), count));
                }
            }

            List<String> removeList = new ArrayList<>();
            for(String s : tempCourseList) {
                int c = orderCount.get(s);
                int max = maxOrder.get(courseNum);
                if(c < max) {
                    removeList.add(s);
                }
            }

            for(String s: removeList) {
                tempCourseList.remove(s);
            }

            courseList.addAll(tempCourseList);
        }

        Collections.sort(courseList);




        String[] answer = new String[courseList.size()];

        for(int i = 0; i < courseList.size(); i++) {
            answer[i] = courseList.get(i);
        }
        return answer;
    }


    public int twoMoreAppear(List<Character> candi, String[] orders) {
        int cnt = 0;
        for(String order: orders) {
            boolean match = true;
            for(char ch: candi) {
                if(!charOrderMap.get(ch).contains(order)) {
                    match = false;
                    break;
                }
            }
            if(match) {
                cnt++;
            }
        }

        return cnt;
    }

    public static class Choice {

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
    }
}