package com.wxx;

import com.alibaba.fastjson.JSON;
import com.wxx.base.BaseHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 *
 * @author: wangxinxin-hj
 * @date: 2021/11/28 13:22
 */
public class Leetcode_1_SumTwoNum extends BaseHandler {

    @Override
    public void myResolve() {
//        int[] nums = {2, 7, 11, 15};
//        int target = 9;

//        int[] nums = {3, 2, 4};
//        int target = 6;

        int[] nums = {3, 3};
        int target = 6;

        my(nums, target);
    }


    /**
     * 时间复杂度  O(n2)
     *
     * @param nums
     * @param target
     */
    private static void my(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int before = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int after = nums[j];
                if (after + before == target) {
                    String result = "[%d,%d]";
                    System.out.printf((result) + "%n", i, j);
                }
            }
        }
    }


    @Override
    public void bestResolve() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        best(nums, target);
    }

    private static void best(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> numsMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (numsMap.containsKey(nums[i])) {
                result[0] = i;
                result[1] = numsMap.get(nums[i]);
                System.out.println(JSON.toJSONString(result));
            }
            numsMap.put(target - nums[i], i);
        }
    }

    public static void main(String[] args) {
//        new Leetcode_1_SumTwoNum().myResolve();
//        new Leetcode_1_SumTwoNum().bestResolve();
        Date date = new Date(1641816510L * 1000);
        System.out.println(date);
    }
}
