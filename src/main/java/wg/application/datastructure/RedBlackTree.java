package wg.application.datastructure;

import java.util.Scanner;

public class RedBlackTree {

    static Node NIL = new Node();

    // 初始化NIL节点 值为0，颜色为黑色
    private static void init_NIL() {
        NIL.key = 0;
        NIL.color = 1;
        // 把NIL的左子树和右子树都指向自己 这样即使操作NIL的左子树和右子树也不会出现异常
        NIL.lChild = NIL.rChild = NIL;
    }

    // 获取节点 只传入key，默认颜色为红色
    private static Node getNewNode(int key) {
        Node node = new Node();
        node.key = key;
        node.lChild = node.rChild = NIL;
        return node;
    }

    // 插入节点
    private static Node insert(Node root, int key) {
        root = __insert(root, key);
        root.color = 1; // 根节点必须是黑色
        return root;
    }

    // 真正插入节点的方法
    private static Node __insert(Node root, int key) {
        // 如果遍历到NIL节点，则说明可以执行插入操作
        if (root == NIL) return getNewNode(key);
        if (key == root.key) return root;
        if (key < root.key) {
            root.lChild = __insert(root.lChild, key);
        } else {
            root.rChild = __insert(root.rChild, key);
        }
        return insert_maintain(root); // 回溯过程中调整平衡
    }

    // 插入操作调整 => 平衡
    private static Node insert_maintain(Node root) {
        int flag = 0; // 1:R型失衡(RR,RL), 2:L型失衡(LL,LR)
        // 判断当前左儿子是否为红色，且两个孙子是否有红色
        if (root.lChild.color == 0 && has_red_color(root.lChild)) flag = 1; // L型失衡
        // 判断当前右儿子是否为红色，且两个孙子是否有红色
        if (root.rChild.color == 0 && has_red_color(root.rChild)) flag = 2; // R型失衡
        // 未失衡
        if (flag == 0) return root;

        // 此时一定失衡

        // 情况1：红红冲突，叔叔节点为红色
        if (root.lChild.color == 0 && root.rChild.color == 0) {
            // 红黑黑
            root.color = 0;
            root.lChild.color = root.rChild.color = 1;
            return root;
        }
        // 情况2：红红冲突，叔叔节点为黑色 此时为L型或R型失衡 需要左/右旋
        if (flag == 1) { // L型失衡
            if (root.lChild.rChild.color == 0) { // LR
                root.lChild = left_rotate(root.lChild); // 左旋
            }
            // LL 右旋
            root = right_rotate(root);
        } else { // R型失衡
            if (root.rChild.lChild.color == 0) { // RL
                root.rChild = right_rotate(root.rChild); // 右旋
            }
            // RR 左旋
            root = left_rotate(root);
        }
        // 此时只是进行了修改，还未进行染色
        // 染色我们使用“红色上浮” => 红黑黑
        root.color = 0;
        root.lChild.color = root.rChild.color = 1;
        return root;
    }

    // 左旋
    private static Node left_rotate(Node root) {
        Node temp = root.rChild;
        root.rChild = temp.lChild;
        temp.lChild = root;
        return temp;
    }

    // 右旋
    private static Node right_rotate(Node root) {
        Node temp = root.lChild;
        root.lChild = temp.rChild;
        temp.rChild = root;
        return temp;
    }

    // 判断该节点下面是否挂着红色节点
    private static boolean has_red_color(Node root) {
        return root.lChild.color == 0 || root.rChild.color == 0;
    }

    // 前序遍历打印
    private static void output(Node root) {
        if (root == NIL) return;
        // 先输出颜色，再输出值，再输出左子树和右子树的值
        System.out.println("( " + root.color + "|" + root.key + " " + root.lChild.key + " " + root.rChild.key + " )");
        output(root.lChild);
        output(root.rChild);
    }

    public static void main(String[] args) {
        init_NIL();
        Node root = NIL;
        Scanner sc = new Scanner(System.in);
        while (true) {
            int val = sc.nextInt();
            root = insert(root, val);
            System.out.println("=== rbtree print ===");
            output(root); // 前序遍历打印
            System.out.println("=== rbtree print done ===");
        }
    }

    static class Node {

        int key; // 当前节点值
        int color; // 当前红黑树节点的颜色 0:red, 1:black
        Node lChild, rChild; // 左右节点

        public Node() {
        }
    }
}


