package wg.application.interfaces;

import wg.application.entity.User;

/************************************************************************
 * author: wg
 * description: FuntionInterfaceTest 
 * createTime: 10:35 2024/3/1
 * updateTime: 10:35 2024/3/1
 ************************************************************************/
@FunctionalInterface
public interface FuntionInterfaceTest {

    // 用到的时候再具体制定逻辑
    public abstract String named(User user);
}
