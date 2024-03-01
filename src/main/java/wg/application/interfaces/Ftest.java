package wg.application.interfaces;

import wg.application.entity.User;

/************************************************************************
 * author: wg
 * description: Ftest 
 * createTime: 10:37 2024/3/1
 * updateTime: 10:37 2024/3/1
 ************************************************************************/
public class Ftest {


    public static User test(User user, FuntionInterfaceTest funtionInterfaceTest) {
        String name = funtionInterfaceTest.named(user);
        user.setName(name);
        return user;
    }

    public static User test1(User user, FuntionInterfaceTest funtionInterfaceTest) {
        funtionInterfaceTest.named(user);
        return user;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(11111L);
        user.setName("wg");

        // 用到的时候再具体制定逻辑
        FuntionInterfaceTest funtionInterfaceTest = (u) -> "111";
        test(user, funtionInterfaceTest);
        System.out.println("user1 = " + user.getName()); // 1111

        // ****************************************************************************************************************************** //
        User user2 = new User();
        user2.setId(11111L);
        user2.setName("2222222");
        FuntionInterfaceTest funtionInterfaceTest1 = (u) -> {
            u.setName("333333");
            return u.getName();
        };

        test1(user2, funtionInterfaceTest1);
        System.out.println("user = " + user2.getName()); // 333333
    }
}
