package wg.application;

import org.junit.Test;
import wg.application.entity.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;

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
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == BigDecimal.class) {
                System.out.println("field.getName() = " + field.getName());
            }
        }
    }
}
