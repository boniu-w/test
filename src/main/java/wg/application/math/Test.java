package wg.application.math;

import org.apache.commons.math3.distribution.NormalDistribution;
import wg.application.enumeration.WaterTypeEnum;
import wg.application.exception.WgException;

import java.util.Random;

/************************************************************************
 * author: wg
 * description: Test 
 * createTime: 17:30 2024/5/20
 * updateTime: 17:30 2024/5/20
 ************************************************************************/
public class Test {
    public static void main(String[] args) {
        /*String materialType = "SS316";
        double T = 40;
        double t = 12.7;
        if (materialType == "SS316") {
            if (T < 35 || T > 90) {
                System.out.println();
            }
            if (T >= 35 && T <= 60) {
                double pof = 2e-5 + (1e-4 - 2e-5) / (60 - 35) * (T - 35);
                double v = pof / t;
                System.out.println("pof = " + v);


                double temp = slopeFormula(35, 60, 2e-5, 1e-4, T);
                v = temp / t;
                System.out.println("v = " + v);

            }
        }*/

       /*double v = calPoF12_wg(null, null, 0, 0);
        System.out.println("v = " + v);*/

        double v1 = calPoF15(null, 0, true);
        System.out.println("v1 = " + v1);
    }

    /**
     * @author: wg
     * @description: 斜率公式
     * @params:
     * @return:
     * @createTime: 9:58  2024/5/21
     * @updateTime: 9:58  2024/5/21
     */
    public static double slopeFormula(double x1, double x2, double y1, double y2, double T) {
        if (x2 - x1 == 0) {
            return 0;
        }
        double y = y1 + ((y2 - y1) / (x2 - x1)) * (T - x1);
        return y;
    }

