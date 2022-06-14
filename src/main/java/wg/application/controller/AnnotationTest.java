package wg.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.annotation.RequiredRole;
import wg.application.vo.Result;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/8/7 10:22
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/annotationTest")
public class AnnotationTest {

    @RequestMapping(value = "/annotationTest")
    @RequiredRole(value = "admin76")
    public Result annotationTest() {
        System.out.println("---annotationTest-----");

        return Result.ok();
    }


}
