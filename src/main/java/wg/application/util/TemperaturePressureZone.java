package wg.application.util;

/************************************************************************
 * author wg
 * description TemperaturePressureZone 
 * createTime 15:16 2024/10/9
 * updateTime 15:16 2024/10/9
 ************************************************************************/
public class TemperaturePressureZone {

    static class Zone {
        double[][] points;
        String label;

        Zone(double[][] points, String label) {
            this.points = points;
            this.label = label;
        }

        /**
         * @author wg
         * @description 射线法就是做一条从该点出发的射线，当穿过区域边界的次数为偶数时该点在区域外，如果是奇数则在区域内：
         * (yi > pressure) != (yj > pressure)
         * 这一条件判断多边形的边是否跨过了点 (temperature, pressure) 的水平线，即这条边的两个端点的 y 坐标分别位于待测点的上方和下方。
         * 如果跨越了，说明射线可能与这条边相交
         * temperature < (xj - xi) * (pressure - yi) / (yj - yi) + xi
         * 这是用线性插值计算射线与多边形边相交时的 x 坐标，并判断是否在待测点的右侧（即射线的方向）。这里的公式计算的是：在给定的 pressure（即待测点的 y 坐标）下，
         * 边与水平线相交时的 x 坐标。如果这个 x 坐标大于 temperature，说明射线与边相交。
         * if (intersect) {
         *   inside = !inside;
         * }
         * 每当射线与多边形的一条边相交时，inside 状态会被取反（即从 false 变为 true，或从 true 变为 false）。射线相交的次数决定了点是否在多边形内：
         * 如果相交次数是奇数次，点位于多边形内部。
         * 如果相交次数是偶数次，点位于多边形外部。
         * @createTime 9:41  2024/10/10
         * @updateTime 9:41  2024/10/10
         */
        boolean contains(double temperature, double pressure) {
            if (temperature == 0 && pressure == 0) {
                return true;
            }
            int n = points.length;
            boolean inside = false;
            for (int i = 0, j = n - 1; i < n; j = i++) {
                double xi = points[i][0], yi = points[i][1];
                double xj = points[j][0], yj = points[j][1];

                boolean intersect = ((yi > pressure) != (yj > pressure)) &&
                        (temperature < (xj - xi) * (pressure - yi) / (yj - yi) + xi);
                if (intersect) {
                    inside = !inside;
                }
            }

            // 检查点是否在边界上
            for (int i = 0; i < n; i++) {
                if (points[i][0] == temperature && points[i][1] == pressure) {
                    return true; // 点在边界上
                }
            }

            return inside;
        }
    }

    public static String findZone(double temperature, double pressure) {
        Zone[] zones = {
                new Zone(new double[][]{{0, 0}, {0, 0.116}, {20, 0.116}, {50, 0.04}, {100, 0.04}, {120, 0.015}, {120, 0}}, "1区"),
                new Zone(new double[][]{{0, 0.116}, {0, 0.22}, {20, 0.2}, {20, 0.175}, {60, 0.09}, {100, 0.09}, {120, 0.06}, {120, 0.015}, {100, 0.04}, {50, 0.04}, {20, 0.116}}, "2区"),
                new Zone(new double[][]{{0, 0.215}, {0, 0.5}, {20, 0.475}, {20, 0.375}, {30, 0.3}, {30, 0.155}, {20, 0.175}, {20, 0.2}}, "3区"),
                new Zone(new double[][]{{100, 0.09}, {100, 0.475}, {120, 0.475}, {120, 0.06}}, "4区"),
                new Zone(new double[][]{{30, 0.155}, {30, 0.3}, {40, 0.29}, {50, 0.22}, {60, 0.2}, {90, 0.195}, {90, 0.387}, {100, 0.4}, {100, 0.09}, {60, 0.09}}, "5区"),
                new Zone(new double[][]{{0, 0.5}, {0, 0.55}, {120, 0.55}, {120, 0.475}, {100, 0.475}, {100, 0.4}, {90, 0.387}, {90, 0.195}, {60, 0.2}, {50, 0.22}, {40, 0.29}, {30, 0.3}, {20, 0.375}, {20, 0.475}}, "6区")
        };

        for (Zone zone : zones) {
            if (zone.contains(temperature, pressure)) {
                return zone.label;
            }
        }
        return "未知区域";
    }

    public static void main(String[] args) {
        double temperature = 0; // 输入温度
        double pressure = 0; // 输入压力
        String zone = findZone(temperature, pressure);
        System.out.println("该点位于: " + zone);

        double temperature2 = 20; // 输入温度
        double pressure2 = 0.175; // 输入压力
        String zone2 = findZone(temperature2, pressure2);
        System.out.println("该点位于: " + zone2);

        double temperature3 = 0; // 输入温度
        double pressure3 = 0.116; // 输入压力
        String zone3 = findZone(temperature3, pressure3);
        System.out.println("该点位于: " + zone3);

        double temperature4 = 90; // 输入温度
        double pressure4 = 0.5; // 输入压力
        String zone4 = findZone(temperature4, pressure4);
        System.out.println("该点位于: " + zone4);
    }
}
