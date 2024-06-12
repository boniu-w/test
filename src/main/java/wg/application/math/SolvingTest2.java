package wg.application.math;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import wg.application.exception.WgException;

/************************************************************************
 * author: wg
 * description: Asmeb31g 计算剩余寿命
 * 一元方程
 * createTime: 18:04 2024/6/11
 * updateTime: 18:04 2024/6/11
 ************************************************************************/
public class SolvingTest2 {

    public static void main(String[] args) {
        // calculateRemainingLife(0, 0, 0, 0, 0, 0, 0, 0, "", "", 0, 0);
        calculateRemainingLife1(0, 0, 0, 0, 0, 0, 0, 0, "", "", 0, 0);
    }

    /**
     * @author: wg
     * @description:
     * @params: D=458.8: 管道外径[mm]
     * t=12.1: 管道壁厚[mm]
     * d0=6: 金属损失最大深度[mm]
     * L0=100: 金属损失长度[mm]
     * sf=1.5: 安全系数
     * maop=13: 最大允许操作压力[MPa]
     * smys=601: 最小屈服强度[MPa]
     * smts=760: 最小拉伸强度[MPa]
     * methodSflow="1.1smys": 流变应力计算方法
     * methodeva="改进": 评估方法
     * year=365[day]
     * CR=0.3: 深度腐蚀速率 [mm/year]
     * crlength=0.2: 长度腐蚀速率[mm/year]
     * @return:
     * @createTime: 16:49  2024/6/11
     * @updateTime: 16:49  2024/6/11
     */
    public static void calculateRemainingLife(double D, double t, double d0, double L0, double sf, double maop, double smys,
                                              double smts, String methodSflow, String methodeva, double CR, double crlength) {
        D = 458.8;
        t = 12.1;
        d0 = 6;
        L0 = 100;
        sf = 1.5;
        maop = 13;
        smys = 601;
        smts = 760;
        methodSflow = "1.1SMYS";
        methodeva = "改进方法失效应力";
        CR = 0.3;
        crlength = 0.2;

        // double dt = 27;
        // double dtdt = d0 + CR * dt;
        // double ltdt = L0 + crlength * dt;
        // double zdt = (ltdt * ltdt) / (D * t);
        // double sf1dt = 0;
        // double sflow = 0;
        // double sf2dt = 0;
        // double sfdt = 0;
        // double s0 = (maop * D) / (2 * t);
        //
        // switch (methodSflow) {
        //     case "1.1SMYS":
        //         sflow = 1.1 * smys;
        //         break;
        //     case "SMYS+69MPa":
        //         sflow = smys + 69;
        //         break;
        //     case "otherwise":
        //         sflow = (smys + smts) / 2;
        //         break;
        //     default:
        //         throw new WgException("流变应力计算方法{0}没有对应的算法", methodSflow);
        // }
        //
        // if ("改进方法失效应力".equals(methodeva)) {
        //     double m2dt = 0;
        //     if (zdt <= 50) {
        //         m2dt = Math.pow(1 + 0.6275 * zdt - 0.003375 * zdt * zdt, 0.5);
        //     } else {
        //         m2dt = 0.032 * zdt + 3.3;
        //     }
        //
        //     double fenzi = 1 - 0.85 * dtdt / t;
        //     double fenmu = 1 - 0.85 * (dtdt / t) * (1 / m2dt);
        //     sf2dt = sflow * (fenzi / fenmu);
        // }
        //
        // sfdt = sf2dt;
        //
        // if ("初始方法失效应力".equals(methodeva)) {
        //     double m1dt = Math.pow(1 + 0.8 * zdt, 0.5);
        //
        //     if (zdt <= 20) {
        //         double fenzi = 1 - (2d / 3d) * (dtdt / t);
        //         double fenmu = 1 - (2d / 3d) * (dtdt / t) * (1 / m1dt);
        //         sf1dt = sflow * (fenzi / fenmu);
        //     } else {
        //         sf1dt = sflow * (1 - dtdt / t);
        //     }
        //
        //     sfdt = sf1dt;
        // }
        //
        // double rsdt = sfdt * 2 * t / D;
        //
        // double finalSfdt = sfdt;
        // double finalSf = sf;
        // MultivariateVectorFunction functions = variables -> {
        //     double x = variables[0];
        //     double y = variables[1];
        //     double f1 = 1.0 / y + 1;
        //     double f2 = 2.0 / y + 1;
        //     return new double[]{
        //             finalSfdt - s0 * finalSf
        //     };
        // };

        double finalD = d0;
        double finalCR = CR;
        double finalCrlength = crlength;
        double finalL = L0;
        double finalD1 = D;
        double finalT = t;
        double finalMaop = maop;
        String finalMethodSflow = methodSflow;
        double finalSmys = smys;
        double finalSmts = smts;
        String finalMethodSflow1 = methodSflow;
        String finalMethodeva = methodeva;
        double finalSf = sf;
        UnivariateFunction function = dt -> {
            // dt = 27;
            double dtdt = finalD + finalCR * dt;
            double ltdt = finalL + finalCrlength * dt;
            double zdt = (ltdt * ltdt) / (finalD1 * finalT);
            double sf1dt = 0;
            double sflow = 0;
            double sf2dt = 0;
            double sfdt = 0;
            double s0 = (finalMaop * finalD1) / (2 * finalT);

            switch (finalMethodSflow) {
                case "1.1SMYS":
                    sflow = 1.1 * finalSmys;
                    break;
                case "SMYS+69MPa":
                    sflow = finalSmys + 69;
                    break;
                case "otherwise":
                    sflow = (finalSmys + finalSmts) / 2;
                    break;
                default:
                    throw new WgException("流变应力计算方法{0}没有对应的算法", finalMethodSflow1);
            }

            if ("改进方法失效应力".equals(finalMethodeva)) {
                double m2dt = 0;
                if (zdt <= 50) {
                    m2dt = Math.pow(1 + 0.6275 * zdt - 0.003375 * zdt * zdt, 0.5);
                } else {
                    m2dt = 0.032 * zdt + 3.3;
                }

                double fenzi = 1 - 0.85 * dtdt / finalT;
                double fenmu = 1 - 0.85 * (dtdt / finalT) * (1 / m2dt);
                sf2dt = sflow * (fenzi / fenmu);
            }

            sfdt = sf2dt;

            if ("初始方法失效应力".equals(finalMethodeva)) {
                double m1dt = Math.pow(1 + 0.8 * zdt, 0.5);

                if (zdt <= 20) {
                    double fenzi = 1 - (2d / 3d) * (dtdt / finalT);
                    double fenmu = 1 - (2d / 3d) * (dtdt / finalT) * (1 / m1dt);
                    sf1dt = sflow * (fenzi / fenmu);
                } else {
                    sf1dt = sflow * (1 - dtdt / finalT);
                }

                sfdt = sf1dt;
            }

            return sfdt - s0 * finalSf;
        };

        // 创建求解器实例，BrentSolver是基于Brent方法的求解器，适合大多数情况
        BrentSolver solver = new BrentSolver();

        // 设置求解精度，例如，1e-6表示结果与实际解之间的最大误差不超过1e-6
        double absoluteAccuracy = 1e-6;

        // 求解方程的根，这里我们假设方程在区间[0, 5]内有根
        double lowerBound = 0;
        double upperBound = 100;

        // 调用solve方法求解
        double root = solver.solve(1000, function, lowerBound, upperBound, absoluteAccuracy);
        System.out.printf("The root of the equation is approximately %.6f%n", root);

        // double dt1 = dt;
        // double dt2 = ((t * 0.8) - d0) / CR;
        //
        // dt = Math.min(dt1, dt2);
        // System.out.println("dt = " + dt);
    }

