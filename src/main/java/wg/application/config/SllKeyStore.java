package wg.application.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import sun.misc.BASE64Encoder;

/************************************************************************
 * @author: wg
 * @description: 私钥是无法从证书库中导出的，因为那样非常不安全。如果特别需要私钥或是私钥字符串，只能考虑用编程的方式从密钥库文件中去获取了
 * @params:
 * @return:
 * @createTime: 16:09  2022/2/8
 * @updateTime: 16:09  2022/2/8
 ************************************************************************/
public class SllKeyStore {

    private File keystoreFile;
    private String keyStoreType;
    private char[] password;
    private String alias;
    private File exportedFile;

    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    public void export() throws Exception {
        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        BASE64Encoder encoder = new BASE64Encoder();
        keystore.load(new FileInputStream(keystoreFile), password);
        KeyPair keyPair = getPrivateKey(keystore, alias, password);
        PrivateKey privateKey = keyPair.getPrivate();
        String encoded = encoder.encode(privateKey.getEncoded());
        encoded = encoded.replaceAll("\n", "");

        //将密钥格式化为一行64个字符
        StringBuilder sb = new StringBuilder(encoded);
        int len = 64;
        while (len < sb.length()) {
            sb.insert(len, "\n");
            len += 65;
        }
        FileWriter fw = new FileWriter(exportedFile);
        fw.write("-----BEGIN RSA PRIVATE KEY-----\r\n");//私钥库文件必须以此开头，否则使用时会出错
        System.out.println(sb + "\n");

        fw.write(sb.toString());
        fw.write("\r\n-----END RSA PRIVATE KEY-----");//私钥库文件必须以此结尾
        fw.close();
    }


    public static void main(String args[]) throws Exception {
        SllKeyStore export = new SllKeyStore();

        // 指定自己的密钥库keystore文件 /usr/local/keystore/tomcat9-wg01.keystore
        export.keystoreFile = new File("d:/tomcat.keystore");//读取官钥库keystore文件
        export.keyStoreType = KeyStore.getDefaultType();

        // 指定密钥库密码
        String passwordString = "123456"; //密钥库口令
        export.password = passwordString.toCharArray();

        // 指定密钥库别名
        export.alias = "test";//密钥库别名

        // 指定要生成的私钥目录及文件名
        export.exportedFile = new File("d:/test.key");//生成的私钥文件
        export.export();
    }
}
