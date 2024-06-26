package wg.application.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wg.application.entity.Student;
import wg.application.util.ValidatorUtil;
import wg.application.vo.Result;
import wg.application.vo.ResultData;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
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

        return ResultData.build().data("student -> " + student);
    }

    @RequestMapping(value = "/validatePost", method = RequestMethod.POST)
    @ResponseBody
    public ResultData validatePost(Map map,
                                   HttpServletRequest request,
                                   Student student,
                                   Model model) {
        System.out.println("model -> " + model);
        //System.out.println("jsonObject -> "+jsonObject); // 参数类型不匹配异常
        System.out.println("student -->>> " + student);
        System.out.println("map --->>>   " + map);

        String sex = request.getParameter("sex");
        System.out.println("sex -> " + sex);

        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            System.out.println(key);
        }


        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println("parameterNames  ++-->>>   " + s);
        }

        return ResultData.build().data("student: " + student);
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

    /************************************************************************
     * @author: wg
     * @description: 测试 validator 工具
     * 测试结果: MethodArgumentNotValidException
     * @params:
     * @return:
     * @createTime: 10:29  2022/10/10
     * @updateTime: 10:29  2022/10/10
     ************************************************************************/
    @PostMapping(value = "/test_validator_util")
    @ResponseBody
    public void testValidatorUtil(@Valid @RequestBody Student student) {
        System.out.println("<><><><><>");
    }

    /************************************************************************
     * @author: wg
     * @description: 测试 validator 工具
     * 测试结果: 报错, WgException
     * @params:
     * @return:
     * @createTime: 10:29  2022/10/10
     * @updateTime: 10:29  2022/10/10
     ************************************************************************/
    @PostMapping(value = "/_test_validator_util")
    @ResponseBody
    public void _testValidatorUtil(@RequestBody Student student) {
        ValidatorUtil.validateEntity(student);
        System.out.println("<><><><><>");
    }
}
