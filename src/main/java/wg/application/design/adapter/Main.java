package wg.application.design.adapter;

/************************************************************************
 * author: wg
 * description: 适配器模式
 * createTime: 15:34 2023/4/3
 * updateTime: 15:34 2023/4/3
 ************************************************************************/
public class Main {
    
    public static void main(String[] args) {
        Target target = new Adapter(new Adaptee());
        target.request();
        
    }
}
