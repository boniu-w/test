package wg.application.service.impl;

import org.springframework.stereotype.Service;
import wg.application.dao.UserMapper;
import wg.application.entity.User;
import wg.application.entity.UserExample;
import wg.application.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<User> getAll() {
        UserExample userExample = new UserExample();
        return userMapper.selectByExample(userExample);
    }
}