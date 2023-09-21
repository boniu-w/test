package wg.application.design.decorator;

/************************************************************************
 * @author: wg
 * @description: 装饰器模式是一种结构型设计模式，它允许在运行时为对象添加额外的行为，而不需要修改原始对象的结构。装饰器模式通过将对象包装在
 * 一个或多个装饰器中来实现这一目的，从而允许客户端在运行时动态地添加或删除行为。
 *
 *  装饰器模式适用于以下场景：
 *
 * 1. 当你需要在运行时动态地为对象添加行为时，例如添加额外的验证、日志记录、缓存等。
 * 2. 当你需要在不影响其他对象的情况下，透明地修改对象的功能时，例如为一个现有的类添加新的方法，但又不能修改它的源代码。
 * @params:
 * @return:
 * @createTime: 15:37  2023/4/3
 * @updateTime: 15:37  2023/4/3
 ************************************************************************/
public class Main {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        // component.operation();
        
        Component decoratedComponent = new ConcreteDecorator(component);
        decoratedComponent.operation();
        
    }
}
