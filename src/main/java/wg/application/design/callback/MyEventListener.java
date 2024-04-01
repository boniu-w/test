package wg.application.design.callback;

/************************************************************************
 * author: wg
 * description: MyEventListener 
 * createTime: 10:12 2024/4/1
 * updateTime: 10:12 2024/4/1
 ************************************************************************/
public class MyEventListener implements EventListener{
    @Override
    public void onEvent() {
        System.out.println("我订阅的博主更新了");
    }
}
