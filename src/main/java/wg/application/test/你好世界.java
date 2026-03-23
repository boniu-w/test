package wg.application.test;

import java.util.Random;

public class 你好世界 {
    public static void main(String[] args) {
        String 姓名 = "张三";
        int 年龄 = 25;

        if (年龄 >= 18) {
            System.out.println(姓名 + "是成年人");
        }

        String[] 职业 = {"战士", "骑士", "dk", "萨满", "猎人", "龙人", "dh", "dz", "德", "法师", "术士", "牧师", "武僧"};
        Random random = new Random();
        int i = random.nextInt(职业.length);
        System.out.println("i = " + i);

        String string = 职业[i];
        System.out.println("string = " + string);
    }
}