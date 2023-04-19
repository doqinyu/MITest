package test.test2;

public class Main2 {

    /**
     * 对两个链表进行求和
     * @param h1
     * @param h2
     * @return
     */
    public static ListNode merge (ListNode h1, ListNode h2) {
        //首先逆转两个链表
        ListNode n1 = reverse(h1);
        ListNode n2 = reverse(h2);
        //创建空的头节点
        ListNode res = new ListNode();
        ListNode tail = res;
        ListNode p = n1;
        ListNode q = n2;
        int cnt = 0;
        while (p != null || q != null || cnt > 0) {
            int pValue = p == null ? 0: p.val;
            int qValue = q == null ? 0 : q.val;
            int sum = pValue + qValue + cnt;
            //创建和的节点，并更新尾节点
            ListNode cur = new ListNode(sum%10);
            System.out.println(cur.val);
            tail.next = cur;
            tail = cur;
            //更新下一轮的计算值
            cnt = sum /10;
            p = p == null ? null : p.next;
            q = q == null ? null : q.next;

        }
        //返回链表的和
        return res.next;
    }

    /**
     * 逆转链表
     * @return
     */
    public static ListNode reverse(ListNode h) {
        //如果是空链表或者只有一个节点，那么无需逆转
        if (h == null || h.next == null) {
            return h;
        }
        ListNode newHead = h;
        ListNode p = h.next;
        newHead.next = null;
        while (p != null) {
            //首先记录下一个待逆转的节点
            ListNode q = p.next;
            //将p节点插入到newHead的前面，更新newHead
            p.next = newHead;
            newHead = p;
            p = q;
        }
        return newHead;
    }

    /**
     * 根据num数组创建链表
     * @param num
     * @return
     */
    public static ListNode create(int[] num) {
        ListNode head = new ListNode();
        ListNode tail = head;
        for (int i = 0; i < num.length; i++) {
            ListNode cur = new ListNode(num[i]);
            tail.next = cur;
            tail = cur;
        }

        return head.next;
    }

    public static void main(String[] args) {
        int[] n1 = new int[] {7,2,4,3};
        int[] n2 = new int[] {5,6,4};
        ListNode h1 = create(n1);
        ListNode h2 = create(n2);
        ListNode sum = merge(h1,h2);
    }
}
