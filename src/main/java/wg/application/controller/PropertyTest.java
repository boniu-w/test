package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.config.WgProperty;

import javax.annotation.Resource;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 14:27 2022/9/6
 * @updateTime: 14:27 2022/9/6
 ************************************************************************/
@RestController
@RequestMapping(value ="/property_test" )
public class PropertyTest {

    @Resource
    WgProperty wgProperty;

    @GetMapping(value = "/get_wg_property")
    public void getWgProperty(){
        String secret = wgProperty.getSecret();
        System.out.println(secret);
    }
}
