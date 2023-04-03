package wg.application.design.observer;

/************************************************************************
 * @author: wg
 * @description: 行为型模式，它定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象，当主题对象状态发生改变时，它的所有依赖者都会收到通知并更新状态。
 * 观察者模式适用于以下场景：
 * 当一个对象的改变需要同时改变其他对象的时候，例如当一个商品的价格发生变化时，需要通知所有的购买者。
 * 当一个对象的改变需要同时改变一组对象的时候，例如当一个博客文章被更新时，需要通知所有订阅该博客的用户。
 * 当一个对象的改变需要通知其他对象，但是你并不知道这些对象是谁的时候，例如一个用户对多个群组都进行了订阅，需要通知所有相关的群组。
 * 当一个对象的改变需要改变另一个对象，但是另一个对象又依赖于其他对象时，例如一个股票价格的变化会影响到一个投资组合的价值，而这个投资组合又依赖于多个股票。
 * @params:
 * @return:
 * @createTime: 15:09  2023/4/3
 * @updateTime: 15:09  2023/4/3
 ************************************************************************/
public class Main {
    public static void main(String[] args) {
        NumberGenerator numberGenerator = new NumberGenerator();
        
        Observer digitObserver = new DigitObserver();
        Observer graphObserver = new GraphObserver();
        
        numberGenerator.registerObserver(digitObserver);
        numberGenerator.registerObserver(graphObserver);
        
        for (int i = 0; i < 10; i++) {
            numberGenerator.generateNumber();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        numberGenerator.removeObserver(digitObserver);
        
        for (int i = 0; i < 10; i++) {
            numberGenerator.generateNumber();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}