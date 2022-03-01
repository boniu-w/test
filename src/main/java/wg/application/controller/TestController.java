package wg.application.controller;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/11/3 17:43
 * @version
 * @Copyright
 *************************************************************/
public class TestController {


    private static int s = 1;

    public static void main(String[] args) {
        System.out.println(s);
        var();
    }

    /****************************************************************
     * 全局声明
     * @author: wg
     * @time: 2020/11/3 17:42
     ****************************************************************/
    public static void var() {
        System.out.println(s);
        s = 10;
    }
}
