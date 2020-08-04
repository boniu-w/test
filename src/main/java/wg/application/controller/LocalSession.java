package wg.application.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/8/4 9:37
 * @version
 * @Copyright
 *************************************************************/
@Controller
@RequestMapping(value = "/localSession")
public class LocalSession {


    @RequestMapping(value = "/setLocalSession")
    @ResponseBody
    public Result setLocalSession(String age, HttpServletRequest request, HttpServletResponse response) {
        //PrintWriter writer = null;
        String name = "";
        try {
            name = "小黑";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",name);

            HttpSession session = request.getSession();
            session.setAttribute("name", name);

            //writer = response.getWriter();
            //writer.write(name);
            //writer.flush();


            System.out.println(age);


            return Result.ok(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //writer.close();
        }
        return Result.ok(name);
    }
}
