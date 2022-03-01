package wg.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.config.AuthorInfo;

import java.util.Map;

@RestController
@RequestMapping(value = "/config_test_controller")
public class ConfigTestController {

    @Autowired
    private AuthorInfo authorInfo;

    @RequestMapping(value = "/get_author_info")
    public void getInfo(){
        String name = authorInfo.getName();

        System.out.println(name);

        Map<String,String> son = authorInfo.getSon();

        System.out.println(son);
    }
}
