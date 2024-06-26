package wg.application.datastructure;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*****************************************
 * description: 二叉树的代码实现, 感觉不太对
 * date: 15:30 2021/7/21
 * auth: wg
 *****************************************/
class Tree<T> {

    class TreeNode<T> {
        T value;
        TreeNode<T> leftChild;
        TreeNode<T> rightChild;

        TreeNode(T value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return this.value.equals(((TreeNode<?>) obj).value);
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    ", leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    '}';
        }
    }

    private TreeNode<T> rootNode;//根结点

    @Override
    public String toString() {
        return "Tree{" +
                "rootNode=" + rootNode +
                '}';
    }

    /**
     * 查询该树的深度
     */
    private int getDepth(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = getDepth(node.leftChild) + 1;
        int rightDepth = getDepth(node.rightChild) + 1;
        return Math.max(leftDepth, rightDepth);
    }

    public int getTreeDepth() {
        return getDepth(this.rootNode);
    }

    /**
     * 查询结点总数
     */
    private int getCount(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        return getCount(node.leftChild) + getCount(node.rightChild) + 1;
    }

    public int getTreeNum() {
        return getCount(this.rootNode);
    }

    /**
     * 前序遍历 根左右 先输出根在输出左最后输出右
     */
    private void pre(TreeNode<T> node) {
        if (node == null) return;
        System.out.print(node.value + "\t");
        pre(node.leftChild);
        pre(node.rightChild);
    }

    public void preOrderTravel() {
        pre(this.rootNode);
    }

    /**
     * 后序遍历 左右根 先输出左 在输出右 最后输出根
     */
    private void back(TreeNode<T> node) {
        if (node == null) return;
        back(node.leftChild);
        back(node.rightChild);
        System.out.print(node.value + "\t");
    }

    public void backOrderTravel() {
        back(this.rootNode);
    }

    /**
     * 中序遍历 左根右
     */
    private void middle(TreeNode<T> node) {
        if (node == null) return;
        middle(node.leftChild);
        System.out.print(node.value + "\t");
        middle(node.rightChild);
    }

    public void middleOrderTravel() {
        middle(this.rootNode);
    }

    /**
     * 层次遍历 例如一个二叉树的层次为
     * A
     * /   \
     * B     C
     * /\    /\
     * D  E  F  G
     * 需要用到队列  先将A入队列 然后取出A 得到左右结点 ，将左右结点入队列 然后左结点出队列 再将左节点的左右结点入队列...如此反复就能对二叉树进行层次遍历了
     * 例如上面的二叉树 A入队列 A出队列 BC入队列 B出队列 DE入队列 C出队列 FG入队列 D出队列 E出队列 F出队列 G出队列 出队列就将该元素输出，即ABCDEFG
     * java中使用LinkedList实现栈和队列
     */
    public void levelTravel() {
        if (rootNode == null) throw new IllegalArgumentException("树为空");
        Queue<TreeNode<T>> q = new LinkedList<TreeNode<T>>();
        q.offer(rootNode);//入队列
        while (!q.isEmpty()) {
            TreeNode<T> node = q.poll();//出队列
            System.out.print(node.value + "\t");
            if (node.leftChild != null) {
                q.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                q.offer(node.rightChild);
            }
        }
    }

    /**
     * 求第k层有多少个结点
     */
    private int getNum(TreeNode<T> root, int k) {
        if (root == null || k < 1) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        int leftNum = getNum(root.leftChild, k - 1);
        int rightNum = getNum(root.rightChild, k - 1);
        return leftNum + rightNum;
    }

    public int getNumForKlevel(int k) {
        return getNum(rootNode, k);
    }

    /**
     * 求叶子结点个数 叶子结点为没有左结点和右结点的结点
     */
    private int getLeaf(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        if (root.leftChild == null && root.rightChild == null) {
            return 1;
        }
        int leftNum = getLeaf(root.leftChild);
        int rightNum = getLeaf(root.rightChild);
        return leftNum + rightNum;
    }

    public int getLeafNum() {
        return getLeaf(rootNode);
    }

    /**
     * 交换根结点的左右子树
     */
    private TreeNode<T> exchange(TreeNode<T> root) {
        if (root == null) {
            return null;
        }
        TreeNode<T> left = exchange(root.leftChild);
        TreeNode<T> right = exchange(root.rightChild);
        root.leftChild = right;
        root.rightChild = left;
        return root;
    }

    public TreeNode<T> change() {
        return exchange(rootNode);
    }

    /**
     * 查询是否存在指定结点
     */
    private boolean exist(TreeNode<T> root, T obj) {
        if (root == null || obj == null) {
            return false;
        }
        if (root.value.equals(obj)) {
            return true;
        }
        boolean isExist = exist(root.leftChild, obj);
        if (!isExist) {
            isExist = exist(root.rightChild, obj);
        }
        return isExist;
    }

    public boolean isExist(T node) {
        return exist(rootNode, node);
    }

