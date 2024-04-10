package wg.application.datastructure.bplustree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/************************************************************************
 * author: wg
 * description: BPlusTree
 * B+树是一种自平衡的树型数据结构，它常被用于数据库和文件系统中作为索引结构。B+树与B树类似，但在B+树中，非叶子节点不存储数据，只存储索引，
 * 所有的数据都存储在叶子节点上。这样的设计使得B+树更适合用于范围查询。
 * createTime: 16:25 2024/4/9
 * updateTime: 16:25 2024/4/9
 ************************************************************************/
public class BPlusTree {

    private Node root;
    private int degree;

    private class Node {
        ArrayList<Integer> keys;
        ArrayList<Node> children;
        boolean leaf;

        Node() {
            keys = new ArrayList<>();
            children = new ArrayList<>();
            leaf = false;
        }
    }

    BPlusTree(int degree) {
        this.root = new Node();
        this.degree = degree;
    }

    void insert(int key) {
        if (root.keys.size() == degree - 1) {
            Node newRoot = new Node();
            newRoot.children.add(root);
            splitChild(newRoot, 0);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(Node node, int key) {
        if (node.leaf) {
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) {
                i++;
            }
            node.keys.add(i, key);
        } else {
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) {
                i++;
            }
            if (node.children.get(i).keys.size() == degree - 1) {
                splitChild(node, i);
                if (key > node.keys.get(i)) {
                    i++;
                }
            }
            insertNonFull(node.children.get(i), key);
        }
    }

    private void splitChild(Node parent, int index) {
        Node child = parent.children.get(index);
        Node newChild = new Node();
        newChild.leaf = child.leaf;

        parent.keys.add(index, child.keys.get(degree / 2));
        for (int i = degree / 2 + 1; i < child.keys.size(); i++) {
            newChild.keys.add(child.keys.get(i));
        }
        for (int i = degree / 2 + 1; i < child.children.size(); i++) {
            newChild.children.add(child.children.get(i));
        }

        child.keys.subList(degree / 2, child.keys.size()).clear();
        child.children.subList(degree / 2 + 1, child.children.size()).clear();

        parent.children.add(index + 1, newChild);
    }

    boolean search(int key) {
        return searchKey(root, key);
    }

    private boolean searchKey(Node node, int key) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i)) {
            i++;
        }
        if (i < node.keys.size() && key == node.keys.get(i)) {
            return true;
        }
        if (node.leaf) {
            return false;
        }
        return searchKey(node.children.get(i), key);
    }

    void levelOrderTraversal() {
        if (root == null) return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                System.out.print("[ ");
                for (int j = 0; j < node.keys.size(); j++) {
                    System.out.print(node.keys.get(j) + " ");
                }
                System.out.print("] ");
                if (!node.leaf) {
                    for (Node child : node.children) {
                        queue.add(child);
                    }
                }
            }
            System.out.println();
        }
    }
}

class Main {
    public static void main(String[] args) {
        BPlusTree bPlusTree = new BPlusTree(3);

        bPlusTree.insert(1);
        bPlusTree.insert(2);
        bPlusTree.insert(3);
        bPlusTree.insert(4);
        bPlusTree.insert(5);
        bPlusTree.insert(6);
        bPlusTree.insert(7);
        bPlusTree.insert(8);
        bPlusTree.insert(9);

        System.out.println("B+ Tree structure:");
        bPlusTree.levelOrderTraversal();

        int searchKey = 5;
        System.out.println("Search for key " + searchKey + ": " + bPlusTree.search(searchKey));
    }
}
