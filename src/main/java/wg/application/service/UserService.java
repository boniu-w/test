package wg.application.service;

import wg.application.entity.User;

import java.util.List;

public interface UserService {

    public List<User> list();

    public void update();
    public void updateTestTryCatch(User user);
    public void updateTestPrivate(User user);
}