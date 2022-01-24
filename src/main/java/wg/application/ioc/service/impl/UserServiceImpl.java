package wg.application.ioc.service.impl;

import wg.application.ioc.dao.UserDao;
import wg.application.ioc.factory.BeanFactory;
import wg.application.ioc.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    public UserServiceImpl() {
        for (int i = 0; i < 10; i++) {
            System.out.println(BeanFactory.getDao("helloDao"));
        }
    }

    // public UserDao userDao = new UserDaoImpl();
    public UserDao userDao = (UserDao) BeanFactory.getDao("helloDao");

    @Override
    public List<String> findAll() {
        return userDao.findAll();
    }
}
