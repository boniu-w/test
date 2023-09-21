package wg.application.design.decorator.example;

// 牛奶装饰器
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", 牛奶";
    }

    @Override
    public double getCost() {
        return super.getCost() + 1.0;
    }
}