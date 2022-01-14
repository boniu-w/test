package wg.application.java17;

import java.io.*;

/************************************************************************
 * @description: 假设 Dog 类中的 Poc 是恶意构造的类，但是正常反序列化是可以成功的。
 * @author: wg
 * @date: 14:47  2021/12/23
 * @params:
 * @return:
 ************************************************************************/
public class SerializeTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Dog dog = new Dog("哈士奇");
        dog.setPoc(new Poc());
        // 序列化 - 对象转字节数组
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(dog);
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        // 反序列化 - 字节数组转对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        System.out.println(object.toString());
    }
}

class Dog implements Serializable {
    private String name;
    private Poc poc;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" + "name='" + name + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Poc getPoc() {
        return poc;
    }

    public void setPoc(Poc poc) {
        this.poc = poc;
    }
}

class Poc implements Serializable {

}

