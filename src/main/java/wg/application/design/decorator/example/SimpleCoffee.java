package wg.application.design.decorator.example;

// 基本的咖啡类
class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "咖啡";
    }

    @Override
    public double getCost() {
        return 3.0;
    }
}