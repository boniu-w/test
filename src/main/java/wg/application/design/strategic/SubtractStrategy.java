package wg.application.design.strategic;

public class SubtractStrategy implements Strategy {
    public int execute(int a, int b) {
        return a - b;
    }
    
    @Override
    public int get() {
        return 0;
    }
}
