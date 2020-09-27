package com.dj.problem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IdRule {
    public static void main(String[] args) {
        String test = "abcdefghijklmn.p";
        System.out.println(new IdRule().solution(test)); 
        
    }

    public String solution(String new_id) {
        Set<Character> set = new HashSet<>(Arrays.asList('-','_','.'));

        
        //1.
        String ch1 = new_id.toLowerCase();
        System.out.println(ch1);

        //2.
        StringBuilder ch2sb = new StringBuilder();
        for(char c: ch1.toCharArray()) {
            if(Character.isDigit(c) || Character.isAlphabetic(c) || set.contains(c)) {
                ch2sb.append(c);
            }
        }
        String ch2 = ch2sb.toString();
        System.out.println(ch2);

        //3.
        StringBuilder ch3sb = new StringBuilder();
        char[] chararr = ch2.toCharArray();
        for(int i = 0; i < chararr.length; i++) {
            if(i != chararr.length - 1 && (chararr[i] == '.' && chararr[i+1] == '.')) {
                continue;
            }
            ch3sb.append(chararr[i]);
        }
        String ch3 = ch3sb.toString();
        System.out.println(ch3);

        //4.
        String ch4 = ch3;
        if(ch3.equals(".")) {
            ch4 = "";
        } else if(ch3.charAt(0) == '.' && ch3.charAt(ch3.length() - 1) == '.') {
            ch4 = ch3.substring(1, ch3.length() - 1);
        } else if(ch3.charAt(0) == '.') {
            ch4 = ch3.substring(1, ch3.length());
        } else if(ch3.charAt(ch3.length() - 1) == '.') {
            ch4 = ch3.substring(0, ch3.length() - 1);
        }
        System.out.println(ch4);

        //5.
        String ch5 = ch4;
        if(ch4.equals("")) {
            ch5 = "a";
        }
        System.out.println(ch5);

        //6.
        String ch6 = ch5;
        if(ch5.length() >= 16) {
            ch6 = ch5.substring(0, 15);
        }
        if(ch6.charAt(ch6.length() - 1) == '.') {
            ch6 = ch6.substring(0, ch6.length() -1 );
        }
        System.out.println(ch6);

        //7.
        String ch7 = ch6;
        if(ch6.length() <= 2) {
            int rest = 3 - ch6.length();
            String last = String.valueOf(ch6.charAt(ch6.length() -1));
            for(int i = 0; i < rest; i++) {
                ch7 += last;
            }
        }
        System.out.println(ch7);

        return ch7;
    }
}