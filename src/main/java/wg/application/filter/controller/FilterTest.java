package wg.application.filter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*************************************************************
 * @Package wg.application.filter.controller
 * @author wg
 * @date 2020/8/24 16:42
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/filterTest")
public class FilterTest {


    @RequestMapping(value = "/test1")
    public void test1(){

    }
}
