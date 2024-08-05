package wg.application.util;

/************************************************************************
 * author wg
 * description TemperatureUtil 
 * createTime 11:10 2024/7/22
 * updateTime 11:10 2024/7/22
 ************************************************************************/
public class TemperatureUtil {

    /**
     * @param inletTemperature   入口温度 (摄氏度)
     * @param outletTemperature  出口温度 (摄氏度)
     * @param ambientTemperature 环境温度 (摄氏度)
     * @param length             管道总长度 (m)
     * @return 温降系数
     * @author wg
     * @description 根据入口温度, 出口温度, 环境温度,管道长度 算温降系数
     * @createTime 10:34  2024/6/28
     * @updateTime 10:34  2024/6/28
     */
    public static double getThermalDecayConstant(double inletTemperature, double outletTemperature, double ambientTemperature, double length) {
        double d = 1E-3;
        if (outletTemperature > ambientTemperature) {
            d = outletTemperature - ambientTemperature;
        }
        return -(Math.log(d / (inletTemperature - ambientTemperature)) / length);
    }

    /**
     * @param inletTemperature     入口温度 (摄氏度)
     * @param ambientTemperature   环境温度 (摄氏度)
     * @param thermalDecayConstant 温降系数
     * @param offset               偏移量(里程)
     * @return 里程处de实际温度
     * @author wg
     * @description 里程处de实际温度 毛工算法
     * @createTime 11:13  2024/7/22
     * @updateTime 11:13  2024/7/22
     */
    public static double drop(double inletTemperature, double ambientTemperature, double thermalDecayConstant, double offset) {
        return ambientTemperature + (inletTemperature - ambientTemperature) * Math.exp(-thermalDecayConstant * offset);
    }

    /**
     * @param inletTemperature     入口温度 (摄氏度)
     * @param ambientTemperature   环境温度 (摄氏度)
     * @param thermalDecayConstant 温降系数
     * @param _L                   管道长度
     * @param _kp                  kp
     * @return 里程处de实际温度
     * @author wg
     * @description 里程处de实际温度 co2腐蚀里的算法, 从测试来看这个算法应该是错的
     * double _T = _Tamb + (_Tin - _Tamb) * Math.exp(-beta * (_L - _Kp) / _L);         // Kp数时的管道温度 (摄氏度)
     * @createTime 14:14  2024/7/23
     * @updateTime 14:14  2024/7/23
     */
    public static double drop(double inletTemperature, double ambientTemperature, double thermalDecayConstant, double _L, double _kp) {
        return ambientTemperature + (inletTemperature - ambientTemperature) * Math.exp(-thermalDecayConstant * (_L - _kp) / _L);
    }

    /**
     * @param inletTemperature   入口温度 [摄氏度]
     * @param ambientTemperature 环境温度 [摄氏度]
     * @param beta               热衰减常数
     * @return 出口温度
     * @author wg
     * @description chartgpt 公式
     * 这个公式常见于描述热力学中的牛顿冷却定律，用于计算物体在一定环境温度下如何随时间冷却或加热。
     * exp(−βt) 表示一个指数衰减过程，描述了物体温度逐渐趋近环境温度的情况。
     * 这个公式描述了温度随时间的变化，通常用于分析热传导或冷却过程。具体来说，这是一个用于描述物体在环境温度下冷却的公式
     * @createTime 15:25  2024/8/2
     * @updateTime 15:25  2024/8/2
     */
    public static double drop1(double inletTemperature, double ambientTemperature, double beta, double t) {
        return ambientTemperature + (inletTemperature - ambientTemperature) * Math.exp(-beta * t);
    }

    /**
     * @param inletTemperature   入口温度 [摄氏度]
     * @param ambientTemperature 环境温度 [摄氏度]
     * @param beta               热衰减常数
     * @param _L                 管道长度
     * @return 出口温度
     * @author wg
     * @description 通译千问 公式
     * @createTime 15:31  2024/8/2
     * @updateTime 15:31  2024/8/2
     */
    public static double drop2(double inletTemperature, double ambientTemperature, double beta, double _L) {
        return ambientTemperature + (inletTemperature - ambientTemperature) * Math.exp(3 / (-beta * _L));
    }

