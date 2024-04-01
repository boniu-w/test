package wg.application.design.callback;

public interface EventPublisher {
    void addEventListener(EventListener listener);
    void removeEventListener(EventListener listener);
    void raiseEvent();
}
