package wg.application.design.observer;

import wg.application.entity.User;

import java.util.ArrayList;
import java.util.List;

/************************************************************************
 * author: wg
 * description: Moments 
 * createTime: 15:11 2023/11/13
 * updateTime: 15:11 2023/11/13
 ************************************************************************/
public class Moments implements ObserverMsg {
    private List<User> userList;
    private String message;

    public Moments() {
        this.userList = new ArrayList<>();
        this.message = "";
    }

    // 增加关注的好友
    public void addUser(User user) {
        if (!userList.contains(user)) {
            userList.add(user);
        }
    }

    // 删除关注的好友
    public void deleteUser(User user) {
        userList.remove(user);
    }

    public void setMessage(String message) {
        this.message = message;
        // setChanged(); // 标记状态改变
        // notifyObservers(message); // 通知所有的观察者
    }

    @Override
    public void update(String msg) {

    }

    public void registerObserver(User user) {

    }

   public void notifyObservers(){

    }
}
