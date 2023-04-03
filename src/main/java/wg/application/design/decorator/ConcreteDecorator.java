package wg.application.design.decorator;

public class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component) {
        super(component);
    }
    
    public void operation() {
        super.operation();
        System.out.println("子装饰器操作, ConcreteDecorator operation.");
    }
}
