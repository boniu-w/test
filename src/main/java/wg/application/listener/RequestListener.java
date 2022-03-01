package wg.application.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

        System.out.println("this is a new request");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

        System.out.println("request destroyed");
    }
}