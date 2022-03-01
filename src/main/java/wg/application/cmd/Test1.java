package wg.application.cmd;

import java.util.Properties;

public class Test1 {
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + "=" + v));
        String property = System.getProperty("report.dir");
        System.out.println(property);
        CmdTest.testProcessBuilder();
    }
}