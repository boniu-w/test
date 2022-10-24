package wg.application.lamda;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:25 2022/10/24
 * @updateTime: 15:25 2022/10/24
 ************************************************************************/
public class MyLamda {

    public static void main(String[] args) {
        String execute = execute((a) -> String.valueOf(a + 33), 22);
        System.out.println(execute); // 551239
    }

    public static String execute(LamdaTest lamdaTest, int a) {
        String say = lamdaTest.say(a);
        String sss = say + "1239";
        return sss;
    }


    // public static void main(String[] args) {
    //     LamdaTest lamdaTest = () -> "123";
    //     String say = lamdaTest.say();
    //     System.out.println(say);
    // }


    @FunctionalInterface
    public interface LamdaTest {

        String say(int a);
    }
}