    public static void calculateRemainingLife1(double D, double t, double d0, double L0, double sf, double maop, double smys,
                                              double smts, String methodSflow, String methodeva, double CR, double crlength) {
        D = 458.8;
        t = 12.1;
        d0 = 6;
        L0 = 100;
        sf = 1.5;
        maop = 13;
        smys = 601;
        smts = 760;
        methodSflow = "1.1SMYS";
        methodeva = "改进方法失效应力";
        CR = 0.3;
        crlength = 0.2;

        double finalD = d0;
        double finalCR = CR;
        double finalCrlength = crlength;
        double finalL = L0;
        double finalD1 = D;
        double finalT = t;
        double finalMaop = maop;
        String finalMethodSflow = methodSflow;
        double finalSmys = smys;
        double finalSmts = smts;
        String finalMethodeva = methodeva;
        double finalSf = sf;

        UnivariateFunction function = dt -> {
            double dtdt = finalD + finalCR * dt;
            double ltdt = finalL + finalCrlength * dt;
            double zdt = (ltdt * ltdt) / (finalD1 * finalT);
            double sf1dt = 0;
            double sflow = 0;
            double sf2dt = 0;
            double sfdt = 0;
            double s0 = (finalMaop * finalD1) / (2 * finalT);

            switch (finalMethodSflow) {
                case "1.1SMYS":
                    sflow = 1.1 * finalSmys;
                    break;
                case "SMYS+69MPa":
                    sflow = finalSmys + 69;
                    break;
                case "otherwise":
                    sflow = (finalSmys + finalSmts) / 2;
                    break;
                default:
                    throw new WgException("流变应力计算方法" + finalMethodSflow + "没有对应的算法");
            }

            if ("改进方法失效应力".equals(finalMethodeva)) {
                double m2dt = 0;
                if (zdt <= 50) {
                    m2dt = Math.sqrt(1 + 0.6275 * zdt - 0.003375 * zdt * zdt);
                } else {
                    m2dt = 0.032 * zdt + 3.3;
                }

                double fenzi = 1 - 0.85 * dtdt / finalT;
                double fenmu = 1 - 0.85 * (dtdt / finalT) * (1 / m2dt);
                sf2dt = sflow * (fenzi / fenmu);
            }

            sfdt = sf2dt;

            if ("初始方法失效应力".equals(finalMethodeva)) {
                double m1dt = Math.sqrt(1 + 0.8 * zdt);

                if (zdt <= 20) {
                    double fenzi = 1 - (2.0 / 3.0) * (dtdt / finalT);
                    double fenmu = 1 - (2.0 / 3.0) * (dtdt / finalT) * (1 / m1dt);
                    sf1dt = sflow * (fenzi / fenmu);
                } else {
                    sf1dt = sflow * (1 - dtdt / finalT);
                }

                sfdt = sf1dt;
            }

            return sfdt - s0 * finalSf;
        };

        double lowerBound = 0;
        double upperBound = 100;
        boolean found = false;

        for (double i = lowerBound; i < upperBound; i += 1) {
            double f1 = function.value(i);
            double f2 = function.value(i + 1);
            System.out.printf("Checking interval [%.2f, %.2f]: f(%.2f) = %.6f, f(%.2f) = %.6f%n", i, i + 1, i, f1, i + 1, f2);
            if (f1 * f2 < 0) {
                lowerBound = i;
                upperBound = i + 1;
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No suitable interval found where the function values have different signs.");
            return;
        }

        // 检查区间顺序
        if (lowerBound >= upperBound) {
            System.out.println("The lower bound must be less than the upper bound.");
            return;
        }

        UnivariateSolver solver = new BrentSolver();
        double absoluteAccuracy = 1e-6;

        double root = solver.solve(1000, function, lowerBound, upperBound);
        System.out.printf("The root of the equation is approximately %.6f%n", root);
    }
}
