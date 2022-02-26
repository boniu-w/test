package wg.application.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerOf implements ApplicationListener<MyEvent> {

    private final Logger logger = LoggerFactory.getLogger(ListenerOf.class);

    @Override
    public void onApplicationEvent(MyEvent event) {

    }
}
