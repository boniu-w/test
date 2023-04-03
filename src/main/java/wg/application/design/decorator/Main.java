package wg.application.design.decorator;

/************************************************************************
 * @author: wg
 * @description: 装饰器模式
 * @params:
 * @return:
 * @createTime: 15:37  2023/4/3
 * @updateTime: 15:37  2023/4/3
 ************************************************************************/
public class Main {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        // component.operation();
        
        Component decoratedComponent =  new ConcreteDecorator(component);
        decoratedComponent.operation();
        
    }
}
