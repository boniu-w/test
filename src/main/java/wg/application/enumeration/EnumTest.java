package wg.application.enumeration;

/*************************************************************
 * @Package wg.application.enumeration
 * @author wg
 * @date 2020/8/24 9:55
 * @version
 * @Copyright
 *************************************************************/
public enum EnumTest {

    MAX_INT {
        public int getMaxInt() {
            return Integer.MAX_VALUE;
        }
    };

    public int getMaxInt() {

        throw new AbstractMethodError();
    }

    public void sleep(long timeout) throws InterruptedException {

        Thread.sleep(timeout);
    }

}
