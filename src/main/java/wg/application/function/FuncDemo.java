package wg.application.function;

@FunctionalInterface
public interface FuncDemo {

    double demo(double a, double b, double c);

    default double demo2() {
        return 0d;
    }

    static double demo3() {
        return 1d;
    }
}
