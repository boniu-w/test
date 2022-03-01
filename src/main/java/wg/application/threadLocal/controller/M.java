package wg.application.threadLocal.controller;

/*************************************************************
 * @Package wg.application.threadLocal.controller
 * @author wg
 * @date 2021/2/22 15:25
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class M {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("my finalize");
    }

    public M() {
    }
}
