package wg.application.test;

import wg.application.entity.User;

/************************************************************************
 * author wg
 * description Test00 
 * createTime 14:44 2024/8/5
 * updateTime 14:44 2024/8/5
 ************************************************************************/
public class Test00 {
    /**
     * @param
     * @return
     * @author wg
     * @description 总体积流速
     * @createTime 14:38  2024/8/5
     * @updateTime 14:38  2024/8/5
     */
    public static double speed(double _v_ls, double _v_gs, double area) {
        double _Up = (_v_ls + _v_gs) / area;                                                // 总体积流速 (mm/s)
        return _Up;
    }

    /**
     * @param
     * @return
     * @author wg
     * @description 冲蚀临界流速
     * @createTime 14:56  2024/8/5
     * @updateTime 14:56  2024/8/5
     */
    public static double tt(double liquidGravity, double pressure, double gasLiquidRatio, double gasGravity,
                            double temperature, double compressibility, double empiricalConstant) {
        // 总密度
        double totalDensity = (12409 * liquidGravity * pressure + 2.7 * gasLiquidRatio * gasGravity * pressure) / (198.7 * pressure + gasLiquidRatio * temperature * compressibility);
        return empiricalConstant * 0.3048 / Math.sqrt(totalDensity);
    }

    public static void main(String[] args) {
        double a = 0d;
        double b = 0d;
        double c = 0d;
        double speed = speed(a, b, c);
        System.out.println("speed = " + speed);

        testTransfer();
    }

    public static void testTransfer() {
        String a = "";

        User user = new User();
        user.setName(a);

        setA(a, user);

        System.out.println("a = " + a); // a =""
        System.out.println("user = " + user); // user = User{id=null, name='111', age=null, birthday=null, gender='null', wealth=null, createTime=null, updateTime=null}

        String b= new String("");
        setA(b, user);
        System.out.println("b = " + b); // b = ""

        setAA(b, user);
        System.out.println("b = " + b); // b =""
    }

    public static void setA(String a, User user) {
        a = "111";
        user.setName(a);
    }

    public static void setAA(String a, User user) {
        a = new String("111");
        user.setName(a);
    }
}
