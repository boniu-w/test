package wg.application.staticFolder;

/************************************************************************
 * @author: wg
 * @description: 代码块 测试
 * @createTime: 15:21 2022/4/21
 * @updateTime: 15:21 2022/4/21
 ************************************************************************/
public class StaticFolder {

    public static void main(String[] args) {
        {
            int x = 3;
            System.out.println("普通代码块内的变量x=" + x);
        }
        int x = 1;
        System.out.println("主方法内的变量x=" + x);
        {
            int y = 7;
            System.out.println("普通代码块内的变量y=" + y);
        }
    }
}
