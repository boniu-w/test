package wg.application.other;

/************************************************************************
 * author: wg
 * description: ObjectTest2 
 * createTime: 16:29 2024/4/26
 * updateTime: 16:29 2024/4/26
 ************************************************************************/
public class ConstructorTest {

    private String id = "111";

    public ConstructorTest() {
        System.out.println("构造器阶段 id = " + id); // 111 说明构造器阶段之前已经有值了
        id = "222";
    }

    public static void main(String[] args) {
        ConstructorTest test2 = new ConstructorTest();
        System.out.println("test2.id = " + test2.id); // 222
    }
}
