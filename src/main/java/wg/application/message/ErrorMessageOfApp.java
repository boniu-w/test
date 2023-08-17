package wg.application.message;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.util.CommonUtil;
import wg.application.util.MessageUtils;

import java.util.Properties;

@RestController
@RequestMapping(value = "error_message")
public class ErrorMessageOfApp {

    @GetMapping(value = "/get")
    public Properties get() {
        String path = "wg/application/message/error-messages.properties";
        ClassPathResource pathResource = new ClassPathResource(path);

        // System.out.println(pathResource.getPath());
        // System.out.println(pathResource.getFilename());

        String message = MessageUtils.getMessage(403);
        System.out.println("message = " + message);

        String message1 = MessageUtils.getMessage(10001, "get");
        System.out.println("message1 = " + message1);


        return CommonUtil.getProperties(pathResource);
    }

}
