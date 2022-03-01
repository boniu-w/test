package wg.application.internet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*************************************************************
 * @Package wg.application.internet.controller
 * @author wg
 * @date 2020/8/24 16:14
 * @version
 * @Copyright
 *************************************************************/
@RequestMapping(value = "/internetTest1")
@RestController
public class InternetTest {


    @RequestMapping(value = "test1")
    public Result test1(HttpServletRequest request){
        String token = request.getParameter("token");

        String attribute = (String)request.getSession().getAttribute("token");


        System.out.println(token);
        System.out.println(attribute);


        HttpSession session = request.getSession();
        session.setAttribute("token",token);

        return Result.ok();
    }
}
