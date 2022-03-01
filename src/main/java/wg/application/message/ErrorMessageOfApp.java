package wg.application.message;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.util.CommonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@RestController
@RequestMapping(value = "error_message")
public class ErrorMessageOfApp {

    @GetMapping(value = "/get")
    public Properties get() {
        // ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        // String userDir = System.getProperty("user.dir");
        // String path = "wg/application/message/error-messages.properties";
        String path = "wg/application/message/error-messages.properties";
        // String path = "static/json/nongHangTitles.json";
        ClassPathResource pathResource = new ClassPathResource(path);

        System.out.println(pathResource.getPath());
        System.out.println(pathResource.getFilename());
        File file = null;
        try {
            file = pathResource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getName());
        // try {
        //     // File file = ResourceUtils.getFile("classpath:message/error-messages.properties");
        //     File file = ResourceUtils.getFile("classpath:static/json/area.json");
        //     System.out.println(file.getAbsolutePath());
        // } catch (FileNotFoundException e) {
        //     e.printStackTrace();
        // }

        // return null;
        return CommonUtil.getProperties(pathResource);
    }

}
