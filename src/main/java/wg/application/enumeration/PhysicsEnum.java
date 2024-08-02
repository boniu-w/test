package wg.application.enumeration;

public enum PhysicsEnum {
    KAIERWEN(273.15, "开尔文"),
    R(8.314, "理想标准气体常数[Pa.m3.mol/K]"),
    Z(0.83, "气体压缩系数"),
    BETA(5, "热衰减常数"),
    G(9.807, "重力加速度");

    private final double value;
    private final String description;

    PhysicsEnum(double value, String description) {
        this.value = value;
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
