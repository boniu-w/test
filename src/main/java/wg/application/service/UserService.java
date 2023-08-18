package wg.application.service;

import wg.application.entity.User;
import wg.application.exception.TheException;

import java.util.List;

public interface UserService {

    public List<User> list();

    public void update();

    public void updateTestTryCatch(User user);

    public void updateTestPrivate(User user);

    void updateTestThrows(User user) throws TheException;

    void testToolClass(User user);

    public void testThrow(User user);

    public void testThrow2(User user);

    public void testTryAndThrow(User user);

    void testUpdateInterceptor(User user);
}