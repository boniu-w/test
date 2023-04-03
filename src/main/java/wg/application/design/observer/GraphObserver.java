package wg.application.design.observer;

public class GraphObserver implements Observer {
    public void update(int value) {
        System.out.print("GraphObserver: ");
        for (int i = 0; i < value; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
