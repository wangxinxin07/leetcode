package com.wxx;

import com.wxx.base.BaseHandler;
import com.wxx.entity.ListNode;

/**
 * 两数相加
 *
 * @author: wangxinxin-hj
 * @date: 2021/11/28 13:22
 */
public class Leetcode_2_TwoNumAdd extends BaseHandler {

    @Override
    public void myResolve() {
//        int[] arry1 = {2, 4, 3};
//        int[] arry2 = {5, 6, 4};
        int[] arry1 = {9,9,9,9,9,9,9};
        int[] arry2 = {9,9,9,9};

        ListNode listNode1 = getListNode(arry1);
        ListNode listNode2 = getListNode(arry2);

        System.out.println(listNode1);
        System.out.println(listNode2);
        ListNode result = my(listNode1, listNode2);
        System.out.println(result);
    }

    private ListNode getListNode(int[] array1){
        ListNode first = new ListNode();
        ListNode last = first;
        ListNode current = first;
        for (int i : array1) {
            current.setVal(i);
            if (last != current) {
                last.setNext(current);
            }
            last = current;
            current = new ListNode();
        }
        return first;
    }

    /**
     *
     */
    private static ListNode my(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        ListNode last = result;
        ListNode current = result;
        int up = 0;
        while (true) {
            if (l1 == null && l2 == null && up == 0) {
                break;
            }
            int val1 = l1 == null ? 0 : l1.getVal();
            int val2 = l2 == null ? 0 : l2.getVal();

            int result_val = val1 + val2 + up;
            up = 0;
            if (result_val > 9) {
                result_val = result_val % 10;
                up = 1;
            }

            if (last != current) {
                last.setNext(current);
            }
            current.setVal(result_val);

            last = current;
            current = new ListNode();

            l1 = l1 == null ? null : l1.getNext();
            l2 = l2 == null ? null : l2.getNext();
        }
        return result;
    }


    @Override
    public void bestResolve() {
        int[] arry1 = {9,9,9,9,9,9,9};
        int[] arry2 = {9,9,9,9};

        ListNode listNode1 = getListNode(arry1);
        ListNode listNode2 = getListNode(arry2);

        ListNode best = best(listNode1, listNode2);
        System.out.println(best);
    }


    private ListNode best(ListNode l1,ListNode l2){
        ListNode dummyHead = new ListNode(-1), pre = dummyHead;
        int t = 0;
        while (l1 != null || l2 != null || t != 0) {
            if (l1 != null) {
                t += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                t += l2.val;
                l2 = l2.next;
            }
            pre.next = new ListNode(t % 10);
            pre = pre.next;
            t /= 10;
        }

        return dummyHead.next;
    }

    private static void best(int[] nums, int target) {
    }

    public static void main(String[] args) {
//        new Leetcode_2_TwoNumAdd().myResolve();
        new Leetcode_2_TwoNumAdd().bestResolve();
    }
}
