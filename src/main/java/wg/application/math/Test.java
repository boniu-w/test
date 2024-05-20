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
            }
        }
    }
}
