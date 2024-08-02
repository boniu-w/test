package wg.application.util;

import wg.application.enumeration.PhysicsEnum;
import wg.application.exception.WgException;

/************************************************************************
 * author wg
 * description PressureUtil 
 * createTime 11:09 2024/7/22
 * updateTime 11:09 2024/7/22
 ************************************************************************/
public class PressureUtil {

    /**
     * @param inletPressure  入口压力
     * @param outletPressure 出口压力
     * @param inletKp        起始里程
     * @param outletKp       终止里程
     * @return 斜率
     * @author wg
     * @description 压力 斜率
     * @createTime 11:05  2024/7/22
     * @updateTime 11:05  2024/7/22
     */
    public static double calculateSlope(double inletPressure, double outletPressure, double inletKp, double outletKp) {
        if ((outletKp - inletKp) == 0) {
            throw new WgException("除数为0");
        }
        return (outletPressure - inletPressure) / (outletKp - inletKp);
    }

    /**
     * @param slope         斜率
     * @param inletPressure 入口压力
     * @param offset        偏移量(里程)
     * @return 点位处的压力
     * @author wg
     * @description 由斜率计算 点位处的压力
     * @createTime 11:06  2024/7/22
     * @updateTime 11:06  2024/7/22
     */
    public static double calculateData(double slope, double inletPressure, double offset) {
        if (offset < 0) {
            throw new WgException("请检查里程点是否正确");
        }
        return slope * offset + inletPressure;
    }

    /**
     * @param carbonDioxideMoleFraction 气体中的CO2摩尔分数 (0.xxx)
     * @param currentPressure           压力[pa]
     * @return 二氧化碳 分压
     * @author wg
     * @description 二氧化碳 分压
     * @createTime 16:11  2024/7/22
     * @updateTime 16:11  2024/7/22
     */
    public static double carbonDioxidePartialPressure(double carbonDioxideMoleFraction, double currentPressure) {
        return carbonDioxideMoleFraction * currentPressure;
    }

    /**
     * @param currentPressure 压力 (pa)
     * @param gasMoleFraction 气体中的CO2摩尔分数 (0.xxx)
     * @return 分压
     * @author wg
     * @description 干气 分压
     * @createTime 16:11  2024/7/22
     * @updateTime 16:11  2024/7/22
     */
    private static double gasPartialPressure(double gasMoleFraction, double currentPressure) {
        return gasMoleFraction * currentPressure;
    }

    /**
     * @param _Pin       入口压力[Pa]
     * @param rou        容量密度[kg/m3]
     * @param depthInlet 入口深度[m]
     * @param depthKp    kp处深度[m]
     * @return kp处压力[Pa]
     * @author wg
     * @description 根据深度 计算kp处压力
     * @createTime 10:24  2024/8/2
     * @updateTime 10:24  2024/8/2
     */
    public static double kpPressure(double _Pin, double rou, double depthInlet, double depthKp) {
        return _Pin + rou * PhysicsEnum.G.getValue() * (depthKp - depthInlet);
    }


    public static void main(String[] args) {
        double _Pin = 6 * Math.pow(10, 6);
        double rou = 800;
        double din = 90;
        double dkp = 85;
        double v = kpPressure(_Pin, rou, din, dkp);
        System.out.println("根据深度 计算kp处压力 = " + v / Math.pow(10, 6));

        double outletPressure = 4 * Math.pow(10, 6);
        double pressureSlope = PressureUtil.calculateSlope(_Pin, outletPressure, 0, 1000); // 压力斜率
        System.out.println("斜率 = " + pressureSlope / Math.pow(10, 6));
        double v1 = calculateData(pressureSlope, _Pin, 500);
        System.out.println("由斜率计算 点位处的压力 = " + v1);
    }
}
