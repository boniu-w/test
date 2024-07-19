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

    public static void main(String[] args) {
        double v = celsius2fahrenheit(25);
        System.out.println("v = " + v); // 77

        double v1 = fahrenheit2celsius(77);
        System.out.println("v1 = " + v1); // 25
    }
}