    /**
     * 求两个结点最近的公共结点
     */
    private TreeNode<T> getNode(TreeNode<T> root, T lNode, T rNode) {
        if ((root.value.equals(lNode) && exist(root, rNode)) || (root.value.equals(rNode) && exist(root, lNode))) {
            return root;
        }
        if (root == null || lNode == null || rNode == null) {
            return null;
        }
        // 如果lNode是左子树的节点
        if (exist(root.leftChild, lNode)) {
            if (exist(root.rightChild, rNode)) {
                return root;
            } else {
                return getNode(root.leftChild, lNode, rNode);
            }
        } else {
            if (exist(root.leftChild, rNode)) {
                return root;
            } else {
                return getNode(root.rightChild, lNode, rNode);
            }
        }
    }

    public TreeNode<T> findFatherNode(T lNode, T rNode) {
        return getNode(rootNode, lNode, rNode);
    }

    /**
     * 根据前序和中序构建二叉树
     */
    private TreeNode<T> getTreeFromPreAndMid(List<T> pre, List<T> mid) {
        if (pre == null || mid == null || pre.size() == 0 || mid.size() == 0) {
            return null;
        }
        if (pre.size() == 1) {
            return new TreeNode<T>(pre.get(0));
        }
        TreeNode<T> root = new TreeNode<T>(pre.get(0));
        // 找出根节点在中序中的位置
        int index = 0;
        while (!mid.get(index++).equals(pre.get(0))) {
        }
        // 构建左子树的前序
        List<T> preLeft = new ArrayList<T>();
        // 左子树的中序
        List<T> midLeft = new ArrayList<T>();
        for (int i = 1; i < index; i++) {
            preLeft.add(pre.get(i));
        }
        for (int i = 0; i < index - 1; i++) {
            midLeft.add(mid.get(i));
        }
        // 重建左子树
        root.leftChild = getTreeFromPreAndMid(preLeft, midLeft);
        // 右子树的前序
        List<T> preRight = new ArrayList<T>();
        // 右子树的中序
        List<T> midRight = new ArrayList<T>();
        for (int i = 0; i <= pre.size() - index - 1; i++) {
            preRight.add(pre.get(index + i));
        }
        for (int i = 0; i <= pre.size() - index - 1; i++) {
            midRight.add(mid.get(index + i));
        }
        // 重建→子树
        root.rightChild = getTreeFromPreAndMid(preRight, midRight);
        return root;
    }

    public Tree(List<T> pre, List<T> mid) {
        this.rootNode = getTreeFromPreAndMid(pre, mid);
    }

    /**
     * 比较该树是否与其他树相等
     */
    private boolean eq(TreeNode<T> root, TreeNode<T> value) {
        if (root == null && value == null) {
            return true;
        }
        if (root == null || value == null) {
            return false;
        }
        boolean isEqual = root.value.equals(value.value);
        boolean isLeftEqual = eq(root.leftChild, value.leftChild);
        boolean isRightEqual = eq(root.rightChild, value.rightChild);
        return isEqual && isLeftEqual && isRightEqual;
    }

    public boolean isEq(Tree<T> tree) throws NoSuchFieldException, IllegalAccessException {
        Field field = Tree.class.getDeclaredField("rootNode");
        field.setAccessible(true);
        TreeNode<T> value = (TreeNode<T>) field.get(tree);
        return eq(rootNode, value);
    }

    /**
     * 测试 该树的图如下           左右交换应该为
     * A                                  A
     * /  \                               /  \
     * B     C                             C    B
     * /     / \                          / \    \
     * D     E   F                        F   E    D
     * \                                         /
     * G                                       G
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<String> pre = new ArrayList<>();
        pre.add("A");
        pre.add("B");
        pre.add("D");
        pre.add("G");
        pre.add("C");
        pre.add("E");
        pre.add("F");
        List<String> mid = new ArrayList<>();
        mid.add("D");
        mid.add("G");
        mid.add("B");
        mid.add("A");
        mid.add("E");
        mid.add("C");
        mid.add("F");
        Tree<String> t = new Tree<>(pre, mid);
        Tree<String> t2 = new Tree<>(pre, mid);

        System.out.println(t);
        System.out.println(t2);

        System.out.println("前序遍历");
        t.preOrderTravel();
        System.out.println();
        System.out.println("中序遍历");
        t.middleOrderTravel();
        System.out.println();
        System.out.println("后序遍历");
        t.backOrderTravel();
        System.out.println();
        System.out.println("层次遍历");
        t.levelTravel();
        System.out.println();
        System.out.println("树的深度");
        System.out.println(t.getTreeDepth());
        System.out.println("树的第三层有多少个结点");
        System.out.println(t.getNumForKlevel(3));
        System.out.println("叶子结点的个数");
        System.out.println(t.getLeafNum());
        System.out.println("比较t1和t2是否相等");
        System.out.println(t.isEq(t2));
        System.out.println("查询是否存在结点B");
        System.out.println(t.isExist("B"));
        System.out.println("G F的公共父结点");
        System.out.println(t.findFatherNode("G", "F"));
        System.out.println("交换t的左右结点");
        t.change();
        System.out.println("交换后前序遍历");
        t.preOrderTravel();
        System.out.println();
        System.out.println("交换后中序遍历");
        t.middleOrderTravel();
        System.out.println();
        System.out.println("交换后后序遍历");
        t.backOrderTravel();
        System.out.println();
        System.out.println("交换后层次遍历");
        t.levelTravel();
    }
}

