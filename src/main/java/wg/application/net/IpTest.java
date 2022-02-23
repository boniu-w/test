package wg.application.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpTest {

    public static void main(String[] args) {
        getIp();
    }

    public static void getIp() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            byte[] address = localHost.getAddress();
            System.out.println();

            InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
            System.out.println();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
