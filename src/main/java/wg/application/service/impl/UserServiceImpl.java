package wg.application.service.impl;

// import org.springframework.security.core.authority.AuthorityUtils;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import wg.application.entity.User;
import wg.application.entity.example.UserExample;
import wg.application.exception.WgException;
import wg.application.mapper.UserMapper;
import wg.application.service.UserService;
import wg.application.util.StringUtil;

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
public class UserServiceImpl implements UserService {
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
     * @description: 测试 事务的失效
     * 测试结果:
     * 1: try catch 时 会失效, 发生异常不会回滚
     * @params:
     * @return:
     * @createTime: 11:34  2022/10/12
     * @updateTime: 11:34  2022/10/12
     ************************************************************************/
    @Transactional(rollbackFor = Exception.class)
    public void update() {
        User user = new User();
        user.setId(1L);
        user.setAge(13);
        userMapper.updateByPrimaryKeySelective(user);

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(1);
        // User user = new User();
        user.setAge(13);
        userMapper.updateByExampleSelective(user,userExample);

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 测试 事务的失效 调用另一个方法里有 try catch
     * 测试结果:  也不行, 依然会失效, 不会回滚
     * @params:
     * @return:
     * @createTime: 15:08  2022/10/12
     * @updateTime: 15:08  2022/10/12
     ************************************************************************/
    @Transactional(rollbackFor = Exception.class)
    public void updateTestTryCatch(User user) {
        updateTestTransactional(user);
        // $updateTestTransactional(user); // 报错, 必须在 方法上抛出异常
    }

    public void updateTestTransactional(User user) {
        userMapper.updateByPrimaryKeySelective(user);

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 测试 抛出异常, throws WgException
     * 测试结果: 方法上 throws WgException , rollbackFor= Exception.class, 会回滚
     * @params:
     * @return:
     * @createTime: 17:01  2022/11/28
     * @updateTime: 17:01  2022/11/28
     ************************************************************************/
    @Transactional(rollbackFor = Exception.class)
    public void updateTestThrows(User user) throws WgException {
        updateTestTransactionalTestThrows(user);
        int i = 1 / 0;
    }

    public void updateTestTransactionalTestThrows(User user) throws WgException {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void $updateTestTransactional(User user) throws Exception {
        userMapper.updateByPrimaryKeySelective(user);

        int i = 1 / 0;
    }

    /************************************************************************
     * @author: wg
     * @description: 测试调用 私用方法
     * 测试结果:
     * 1: update方法有 @Transactional 注解, private _updateTestTransactional() 没有 Transactional 注解, 发生异常时 事务是生效的, 回滚
     * 2: update方法没有 @Transactional 注解, private _updateTestTransactional() 有 Transactional 注解 发生异常时 事务不生效, 不回滚
     * 结论: @Transactional 注解 所在的方法必须是 public 的
     * 解释: 在 Spring 中，因为 Spring 是通过 AOP 代理来实现事务的，只有公共方法才能被代理调用。这是因为，对于非公共方法，只能在类内
     * 部调用，而 Spring 的事务管理机制是通过在代理对象中嵌入事务增强器来实现事务控制的。如果目标方法不是公共的，Spring 无法在代理对象中创建
     * 一个包含该方法的事务增强器。因此，使用 @Transactional 注解时，确保要标记公共方法，以便 Spring 能够代理该方法并应用事务增强器。
     * @params:
     * @return:
     * @createTime: 15:13  2022/10/12
     * @updateTime: 15:13  2022/10/12
     ************************************************************************/
    // @Transactional(rollbackFor = Exception.class)
    public void updateTestPrivate(User user) {
        _updateTestTransactional(user);
    }

    @Transactional(rollbackFor = Exception.class)
    private void _updateTestTransactional(User user) {
        userMapper.updateByPrimaryKeySelective(user);

        int i = 1 / 0;
    }

    /************************************************************************
     * @author: wg
     * @description: 测试 调用的工具类里有 try catch
     * 结论: 调用的工具类里有 try catch , 发生异常时 事务不生效, 不会回滚
     * @params:
     * @return:
     * @createTime: 9:36  2022/11/29
     * @updateTime: 9:36  2022/11/29
     ************************************************************************/
    @Transactional(rollbackFor = Exception.class)
    public void testToolClass(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        StringUtil.testTransaction();
    }

    /************************************************************************
     * @author: wg
     * @description: 测试 在方法里直接 throw exception
     * 结论: 事务会生效, 会回滚
     * @params:
     * @return:
     * @createTime: 9:54  2022/11/29
     * @updateTime: 9:54  2022/11/29
     ************************************************************************/
    @Transactional(rollbackFor = Exception.class)
    public void testThrow(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        if (1 == 1) {
            System.out.println("exception");
            throw new WgException(500);
        }
    }

    /************************************************************************
     * @author: wg
     * @description:
     * 测试: 其他方法里 throw exception, 而本方法不 throw
     * 结论: 事务会生效, 会回滚
     * @params:
     * @return:
     * @createTime: 10:00  2022/11/29
     * @updateTime: 10:00  2022/11/29
     ************************************************************************/
    @Transactional(rollbackFor = Exception.class)
    public void testThrow2(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        StringUtil.testTransactionThrow();
    }

    /************************************************************************
     * @author: wg
     * @description:
     * 测试: try catch 加 throw exception
     * 结论: 发生异常会回滚, 事务生效
     * @params:
     * @return:
     * @createTime: 9:59  2022/12/1
     * @updateTime: 9:59  2022/12/1
     ************************************************************************/
    @Transactional(rollbackFor = Exception.class)
    public void testTryAndThrow(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WgException(500);
        }
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
