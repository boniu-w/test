package wg.application.math;

/************************************************************************
 * author: wg
 * description: Test 
 * createTime: 17:30 2024/5/20
 * updateTime: 17:30 2024/5/20
 ************************************************************************/
public class Test {
    public static void main(String[] args) {
        String materialType = "SS316";
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
        }
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
}
