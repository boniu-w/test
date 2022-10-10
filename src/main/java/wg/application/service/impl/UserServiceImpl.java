package wg.application.service.impl;

// import org.springframework.security.core.authority.AuthorityUtils;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import wg.application.entity.User;
import wg.application.entity.example.UserExample;
import wg.application.mapper.UserMapper;
import wg.application.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/************************************************************************
 * @author: wg
 * @description: security 相关登录配置
 * @params:
 * @return:
 * @createTime: 16:18  2022/3/30
 * @updateTime: 16:18  2022/3/30
 ************************************************************************/
@Service
public class UserServiceImpl implements UserService  {
//, UserDetailsService
    @Resource
    UserMapper userMapper;

    // @Resource
    // PasswordEncoder passwordEncoder;

    @Override
    public List<User> list() {
        UserExample userExample = new UserExample();
        return userMapper.selectByExample(userExample);
    }

    /************************************************************************
     * @author: wg
     * @description: 此时, 配置文件里的配置失效
     * @params:
     * @return:
     * @createTime: 16:17  2022/3/30
     * @updateTime: 16:17  2022/3/30
     ************************************************************************/
    // @Override
    // public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    //     // // 数据库查询方式
    //     // UserExample userExample = new UserExample();
    //     // userExample.createCriteria().andNameEqualTo(userName);
    //     // List<User> userList = userMapper.selectByExample(userExample);
    //     // if (userList == null || userList.size() == 0) {
    //     //     throw new WgException("userList == 0");
    //     // }
    //     // return new org.springframework.security.core.userdetails.User(userName, "123", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    //
    //     String password = passwordEncoder.encode("123");
    //     System.out.println("password -->>  " + password);
    //     userName = "wg";
    //     return new org.springframework.security.core.userdetails.User(userName, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    // }
}

// @Service
// public class UserServiceImpl implements UserService {
//
//     @Resource
//     UserMapper userMapper;
//
//     @Override
//     public List<User> list() {
//         UserExample userExample = new UserExample();
//         return userMapper.selectByExample(userExample);
//     }
// }
