package wg.application.controller;

/************************************************************************
* @description: 为什么 要 私有的属性 共有的方法
* @author: wg
* @date:  10:02  2021/9/8
************************************************************************/
public class PrivateAndPublic {
    public static void main(String[] args) {
        BaseVar baseVar = new ChildVar();
        ChildVar childVar = new ChildVar();

        System.out.println(baseVar.id);
        System.out.println(childVar.id);

        // **********************************************
        BaseVar0 baseVar0 = new ChildVar0();
        ChildVar0 childVar0 = new ChildVar0();

        System.out.println("baseVar0.getId() " + baseVar0.getId());
        System.out.println("childVar0.getId() " + childVar0.getId());
        // ↓**********************************************
        BaseVar1 baseVar1 = new ChildVar1();
        ChildVar1 childVar1 = new ChildVar1();

        System.out.println("baseVar1.getId() " + baseVar1.getId());
        System.out.println("childVar1.getId() " + childVar1.getId());
        // ↑**********************************************

        BaseVar2 baseVar2 = new ChildVar2();
        ChildVar2 childVar2 = new ChildVar2();

        System.out.println("baseVar2.getId() " + baseVar2.getId());
        System.out.println("childVar2.getId() " + childVar2.getId());

    }
}

// **************************************************************

class BaseVar {
    public int id = 5;
}

class ChildVar extends BaseVar {
    public int id = 2;
}

// **************************************************************

class BaseVar0 {
    private int id = 5;

    public int getId() {
        return id;
    }
}

class ChildVar0 extends BaseVar0 {
    private int id = 2;
}

// **************************************************************

class BaseVar1 {
    public int id = 5;

    public int getId() {
        return id;
    }
}

class ChildVar1 extends BaseVar1 {
    public int id = 2;
}

// **************************************************************

class BaseVar2 {
    public int id = 5;

    public int getId() {
        return id;
    }
}

class ChildVar2 extends BaseVar2 {
    public int id = 2;

    @Override
    public int getId() {
        return id;
    }
}
