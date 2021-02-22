package wg.application.threadLocal.controller;

import java.io.IOException;

/*************************************************************
 * @Package wg.application.threadLocal.controller
 * @author wg
 * @date 2021/2/22 15:26
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class NomalRerfence {

    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;
        System.gc();

        System.in.read();
    }

}
