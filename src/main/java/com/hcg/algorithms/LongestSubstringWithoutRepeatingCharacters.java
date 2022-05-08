package com.hcg.algorithms;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取最长不重复子串
 */
public class LongestSubstringWithoutRepeatingCharacters {

    private static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }

        int left = 0;
        int right = 0;
        int max = 0;
        Set<Character> charSet = new HashSet<>();

        while (left < s.length()) {
            if (charSet.contains(s.charAt(right))) {
                max = Math.max(max, right - left);
                charSet.remove(s.charAt(left++));
            } else {
                charSet.add(s.charAt(right++));
                if (right == s.length()) {
                    return Math.max(max, right - left);
                }
            }
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println("length = " + lengthOfLongestSubstring("au"));
    }
}