    /**
     * @param inletTemperature   入口温度 [摄氏度]
     * @param ambientTemperature 环境温度 [摄氏度]
     * @param beta               热衰减常数
     * @return 出口温度
     * @author wg
     * @description mathcad 公式
     * @createTime 15:32  2024/8/2
     * @updateTime 15:32  2024/8/2
     */
    public static double drop3(double inletTemperature, double ambientTemperature, double beta) {
        return ambientTemperature + (inletTemperature - ambientTemperature) * Math.exp(-beta);
    }

    /**
     * @param _Tamb 环境温度 [摄氏度]
     * @param _Tin  入口温度 [摄氏度]
     * @param beta  热衰减常数
     * @param _L    管道长度[m]
     * @return 出口温度
     * @author wg
     * @description 根据入口温度, 环境温度, 温降系数, 管道长度 算出口温度
     * @createTime 10:38  2024/8/2
     * @updateTime 10:38  2024/8/2
     */
    public static double temperatureOutlet(double _Tamb, double _Tin, double beta, double _L) {
        return _Tamb + (_Tin - _Tamb) * Math.exp(-beta * _L);
    }

    /**
     * @param centigrade 摄氏度
     * @return 列氏度
     * @author wg
     * @description 摄氏度 转 列氏度
     * @createTime 15:15  2024/8/5
     * @updateTime 15:15  2024/8/5
     */
    public static double convert2Reaumur(double centigrade) {
        return 4 / 5.0 + (centigrade + 80);
    }

    /**
     * @param reaumur 列氏度
     * @return 摄氏度
     * @author wg
     * @description 列氏度 转 摄氏度
     * @createTime 15:15  2024/8/5
     * @updateTime 15:15  2024/8/5
     */
    public static double convert2centigrade(double reaumur) {
        return reaumur * 5 / 4.0 - 80;
    }

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

    /**
     * @param temperatureFahrenheit 华氏度
     * @return 摄氏度
     * @author wg
     * @description 华氏度 -> 摄氏度
     * @createTime 15:23  2024/8/5
     * @updateTime 15:23  2024/8/5
     */
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

    /**
     * @param temperatureKelvin 开尔文
     * @return 摄氏度
     * @author wg
     * @description 开尔文 -> 摄氏度
     * @createTime 15:24  2024/8/5
     * @updateTime 15:24  2024/8/5
     */
    public static double kelvin2celsius(double temperatureKelvin) {
        return temperatureKelvin - 273.15;
    }

    public static void main(String[] args) {
        double _K = 3.169786384922225 * Math.pow(10, -17);
        System.out.println("_K = " + _K);

        // 温降系数
        double inletTemperature = 60;
        double outletTemperature = 40;
        double ambientTemperature = 38;
        double length = 1000;
        double thermalDecayConstant = getThermalDecayConstant(inletTemperature, outletTemperature, ambientTemperature, length);
        System.out.println("thermalDecayConstant = " + thermalDecayConstant);
        double drop = drop(inletTemperature, ambientTemperature, thermalDecayConstant, 800);
        System.out.println("毛工 kp处温度 = " + drop);

        double drop1 = drop(inletTemperature, ambientTemperature, thermalDecayConstant, 1000, 200);
        System.out.println("我的 kp处温度 = " + drop1);

        double v = temperatureOutlet(ambientTemperature, inletTemperature, thermalDecayConstant, length);
        System.out.println("出口温度 = " + v);

        double beta = 5d; // 热衰减常数
        double v1 = drop1(95, 6, beta, 10);
        System.out.println("chartgpt 公式 = " + v1);

        double exp = Math.exp(-beta * 1000);
        double exp1 = Math.exp(-beta * 1); // 等同与 Math.pow(Math.E, -beta * 1);
        System.out.println("exp = " + exp);
        System.out.println("exp1 = " + exp1);
        double pow = Math.pow(Math.E, -beta * 1);
        System.out.println("pow = " + pow);

        double v12 = drop2(95, 6, beta, 10000);
        System.out.println("通译千问 = " + v12);

        double vv3 = drop3(95, 6, beta);
        System.out.println("mathcad 公式 = " + vv3);

        // 总结: 通译千问 mathcad chartgpt 公式都不对

    }
}
