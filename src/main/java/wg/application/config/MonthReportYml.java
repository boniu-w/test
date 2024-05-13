package wg.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import wg.application.ioc.factory.BeanFactory;
import wg.application.util.FileUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/************************************************************************
 * author: wg
 * description: MonthReportYml 
 * createTime: 17:38 2024/5/13
 * updateTime: 17:38 2024/5/13
 ************************************************************************/
// @Configuration
// @PropertySource(value = "classPath:monthreport.yml")
public class MonthReportYml {

    public static void main(String[] args) {
        test();
    }

    public static void test(){
        String name="monthreport.yml";
        try {
            Map<String, Object> ymlFile = FileUtil.getYmlFile(name);
            System.out.println("ymlFile = " + ymlFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
