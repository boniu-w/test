// package wg.application.java17;
//
// import java.io.*;
//
// /************************************************************************
//  * @description: 在 Java 17 中可以自定义反序列化过滤器，拦截不允许的类。
//  * 这时反序列化会得到异常。
//  * Exception in thread "main" java.io.InvalidClassException: filter status: REJECTED
//  * 	at java.base/java.io.ObjectInputStream.filterCheck(ObjectInputStream.java:1412)
//  * 	at java.base/java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:2053)
//  * 	at java.base/java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1907)
//  * 	....
//  * @author: wg
//  * @date: 14:50  2021/12/23
//  * @params:
//  * @return:
//  ************************************************************************/
// public class SerializeTest02 {
//     public static void main(String[] args) throws IOException, ClassNotFoundException {
//         Dog dog = new Dog("哈士奇");
//         dog.setPoc(new Poc());
//         // 序列化 - 对象转字节数组
//         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//         try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
//             objectOutputStream.writeObject(dog);
//         }
//         byte[] bytes = byteArrayOutputStream.toByteArray();
//         // 反序列化 - 字节数组转对象
//         ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//         ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//         // 允许 java17.Dog 类，允许 java.base 中的所有类，拒绝其他任何类
//         ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(
//                 "wg.application.java17.Dog;java.base/*;!*");
//         objectInputStream.setObjectInputFilter(filter);
//         Object object = objectInputStream.readObject();
//         System.out.println(object.toString());
//     }
// }
