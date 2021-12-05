package com.wxx;

import com.alibaba.fastjson.JSON;
import com.wxx.base.BaseHandler;

import java.util.*;

/**
 * 无重复字符的最长子串
 * 
 *
 * @author: wangxinxin-hj
 * @date: 2021/11/28 13:22
 */
public class Leetcode_3_NoDumplicateSonStr extends BaseHandler {

    @Override
    public void myResolve() {
        my("abcabcbb");
    }

    private String my(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        List<Set<String>> endList = new ArrayList<>();
        List<Set<String>> noDumplicateList = new ArrayList<>();
        String[] split = str.split("");
        for (String s : split) {
            Set<String> current = new HashSet<>();
            current.add(s);

            Iterator<Set<String>> iterator = noDumplicateList.iterator();
            while (iterator.hasNext()) {
                Set<String> next = iterator.next();
                if (next.contains(s)) {
                    endList.add(next);
                    iterator.remove();
                } else {
                    next.add(s);
                }
            }
            noDumplicateList.add(current);
        }
        endList.addAll(noDumplicateList);

        Set<String> strings = endList.stream().max(Comparator.comparing(Set::size)).get();
        System.out.println("max size=" + strings.size());
        System.out.println(JSON.toJSONString(strings));
        return strings.toString();
    }


    @Override
    public void bestResolve() {
//        best("pwwkew");
        best("bbbbb");
//        best("abcabcbb");

    }

    private Integer best(String str) {
        if (str.length() == 0) {
            return 0;
        }
        int max = 0;
        int left = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }
            map.put(c, i);
            max = Math.max(max, i - left + 1);
        }
        System.out.println(max);
        return max;
    }


    private static void best(int[] nums, int target) {
    }

    public static void main(String[] args) {

//        new Leetcode_3_NoDumplicateSonStr().myResolve();

        new Leetcode_3_NoDumplicateSonStr().bestResolve();
    }
}
