package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.service.UserService;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user_controller")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping(value = "/get_all")
    public void getAll() {
        userService.getAll();
    }
}
