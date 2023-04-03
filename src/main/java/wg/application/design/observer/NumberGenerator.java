package wg.application.design.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberGenerator implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private Random random = new Random();
    private int value;

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(value);
        }
    }

    public void generateNumber() {
        value = random.nextInt(100);
        notifyObservers();
    }
}
