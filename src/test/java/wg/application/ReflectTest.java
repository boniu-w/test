package wg.application;

import org.junit.Test;
import wg.application.entity.User;
import wg.application.function.StringLength;
import wg.application.service.UserService;
import wg.application.service.impl.UserServiceImpl;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;

/************************************************************************
 * author: wg
 * description: RelectTest 
 * createTime: 15:36 2023/8/9
 * updateTime: 15:36 2023/8/9
 ************************************************************************/
public class ReflectTest {

    /**
     * @author: wg
     * @description:
     * @params:
     * @return:
     * @createTime: 16:15  2023/8/9
     * @updateTime: 16:15  2023/8/9
     */
    @Test
    public void test1() {
        Field[] declaredFields = User.class.getDeclaredFields(); // 这个方法返回所有在 User 类中声明的字段，不论其访问修饰符是什么。
        Field[] fields = User.class.getFields(); // 仅返回公有访问权限的字段，私有、受保护和默认访问权限的字段不会包含在内。
        String simpleName = User.class.getSimpleName(); // 它返回类名而不包含其完整包路径。

        /*
         这个方法返回一个表示类的直接超类的 Type 对象，其中包含有关超类泛型信息的信息。如果超类没有泛型参数，返回的将是 Class 对象。
         这在需要获取超类的泛型参数信息时非常有用，例如，您可以用于在运行时检查父类的泛型类型。
        */
        Type genericSuperclass = User.class.getGenericSuperclass();

        /*
         这个方法返回类的直接超类的 Class 对象。它仅返回超类的类对象，不包含关于泛型参数的信息。
         如果要获取超类的类对象而不考虑泛型信息，这个方法是合适的。
         */
        Class<? super User> superclass = User.class.getSuperclass();

        /*
         这个方法在 Java 8 中引入，用于获取直接超类的注解信息。它返回一个 AnnotatedType 对象，其中包含有关超类的注解信息。
         这在需要在运行时检查父类的注解信息时非常有用。
        */
        AnnotatedType annotatedSuperclass = User.class.getAnnotatedSuperclass();

        // 用于获取实现类的所有接口信息 及 泛型信息
        Type[] genericInterfaces = UserService.class.getGenericInterfaces();
        Type[] genericInterfaces1 = UserServiceImpl.class.getGenericInterfaces();
        Type[] genericInterfacesStringLength = StringLength.class.getGenericInterfaces();

        // 用于获取类的规范名。规范名是一个包含类的完整包路径的字符串，以便在不同的包中使用。
        String canonicalName = User.class.getCanonicalName();

        // ****************************************************************************************************************************** //

        for (Field field : declaredFields) {
            System.out.println("field = " + field.getName());
            if (field.getType() == BigDecimal.class) {
                System.out.println("field.getName() = " + field.getName());
            }
        }

        System.out.println();
        for (Field field : fields) {
            System.out.println("field.getName() = " + field.getName());
        }

        System.out.println("simpleName = " + simpleName); // User
        System.out.println("genericSuperclass = " + genericSuperclass); // class java.lang.Object
        System.out.println("genericSuperclass.getTypeName() = " + genericSuperclass.getTypeName()); // java.lang.Object

        System.out.println("genericInterfaces = " + Arrays.toString(genericInterfaces)); // []
        System.out.println("genericInterfaces1 = " + Arrays.toString(genericInterfaces1)); // [interface wg.application.service.UserService]
        System.out.println("genericInterfacesStringLength = " + Arrays.toString(genericInterfacesStringLength)); // [java.util.function.Function<java.lang.String, java.lang.Integer>]
        System.out.println("canonicalName = " + canonicalName); // wg.application.entity.User

    }

    @Test
    public void test2() {

    }
}
