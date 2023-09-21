package wg.application.design.adapter;

public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public int specificRequest(String atr) {
        adaptee.specificRequest();
        return 0;
    }
}
