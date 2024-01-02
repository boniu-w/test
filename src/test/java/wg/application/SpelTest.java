package wg.application;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Date;

/************************************************************************
 * author: wg
 * description: SpelTest 
 * createTime: 15:35 2024/1/2
 * updateTime: 15:35 2024/1/2
 ************************************************************************/
@SpringBootTest
public class SpelTest {

    @Test
    public void test0() {
        ExpressionParser parser = new SpelExpressionParser();

        boolean result1 = parser.parseExpression("2>1 and (!true or !false)").getValue(boolean.class);
        boolean result2 = parser.parseExpression("2>1 && (!true || !false)").getValue(boolean.class);

        boolean result3 = parser.parseExpression("2>1 and (NOT true or NOT false)").getValue(boolean.class);
        boolean result4 = parser.parseExpression("2>1 && (NOT true || NOT false)").getValue(boolean.class);

        System.out.println("result1=" + result1);
        System.out.println("result2=" + result2);
        System.out.println("result3=" + result3);
        System.out.println("result4=" + result4);
    }

    @Test
    public void test2() {
        ExpressionParser parser = new SpelExpressionParser();

        String str1 = parser.parseExpression("'Hello World!'").getValue(String.class);
        int int1 = parser.parseExpression("1").getValue(Integer.class);
        long long1 = parser.parseExpression("-1L").getValue(long.class);
        float float1 = parser.parseExpression("1.1").getValue(Float.class);
        double double1 = parser.parseExpression("1.1E+2").getValue(double.class);
        int hex1 = parser.parseExpression("0xa").getValue(Integer.class);
        long hex2 = parser.parseExpression("0xaL").getValue(long.class);
        boolean true1 = parser.parseExpression("true").getValue(boolean.class);
        boolean false1 = parser.parseExpression("false").getValue(boolean.class);
        Object null1 = parser.parseExpression("null").getValue(Object.class);

        System.out.println("str1=" + str1);
        System.out.println("int1=" + int1);
        System.out.println("long1=" + long1);
        System.out.println("float1=" + float1);
        System.out.println("double1=" + double1);
        System.out.println("hex1=" + hex1);
        System.out.println("hex2=" + hex2);
        System.out.println("true1=" + true1);
        System.out.println("false1=" + false1);
        System.out.println("null1=" + null1);
    }

    @Test
    public void test3() {
        ExpressionParser parser = new SpelExpressionParser();
        boolean v1 = parser.parseExpression("1>2").getValue(boolean.class);
        boolean between1 = parser.parseExpression("1 between {1,2}").getValue(boolean.class);
        System.out.println("v1=" + v1);
        System.out.println("between1=" + between1);
    }

    /**
     * @author: wg
     * @description: 类类型
     * 正则: 使用“str matches regex，如“'123' matches '\d{3}'”将返回true；
     * @params:
     * @return:
     * @createTime: 15:38  2024/1/2
     * @updateTime: 15:38  2024/1/2
     */
    @Test
    public void testClassTypeExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //java.lang包类访问
        Class<String> result1 = parser.parseExpression("T(String)").getValue(Class.class);
        System.out.println(result1);

        //其他包类访问
        String expression2 = "T(com.javacode2018.spel.SpelTest)";
        Class<SpelTest> value = parser.parseExpression(expression2).getValue(Class.class);
        System.out.println(value == SpelTest.class);

        //类静态字段访问
        int result3 = parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);
        System.out.println(result3 == Integer.MAX_VALUE);

        //类静态方法调用
        int result4 = parser.parseExpression("T(Integer).parseInt('1')").getValue(int.class);
        System.out.println(result4);
    }

    /**
     * @author: wg
     * @description: 类实例
     * @params:
     * @return:
     * @createTime: 15:39  2024/1/2
     * @updateTime: 15:39  2024/1/2
     */
    @Test
    public void testConstructorExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String result1 = parser.parseExpression("new String('路人甲java')").getValue(String.class);
        System.out.println(result1); // 路人甲java

        Date result2 = parser.parseExpression("new java.util.Date()").getValue(Date.class);
        System.out.println(result2); // Tue Jan 02 15:39:47 CST 2024
    }

    /**
     * @author: wg
     * @description: instanceof
     * @params:
     * @return:
     * @createTime: 15:40  2024/1/2
     * @updateTime: 15:40  2024/1/2
     */
    @Test
    public void testInstanceOfExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        Boolean value = parser.parseExpression("'路人甲' instanceof T(String)").getValue(Boolean.class);
        System.out.println(value); // true
    }

    /**
     * @author: wg
     * @description: 变量定义及引用
     * 使用"#root"引用根对象，使用"#this"引用当前上下文对象；
     * @params:
     * @return:
     * @createTime: 15:40  2024/1/2
     * @updateTime: 15:40  2024/1/2
     */
    @Test
    public void testVariableExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", "路人甲java");
        context.setVariable("lesson", "Spring系列");

        //获取name变量，lesson变量
        String name = parser.parseExpression("#name").getValue(context, String.class);
        System.out.println(name); // 路人甲java
        String lesson = parser.parseExpression("#lesson").getValue(context, String.class);
        System.out.println(lesson); // Spring系列

        //StandardEvaluationContext构造器传入root对象，可以通过#root来访问root对象
        context = new StandardEvaluationContext("我是root对象");
        String rootObj = parser.parseExpression("#root").getValue(context, String.class);
        System.out.println(rootObj); // 我是root对象

        //#this用来访问当前上线文中的对象
        String thisObj = parser.parseExpression("#this").getValue(context, String.class);
        System.out.println(thisObj); // 我是root对象
    }

    /**
     * @author: wg
     * @description: 自定义函数
     * @params:
     * @return:
     * @createTime: 15:43  2024/1/2
     * @updateTime: 15:43  2024/1/2
     */
    @Test
    public void testFunctionExpression() throws SecurityException, NoSuchMethodException {
        //定义2个函数,registerFunction和setVariable都可以，不过从语义上面来看用registerFunction更恰当
        StandardEvaluationContext context = new StandardEvaluationContext();
        Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
        context.registerFunction("parseInt1", parseInt);
        context.setVariable("parseInt2", parseInt);

        ExpressionParser parser = new SpelExpressionParser();
        System.out.println(parser.parseExpression("#parseInt1('3')").getValue(context, int.class)); // 3
        System.out.println(parser.parseExpression("#parseInt2('3')").getValue(context, int.class)); // 3

        String expression1 = "#parseInt1('3') == #parseInt2('3')";
        boolean result1 = parser.parseExpression(expression1).getValue(context, boolean.class);
        System.out.println(result1); // true
    }

    /**
     * @author: wg
     * @description: 表达式赋值
     * @params:
     * @return:
     * @createTime: 15:45  2024/1/2
     * @updateTime: 15:45  2024/1/2
     */
    @Test
    public void testAssignExpression1() {
        Object user = new Object() {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return "$classname{" +
                        "name='" + name + '\'' +
                        '}';
            }
        };
        {
            //user为root对象
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext context = new StandardEvaluationContext(user);
            parser.parseExpression("#root.name").setValue(context, "路人甲java");
            System.out.println(parser.parseExpression("#root").getValue(context, user.getClass()));
        }
        {
            //user为变量
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext context = new StandardEvaluationContext();
            context.setVariable("user", user);
            parser.parseExpression("#user.name").setValue(context, "路人甲java");
            System.out.println(parser.parseExpression("#user").getValue(context, user.getClass()));
        }
    }
}
