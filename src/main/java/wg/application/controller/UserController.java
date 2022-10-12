package wg.application.controller;

import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "page")
    public Result<List<User>> page() {

        return null;
    }

    @GetMapping(value = "/list")
    public Result<List<User>> list() {
        Result<List<User>> result = new Result<>();
        List<User> all = userService.list();
        result.setResult(all);
        return result;
    }

    @PostMapping(value = "/add")
    public void add(User user) {
        System.out.println(user);
    }


    @PostMapping(value = "/update_user")
    public void updateUser(@RequestBody User user) {
        userService.updateTestPrivate(user);
    }
}
