package wg.application.exception;

public class ExceptionTest {

    /************************************************************************
     * @description: 编译不通过
     * @author: wg
     * @date: 9:53  2021/11/22
     * @params:
     * @return:
     ************************************************************************/
    // public void test1() throws Exception {
    //
    //     throw new Exception("");
    //     System.out.println("-------");
    // }

    /************************************************************************
     * @description: throws 不会继续了
     * @author: wg
     * @date: 9:56  2021/11/22
     * @params:
     * @return:
     ************************************************************************/
    public static void test2() throws Exception {
        int i = 2 / 0;

        System.out.println("test2  ");
    }

    /************************************************************************
     * @description:
     * @author: wg
     * @date: 9:56  2021/11/22
     * @params:
     * @return:
     ************************************************************************/
    public static void test3() {
        try {
            int i = 2 / 0;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("test2  ");
    }

    public static void main(String[] args) {
        // try {
        //     test2();
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }

        // try {
        test3();
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }
    }
}
