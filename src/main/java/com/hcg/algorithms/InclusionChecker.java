package com.hcg.algorithms;

/**
 * 检查字符串s1是否包含于字符串s2中
 * 两个字符串内容全部为小写英文字母
 */
public class InclusionChecker {

    private static boolean checkInclusion(String s1, String s2) {
        if ((s1 == null && s2 == null)) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        if ((s1.length() == 0 && s2.length() == 0)) {
            return true;
        }
        if (s1.length() == 0 || s2.length() == 0) {
            return false;
        }
        if (s2.length() < s1.length()) {
            return false;
        }

        // 执行之后所有字母出现的地方全部为-1
        int[] cnt = new int[26];
        for (int i = 0; i < s1.length(); ++i) {
            --cnt[s1.charAt(i) - 'a'];
        }

        int left = 0;
        for (int right = 0; right < s2.length(); ++right) {
            // 如果有相同字符出现，则会把相应位置数字变为0
            int x = s2.charAt(right) - 'a';
            ++cnt[x];
            // 如果为正数，表明该字符从未出现过
            while (cnt[x] > 0) {
                --cnt[s2.charAt(left++) - 'a'];
            }
            if (right - left + 1 == s1.length()) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println("Inclusion = " + checkInclusion("xyz", "abcdefggg"));
    }
}
