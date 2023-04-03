package wg.application.design.decorator;

public class Decorator implements Component {
    private Component component;
    
    public Decorator(Component component) {
        this.component = component;
    }
    
    public void operation() {
        component.operation();
        System.out.println("装饰器操作");
    }
}