    public static double calPoF12_wg(String typeofSeawater, String materialType, double temperature, double wallThickness) {
        double pof = 0, temp = 0;
        typeofSeawater = WaterTypeEnum.a.getName();
        materialType = "SS316";
        wallThickness = 12.7;
        temperature = 40;
        if (WaterTypeEnum.a.getName().equals(typeofSeawater)) {
            if ("SS316".equals(materialType)) {
                if (temperature >= 0 && temperature <= 10) {
                    temp = slopeFormula(0, 10, 1e-4, 0.1, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 10 && temperature <= 20) {
                    temp = slopeFormula(10, 20, 0.1, 0.5, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }

                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            if ("Duplex".equals(materialType)) {
                if (temperature >= 0 && temperature <= 10) {
                    temp = slopeFormula(0, 10, 5e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 10 && temperature <= 20) {
                    temp = slopeFormula(10, 20, 1e-4, 1e-1, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 20 && temperature <= 30) {
                    temp = slopeFormula(20, 30, 0.1, 0.5, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }

                throw new WgException("温度{0}不再计算范围内", temperature);
            }

            if ("6Mo".equals(materialType)) {
                if (temperature >= 0 && temperature <= 15) {
                    temp = slopeFormula(0, 15, 3e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 15 && temperature <= 25) {
                    temp = slopeFormula(15, 25, 1e-4, 1e-1, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 25 && temperature <= 35) {
                    temp = slopeFormula(25, 35, 0.1, 0.5, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }

                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            throw new WgException("无此{0}的计算公式", materialType);
        }

        if (WaterTypeEnum.g.getName().equals(typeofSeawater)) {
            if ("SS316".equals(materialType)) {
                if (temperature >= 40 && temperature <= 60) {
                    temp = slopeFormula(40, 60, 3e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 60 && temperature <= 70) {
                    temp = slopeFormula(60, 70, 1e-4, 1e-1, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 70 && temperature <= 80) {
                    temp = slopeFormula(70, 80, 0.1, 0.5, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            if ("Duplex".equals(materialType)) {
                if (temperature >= 130 && temperature <= 150) {
                    temp = slopeFormula(130, 150, 3e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            throw new WgException("无此{0}的计算公式", materialType);
        }
        if (WaterTypeEnum.c.getName().equals(typeofSeawater)) {
            if ("SS316".equals(materialType)) {
                if (temperature >= 60 && temperature <= 80) {
                    temp = slopeFormula(60, 80, 3e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 60 && temperature <= 90) {
                    temp = slopeFormula(80, 90, 1e-4, 1e-1, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 90 && temperature <= 100) {
                    temp = slopeFormula(90, 100, 1e-1, 0.5, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            if ("Duplex".equals(materialType)) {
                if (temperature >= 100 && temperature <= 120) {
                    temp = slopeFormula(100, 120, 3e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 120 && temperature <= 130) {
                    temp = slopeFormula(120, 130, 1e-4, 1e-1, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 130 && temperature <= 140) {
                    temp = slopeFormula(130, 140, 1e-4, 0.5, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            throw new WgException("无此{0}的计算公式", materialType);
        }

        if (WaterTypeEnum.h.getName().equals(typeofSeawater)) {
            if ("SS316".equals(materialType)) {
                if (temperature >= 100 && temperature <= 120) {
                    temp = slopeFormula(100, 120, 5e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 120 && temperature <= 130) {
                    temp = slopeFormula(120, 130, 1e-4, 1e-1, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                if (temperature > 130 && temperature <= 140) {
                    temp = slopeFormula(130, 140, 1e-4, 0.5, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            if ("Duplex".equals(materialType)) {
                if (temperature >= 130 && temperature <= 150) {
                    temp = slopeFormula(130, 150, 5e-5, 1e-4, temperature);
                    pof = temp / wallThickness;
                    return pof;
                }
                throw new WgException("温度{0}不再计算范围内", temperature);
            }
            throw new WgException("无此{0}的计算公式", materialType);
        }
        throw new WgException("无当前水类别");
    }

    public static double calPoF15(String TypeofWater, double flowRate, boolean similarItem) {
        TypeofWater = WaterTypeEnum.a.getName();
        flowRate = 1.5;
        similarItem = true;
        if (flowRate > 2 || !similarItem) return findG(0, 0, 1.0);
        if (TypeofWater == "RawSeawater") {
            if (flowRate < 1) return findG(0.08, 0.01, 0);
            else return findG(0.2, 0.1, 0);
        } else if (TypeofWater == "Seawater+biocide/chlorination") {
            if (flowRate < 1) return findG(0.08, 0.01, 0);
            else return findG(0.2, 0.1, 0);
        } else if (TypeofWater == "SeawaterLowOxygen") return findG(0.02, 0.02, 0);
        else if (TypeofWater == "SeawaterLowOxygen+biocide") return findG(0.02, 0.02, 0);
        else if (TypeofWater == "SeawaterLowOxygen+biocide+chlorination") return findG(0.02, 0.02, 0);
        else if (TypeofWater == "SeawaterLowOxygen+chlorination") return findG(0.02, 0.02, 0);
        else if (TypeofWater == "FreshWater") return findG(0.015, 0.05, 0);
        else if (TypeofWater == "ClosedLoop") return findG(0.015, 0.05, 0);
        else if (TypeofWater == "ExposedDeains") {
            if (flowRate < 1) return findG(0.08, 0.01, 0);
            else return findG(0.2, 0.1, 0);
        } else if (TypeofWater == "SanitaryDrains") return findG(0.05, 0.05, 0);
        else {
            System.out.println("无当前水类别");
            return -1;
        }
    }

    public static double findG(double u, double Q, double POF) {
        Random random = new Random();
        if (POF != 0) {
            return POF;
        }
        double m = 0, K = 100000;
        if (POF == 0 && u == 0 && Q == 0) {
            System.out.println("错误");
            return -1;
        }
        double xi, P;
        NormalDistribution normalDistribution;
        for (int i = 0; i < K; i++) {
            normalDistribution = new NormalDistribution(u, Q);
            xi = normalDistribution.sample();
            d = time * xi; //步骤5
            g = 2 * 1.1 * SMYS * t / D * (1 - d / t) - pa; //步骤4
            if (g < 0) m++;
        }
        P = m / K;
        return P;
    }

    public static double POF, logi, sit, u, Q, d, g, P;
    public static double T = 25; // 温度
    public static double t = 12.7; // 壁厚
    public static double D = 273.1; // 外径
    public static double pa = 10; // 操作压力
    public static double SMYS = 285, time = 15;
    public static double ER = 0.028; // 腐蚀速率
    public static double Z = 0.9, k = 50, uO = 0.0011, uG = 0.00003, urelmax = 7.06, pr = 0.8, po = 850, pW = 1024, Qc = 0.5;
}
