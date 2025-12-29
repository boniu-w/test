package wg.application.threadLocal.controller;

/*************************************************************
 * @Package wg.application.threadLocal.controller
 * @author wg
 * @date 2021/2/22 15:25
 * @version
 * @Copyright
 * @discription
 * 强引用
 * 软引用
 * 弱引用
 * 虚引用
 *************************************************************/
public class M {

    /**
     * @param
     * @return
     * @author wg
     * @description java18 已废弃, cleaner 替代
     * @createTime 11:29 2025/12/29
     * @updateTime 11:29 2025/12/29
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("my finalize");
    }

    public M() {
    }
}
