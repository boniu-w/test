package wg.application.util;

/************************************************************************
 * author wg
 * description UnitConvertUtil 单位转换工具
 * createTime 17:05 2024/7/19
 * updateTime 17:05 2024/7/19
 ************************************************************************/
public class UnitConvertUtil {

    /**
     * @param temperatureCelsius 摄氏度
     * @return fahrenheit 华氏度
     * @author wg
     * @description 摄氏度 -> 华氏度
     * @createTime 16:53  2024/7/19
     * @updateTime 16:53  2024/7/19
     */
    public static double celsius2fahrenheit(double temperatureCelsius) {
        return temperatureCelsius * 9 / 5 + 32;
    }

    public static double fahrenheit2celsius(double temperatureFahrenheit) {
        return (temperatureFahrenheit - 32) * 5 / 9;
    }

    /**
     * @param temperatureCelsius 摄氏度
     * @return kelvin 开尔文
     * @author wg
     * @description 摄氏度 -> 开尔文
     * @createTime 16:53  2024/7/19
     * @updateTime 16:53  2024/7/19
     */
    public static double celsius2kelvin(double temperatureCelsius) {
        return temperatureCelsius + 273.15;
    }

    public static double kelvin2celsius(double temperatureKelvin) {
        return temperatureKelvin - 273.15;
    }

    /**
     * @param MPa 是兆帕（Megapascal）
     * @return psi 是磅力每平方英寸（pounds per square inch）
     * @author wg
     * @description 兆帕 转 psi
     * @createTime 17:13  2024/7/19
     * @updateTime 17:13  2024/7/19
     */
    public static double MPa2psi(double MPa) {
        return MPa * 145.037;
    }

    /**
     * @param psi 是磅力每平方英寸（pounds per square inch）
     * @return MPa 是兆帕（Megapascal）
     * @author wg
     * @description psi 转 兆帕
     * @createTime 17:16  2024/7/19
     * @updateTime 17:16  2024/7/19
     */
    public static double psi2MPa(double psi) {
        return psi / 145.037;
    }

    /**
     * @param inch 英寸
     * @return 毫米
     * @author wg
     * @description 英寸 -> mm
     * @createTime 10:27  2024/8/6
     * @updateTime 10:27  2024/8/6
     */
    public static double tomm(double inch) {
        return inch * 25.4;
    }

    public static void main(String[] args) {
        double v = celsius2fahrenheit(25);
        System.out.println("v = " + v); // 77

        double v1 = fahrenheit2celsius(77);
        System.out.println("v1 = " + v1); // 25

        double tomm = tomm(12);
        System.out.println("tomm = " + tomm);
    }

    public void test(){}
}
