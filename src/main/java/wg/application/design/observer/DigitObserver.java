package wg.application.design.observer;

public class DigitObserver implements Observer {
    public void update(int value) {
        System.out.println("DigitObserver: " + value);
    }
}

