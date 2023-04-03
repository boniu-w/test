package wg.application.design.decorator;

public class ConcreteComponent implements Component {
    public void operation() {
        System.out.println("原始对象操作, ConcreteComponent operation.");
    }
}
