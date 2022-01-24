package wg.application.ioc.dao.impl;

import wg.application.ioc.dao.UserDao;

import java.util.Arrays;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<String> findAll() {
        return Arrays.asList("1", "2", "3");
    }
}
