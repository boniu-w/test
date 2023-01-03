package wg.application.controller;

import org.springframework.web.bind.annotation.*;
import wg.application.entity.User;
import wg.application.exception.WgException;
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

    @PostMapping(value = "/update_user_test_throws")
    public void updateUserTestThrows(@RequestBody User user) {
        try {
            userService.updateTestThrows(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/update_user_test_tool_of_try")
    public void updateUserTestToolOfTry(@RequestBody User user) {
        try {
            userService.testToolClass(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/update_user_throw")
    public void updateUserThrow(@RequestBody User user) {
        try {
            userService.testThrow(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/update_user_throw2")
    public void updateUserThrow2(@RequestBody User user) {
        try {
            userService.testThrow2(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/update_user_test_try_and_throw")
    public void testTryAndThrow(@RequestBody User user) {
        try {
            userService.testTryAndThrow(user);
        } catch (WgException e) {
            throw new RuntimeException(e);
        }
    }
}
