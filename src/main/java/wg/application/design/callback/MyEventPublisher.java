package wg.application.design.callback;

import java.util.ArrayList;
import java.util.List;

/************************************************************************
 * author: wg
 * description: MyEventPublisher 
 * createTime: 10:09 2024/4/1
 * updateTime: 10:09 2024/4/1
 ************************************************************************/
public class MyEventPublisher implements EventPublisher{
    private List<EventListener> listeners = new ArrayList<>();
    @Override
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void raiseEvent() {
        System.out.println("我是一个博主, 我现在发布了一篇文章, 我要通知到每一个订阅我订阅列表里的人");
        for (EventListener listener : listeners) {
            listener.onEvent();
        }
    }
}
