package wg.application.math;

import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

/************************************************************************
 * author: wg
 * description: WeibullRandomGenerator
 * 威布尔分布
 * createTime: 15:55 2024/5/20
 * updateTime: 15:55 2024/5/20
 ************************************************************************/
public class WeibullRandomGenerator {
    public static void main(String[] args) {
        double v = 2 * 1.1 * 285 * 12.7 / 273.1;
        double v1 = 1 - 8.38 / 12.7;
        double v2 = v * v1 - 10;
        System.out.println("v2 = " + v2);

        // 定义威布尔分布的形状参数和尺度参数
        double shape = 0.52487;  // 形状参数k
        double scale = 0.056;  // 尺度参数λ

        // 创建威布尔分布实例
        RandomGenerator randomGenerator = new JDKRandomGenerator();
        WeibullDistribution weibullDistribution = new WeibullDistribution(randomGenerator, shape, scale, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);

        // 生成符合威布尔分布的随机数
        int sampleSize = 1000;
        double[] randomNumbers = new double[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            randomNumbers[i] = weibullDistribution.sample();
        }

        // 打印生成的随机数（示例打印前10个随机数）
        System.out.println("Generated Weibull-distributed random numbers:");
        for (int i = 0; i < 100; i++) {
            System.out.println(randomNumbers[i]);
        }
    }
}
