package wg.application.algorithm.chatgpt.redblacktree;

/************************************************************************
 * @author: wg
 * @description:
 * 红黑树是特殊的平衡二叉树，具有以下特性：
 * 1、根节点的颜色是黑色
 * 2、节点颜色要么是黑色、要么是红色
 * 3、如果一个节点的颜色是红色，则它的子节点必须是黑色，即不能有2个连续的红色节点
 * 4、每个叶子节点都是黑色(这里的叶子节点是为空的叶子节点)
 * 5、从一个节点到该节点的叶子节点的所有路径上都包含相同数量的黑色节点
 * @params:
 * @return:
 * @createTime: 9:19  2023/2/13
 * @updateTime: 9:19  2023/2/13
 ************************************************************************/
public class RedBlackTree<T extends Comparable<T>> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        T key;
        Node left, right;
        boolean color;

        Node(T key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    private Node root;

    public void insert(T key) {
        root = insert(root, key);
        root.color = BLACK;
    }

    private Node insert(Node h, T key) {
        if (h == null) return new Node(key, RED);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = insert(h.left, key);
        else if (cmp > 0) h.right = insert(h.right, key);
        else h.key = key;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }
}
