package wg.application.ioc.servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.ioc.service.UserService;
import wg.application.ioc.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import java.util.List;

@RestController()
@RequestMapping(value = "/helloservlet")
public class HelloServlet extends HttpServlet {

    // public UserService userService = new UserServiceImpl();

    // @GetMapping(value = "/findall")
    // public List<String> findAll() {
    //     // UserService userService = new UserServiceImpl();
    //     return userService.findAll();
    // }

}
