package wg.application.datastructure.erchashu;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 13:34 2022/11/7
 * @updateTime: 13:34 2022/11/7
 ************************************************************************/
public class TreeTest {

    public static void main(String[] args) {
        Tree t = new Tree();
        t.insert(70);
        t.insert(100);
        t.insert(90);
        t.insert(80);
        t.insert(91);
        t.insert(9);
        t.insert(81);
        t.insert(4);
        Node node = t.find(100);
        System.out.println(node.leftNode.data);
        System.out.println(t.count());
        System.out.println(t);
    }
}
