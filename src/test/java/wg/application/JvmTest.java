package wg.application;

import org.junit.Test;
import wg.application.jvm.ApplicationMonitorTask;
import wg.application.jvm.entity.ApplicationMonitorMessage;

public class JvmTest {

    @Test
    public void jvmInfo() {
        ApplicationMonitorTask applicationMonitorTask = new ApplicationMonitorTask();
        ApplicationMonitorMessage jvmInfo = applicationMonitorTask.getJvmInfo();
        System.out.println(jvmInfo);
    }
}
