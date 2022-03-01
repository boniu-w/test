package wg.application.leeCode;

public class MergeTowList {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1, new ListNode(1, new ListNode(1, new ListNode(4, new ListNode(5)))));
        ListNode l2 = new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))));

        ListNode listNode = mergeTwoListsRecursion(l1, l2);
        System.out.println(listNode);

        // ListNode node = mergeTwoLists(l1, l2);
        // System.out.println(node);
    }

    /************************************************************************
     * @description: 递归
     * @author: wg
     * @date: 15:30  2021/10/12
     ************************************************************************/
    public static ListNode mergeTwoListsRecursion(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoListsRecursion(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoListsRecursion(l1, l2.next);
            return l2;
        }
    }

    /************************************************************************
     * @description: 迭代 内存泄露
     * @author: wg
     * @date: 15:24  2021/10/12
     ************************************************************************/
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode prehead = new ListNode(-1);
        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = l1 == null ? l2 : l1;

        return prehead.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }
}
