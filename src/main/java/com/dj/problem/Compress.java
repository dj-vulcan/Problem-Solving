package com.dj.problem;

class Compress {
    public static void main(String[] args) {
        new Compress().solution("aabbaccc");
    }

    public int solution(String s) {
        int min = Integer.MAX_VALUE;
        for(int i = 1; i <= s.length(); i++) {
            String com = compress(s, i);
            System.out.println(com);
            min = Math.min(min, com.length());
        }
        return min;
    }
    
    String compress(String s, int unit) {
        String[] split = new String[(int)Math.ceil(s.length()/(double)unit)];
        for(int i = 0; i < split.length; i++) {
            int endIdx = i*unit + unit;
            endIdx = endIdx >= s.length() ? s.length() : endIdx;
            split[i] = s.substring(i*unit, endIdx);
        }
        
        int curCount = 1;
        String result = "";
        for(int i = 0; i < split.length; i++) {
            if(i == split.length - 1) {
                result += (curCount > 1 ? curCount : "") + split[i];
            }else if(split[i].equals(split[i+1])) {
                curCount++;
            } else {
                result += (curCount > 1 ? curCount : "") + split[i];
                curCount = 1;
            }
        }
        
        return result;
    }
}