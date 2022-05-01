package com.hcg.algorithms;

/**
 * 二分查找
 * 在一个升序数组中查找指定值
 */
public class BinarySearch {

    private static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 3, 4, 8, 10, 23, 57};
        int target = 10;
        System.out.println("BinarySearch.main: " + binarySearch(nums, target));
    }
}
