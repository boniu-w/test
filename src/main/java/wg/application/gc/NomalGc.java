package wg.application.gc;

import java.io.IOException;

/*************************************************************
 * @Package wg.application.gc
 * @author wg
 * @date 2021/3/2 11:24
 * @version
 * @Copyright
 * @discription
 *************************************************************/
public class NomalGc {


    public static void main(String[] args) {
        MyObject o1 = new MyObject();
        MyObject o2 = new MyObject();

        o1 = o2;
        System.gc();
        // o2.finalize();

        // try {
        //     int readChar = System.in.read();
        //     System.out.println(readChar);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

    }

    static class MyObject {

        public MyObject() {
            System.out.println("create");
        }

        public Object object;

        @Override
        protected void finalize() {
            System.out.println("my finalize ---");
        }

    }

}
