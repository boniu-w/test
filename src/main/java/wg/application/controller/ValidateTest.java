package wg.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wg.application.entity.Student;
import wg.application.vo.Result;
import wg.application.vo.ResultData;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/11/3 12:14
 * @version
 * @Copyright
 *************************************************************/
@Controller
@RequestMapping(value = "/validateTestController")
public class ValidateTest {

    /****************************************************************
     * object 提交数据
     * @author: wg
     * @time: 2020/11/3 15:05
     ****************************************************************/
    @RequestMapping(value = "/validate")
    @ResponseBody
    public ResultData validate(Map map, HttpServletRequest request, Student student) {
        System.out.println("student -->>> " + student);
        System.out.println("map --->>>   " + map);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println("parameterNames  -->>>   " + s);
        }

        return ResultData.build().msg("$$$$$$$$$$");
    }


    @RequestMapping(value = "/validatePost",method = RequestMethod.POST)
    @ResponseBody
    public ResultData validatePost(Map map, HttpServletRequest request, Student student) {
        System.out.println("student -->>> " + student);
        System.out.println("map --->>>   " + map);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println("parameterNames  ++-->>>   " + s);
        }

        return ResultData.build().msg("$$$$$$$$$$");
    }

    /****************************************************************
     * <form>标签提交数据
     * @author: wg
     * @time: 2020/11/3 15:04
     ****************************************************************/
    @RequestMapping(value = "/validate2")
    @ResponseBody
    public Result validate2(Student student, HttpServletRequest request) {
        System.out.println("))))))))(((((((((     " + student);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println("   " + s);
        }

        return Result.ok("@@@@@@@@@@@  222222222222222");
    }


    /****************************************************************
     *
     * @author: wg
     * @time: 2020/11/3 17:22
     ****************************************************************/
    @RequestMapping(value = "/getFormData")
    @ResponseBody
    public Result getFormData(Student student, HttpServletRequest request) {
        System.out.println("))))))))(((((((((     " + student);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println("   " + s);
        }

        return Result.ok("@@@@@@@@@@@  222222222222222");
    }
}
