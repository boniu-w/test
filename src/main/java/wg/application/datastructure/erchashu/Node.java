package wg.application.datastructure.erchashu;

/**
 * 二叉树节点
 * 二叉搜索树
 */
public class Node {
 
    public int data;
 
    public Node leftNode;
 
    public Node rightNode;
 
    public Node(int key) {
        data = key;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }
}