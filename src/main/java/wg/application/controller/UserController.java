package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.entity.User;
import wg.application.service.UserService;
import wg.application.vo.Result;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/user_controller")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping(value = "/get_all")
    public Result<List<User>> getAll() {
        Result<List<User>> result = new Result<>();
        List<User> all = userService.getAll();
        result.setResult(all);
        return result;
    }
}
