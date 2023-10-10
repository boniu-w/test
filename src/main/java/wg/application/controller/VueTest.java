package wg.application.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wg.application.util.JsonUtilMyTest;
import wg.application.vo.Result;

import javax.servlet.http.HttpServletRequest;

/****************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2021/4/27 11:17
 * @decription
 * @version
 * @Copyright
 ****************************************************/
@RestController
@RequestMapping(value = "vueTest")
public class VueTest {


    @RequestMapping(value = "/getVueTemplate")
    public Result getVueTemplate(@RequestParam(required = false) String vueTemplate,
                                 @RequestBody(required = false) JSONObject jsonObject,
                                 HttpServletRequest request) {

        //System.out.println("--- getVueTemplate --- " + vueTemplate);
        //
        //String vueTemplate1 = request.getParameter("vueTemplate");
        //System.out.println("----->>>> " + vueTemplate1);

        System.out.println("jsonObject -> " + jsonObject);
        String vueTemplate2 = jsonObject.getString("vueTemplate");

        long round = Math.round(Math.random() * 1000);
        String path = "D:\\IdeaProjects\\genate-form\\vue-form-making\\src\\components" +
          "\\VueTemplate" + round + ".vue";
        JsonUtilMyTest.jsonDataToFileByBufferedWritter(path, vueTemplate2);

        return Result.ok("success");

    }


    @RequestMapping(value = "/getVueTemplateData")
    public Result getVueTemplateData(@RequestBody JSONObject jsonObject) {

        System.out.println(jsonObject);

        return Result.ok("hello     --");
    }
}
