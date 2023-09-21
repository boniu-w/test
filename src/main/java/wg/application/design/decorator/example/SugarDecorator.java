package wg.application.design.decorator.example;

// 糖装饰器
class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", 糖";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
}
