package wg.application.design.decorator.example;

/************************************************************************
 * @author: wg
 * @description: 在这个示例中，我们创建了一个基本的咖啡类 SimpleCoffee 和两个装饰器类 MilkDecorator 和 SugarDecorator，它们都实现了 Coffee 接口。
 * 通过不断地包装咖啡对象，我们可以在不修改原始咖啡类的情况下为咖啡添加新的功能，例如牛奶和糖。这就是装饰器模式的核心思想
 * @params:
 * @return:
 * @createTime: 16:04  2023/9/21
 * @updateTime: 16:04  2023/9/21
 ************************************************************************/
public class DecoratorPatternExample {
    public static void main(String[] args) {
        // 创建一个基本的咖啡
        Coffee coffee = new SimpleCoffee();
        System.out.println("咖啡描述: " + coffee.getDescription());
        System.out.println("价格: $" + coffee.getCost());

        // 添加牛奶
        coffee = new MilkDecorator(coffee);
        System.out.println("咖啡描述: " + coffee.getDescription());
        System.out.println("价格: $" + coffee.getCost());

        // 再加点糖
        coffee = new SugarDecorator(coffee);
        System.out.println("咖啡描述: " + coffee.getDescription());
        System.out.println("价格: $" + coffee.getCost());
    }
}