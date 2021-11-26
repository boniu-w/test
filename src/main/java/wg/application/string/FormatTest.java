package wg.application.string;

public class FormatTest {

    public static void main(String[] args) {
        test1();
    }

    /************************************************************************
     * @description: 左对齐
     * @author: wg
     * @date: 10:15  2021/11/26
     * @params:
     * @return:
     ************************************************************************/
    public static void test1() {
        for (int i = 0; i < 10; i++) {
            System.out.print(String.format("%-10s", 123123123));
        }
    }
}
