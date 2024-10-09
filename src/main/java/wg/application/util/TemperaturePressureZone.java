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
