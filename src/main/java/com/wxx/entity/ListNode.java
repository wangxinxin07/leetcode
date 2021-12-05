package com.wxx.entity;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @author: wangxinxin-hj
 * @date: 2021/12/5 10:32
 */
@Data
@ToString
public class ListNode {

    public Integer val;

    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
