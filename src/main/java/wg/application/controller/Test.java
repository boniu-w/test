package wg.application.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wg.application.component.DecipherPhone;
import wg.application.component.TransformTitle;
import wg.application.config.SpringIOCTest;
import wg.application.entity.*;
import wg.application.enumeration.Title;
import wg.application.exception.Assert;
import wg.application.exception.TheException;
import wg.application.service.AspectService;
import wg.application.service.LiuShuiInterface;
import wg.application.service.TestInterface;
import wg.application.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author wg
 * @Package wg.application.controller
 * @date 2020/4/10 10:07
 * @Copyright
 */
@Controller
@RequestMapping(value = "/test")
@EnableAspectJAutoProxy
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    
    //@Autowired
    //LiuShuiMapper liuShuiMapper;
    
    private Result result;
    
    @RequestMapping(value = "/wg")
    @ResponseBody
    public List wg() {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i, i + "--i");
        }
        
        arrayList.add(3, 333);
        
        arrayList.set(2, 222);
        
        return arrayList;
    }
    
    
    /*****************************************************
     * @params:
     * @description: 是否可以作为垃圾回收的 例子呢? 感觉可以
     * @author: wg
     * @date: 2021/7/29 11:22
     *****************************************************/
    @RequestMapping(value = "/test")
    @ResponseBody
    public boolean test() {
        
        System.out.println(result);
        
        result = test2();
        String name = result.getName();
        
        
        result = new Result();
        System.out.println(result);
        
        System.out.println(name);
        
        return false;
    }
    
    /**
     * 通过反射取得entity属性值
     *
     * @author: wg
     * @time: 2020/4/13 16:28
     */
    @RequestMapping(value = "/getEntityParam")
    @ResponseBody
    public String getEntityParam() {
        
        
        try {
            Class<?> aClass = Class.forName("wg.application.entity.Result");
            Class<Result> resultClass = Result.class;
            
            Field[] declaredFields = resultClass.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                String name = declaredFields[i].getName();
                
                Result result = resultClass.newInstance();
                
                
            }
            
            
            Object newInstance = aClass.newInstance();
            if (newInstance instanceof Result) {
                ((Result) newInstance).setName("123");
                String name = ((Result) newInstance).getName();
                System.out.println(name);
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        
        return "hello";
        
    }
    
    public Result test2() {
        result = new Result();
        result.setName("wg");
        return result;
    }
    
    @PostMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) {
        List<BankFlow> bankFlowList = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            BankFlow bankFlow = new BankFlow();
            bankFlow.setId(UUID.randomUUID().toString());
            bankFlow.setTick("---" + i);
            bankFlowList.add(bankFlow);
            
        }
        
        
        System.out.println(bankFlowList.toString());
        
        String[] titles = {"id", "1231", "123123"};
        String fileName = "导出流水表";
        String sheetName = "导出流水";
        
        
        String[][] content = new String[bankFlowList.size()][titles.length];
        
        for (int i = 0; i < bankFlowList.size(); i++) {
            content[i] = new String[titles.length];
            BankFlow bankFlow = bankFlowList.get(i);
            if (bankFlow.getTick().equals("conditionExcel")) {
                content[i][0] = "123123";
                content[i][1] = "asdjhfsakdjfh";
                content[i][2] = "dkhfklsdjf";
            }
            
        }
        
        
        HSSFWorkbook hssfWorkbook = this.getHSSFWorkbook(sheetName, titles, content, null);
        
        try {
            
            this.setResponseHeader(response, fileName);
            OutputStream outputStream = response.getOutputStream();
            hssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * @author: wg
     * @time: 2020/4/15 10:26
     */
    public HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {
        
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        
        //声明列对象
        HSSFCell cell = null;
        
        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        
        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
    
    //@RequestMapping(value = "/exportUtilTest")
    //public void exportUtilTest(HttpServletResponse response) {
    //    List<BankFlow> list = new ArrayList<>();
    //    String sheetName = "123";
    //
    //    for (int i = 0; i < 10; i++) {
    //        BankFlow bankFlow = new BankFlow();
    //        bankFlow.setTick("conditionExcel" + i);
    //        bankFlow.setTransactionAmount(i + 100);
    //        list.add(bankFlow);
    //    }
    //
    //
    //    Class<BankFlow> bankFlowClass = BankFlow.class;
    //    ExcelUtil excelUtil = new ExcelUtil(bankFlowClass);
    //    excelUtil.exportExcel(list, sheetName, response);
    //
    //}
    
    
    @Autowired
    AspectService aspectService;
    
    /*************************************************************
     * 切面
     * 有一种情况 没起作用: this 调用的时候
     * 如果在其他方法调用update()时切面不起作用，可能是因为该方法并没有通过代理对象调用。
     * 在Java AOP中，切面是通过代理实现的。当一个方法被拦截时，AOP框架会生成一个代理对象，并把原始对象的方法调用转发给代理对象。代理对象再调
     * 用目标对象的方法，并在适当的时间点执行切面代码。
     * 如果您在其他方法中直接调用update()方法，而不是通过代理对象调用，那么切面将无法工作。这是因为直接调用方法不会触发代理对象的拦截器链，
     * 自然也就无法执行切面。要确保切面能够正常工作，您需要使用代理对象来调用所有被切入的方法。这通常可以通过依赖注入或手动创建代理对象来实现。
     * 例如，如果您正在使用Spring AOP，则可以通过使用@Autowired将目标对象
     * 注入到其他组件中，并使用注入的目标对象来调用带有切面的方法。
     * @author: wg
     * @time: 2020/4/28 13:20
     *************************************************************/
    @RequestMapping(value = "/testAspect/{userName}")
    @ResponseBody
    public void testAspect(@PathVariable String userName) {
        aspectService.add(userName);
        System.out.println("----------userName" + userName);
    }
    
    /*************************************************************
     * 科学计数法
     * @author: wg
     * @time: 2020/6/15 11:02
     *************************************************************/
    @ResponseBody
    @RequestMapping(value = "/kexuejishufa")
    public double kexuejishufa() {
        double d = 6.22848E+18;
        BigDecimal bigDecimal = new BigDecimal(d);
        System.out.println(bigDecimal);
        
        return d;
    }
    
    /*******************************************************************
     * math
     * @Author wg
     * @Date 2020/9/24 14:59
     *******************************************************************/
    @RequestMapping(value = "testMath")
    @ResponseBody
    public wg.application.vo.Result testMath() {
        
        if (1000 == 1000) {
            logger.info("true");
        }
        
        Integer i = 1000;
        Integer i1 = 1000;
        
        System.out.println((i == i1) + " i==i1 ");
        
        
        return wg.application.vo.Result.ok();
    }
    
    
    /*************************************************************
     * 截字符串
     * @author: wg
     * @time: 2020/6/15 14:02
     *************************************************************/
    @ResponseBody
    @RequestMapping(value = "/splitString")
    public String[] splitString() {
        String[] strings = {"账号", "交易日期", "交易时间", "日志号", "传票号", "产品号", "户名", "交易金额", "交易余额", "交易渠道", "摘要", "1", "2", "交易行号", "对手卡号", "7", "3", "4", "5", "6"};
        
        //String reg = "\\u007c\\u0021";
        String reg = "\\|!";
        String reg1 = "\\!";
        
        String res = "6228270021221339476|!20170526|!162921|!404666835|!EP010000|!1611500899736286|!薛芳                                                                                                |!-64.54|!37481.16|!EPAY|!支付宝    |!                                                                                                    |!                                                                                                    |!029999                        |!19036401948872579               |!                |!支付宝（中国）网络技术有限公司                                                                      |!0.00|!029999|!天津市分行资金清算中心                                      |!|!|!";
        //res = "账号\t交易日期\t交易时间\t日志号\t传票号\t产品号\t户名\t交易金额\t交易余额\t交易渠道\t摘要\t1\t2\t交易行号\t对手卡号\t\t3\t4\t5\t6";
        //reg="\t";
        
        String[] split = res.split(reg);
        String[] finallyString = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            finallyString[i] = split[i].trim();
        }
        
        System.out.println(split.length);
        System.out.println(finallyString.length);
        
        // ******************************************************
        
        String name = "foobarbar";
        String substring = name.substring(5);
        System.out.println(substring);
        
        
        String t = "  bar  ";
        String trim = t.trim();
        System.out.println(t.length());
        System.out.println(trim.length() + "  " + trim);
        
        
        return finallyString;
    }
    
    /*************************************************************
     * hashmap的数据结构 一个例证 put的返回值;
     * @author: wg
     * @time: 2020/6/15 14:34
     *************************************************************/
    @ResponseBody
    @RequestMapping(value = "/obj")
    public Object obj() {
        HashMap map = new HashMap();
        
        Object put = map.put(0, "23");
        System.out.println(put);
        
        Object put1 = map.put(1, "2323");
        System.out.println(put1);
        
        Object put2 = map.put(1, "777");
        System.out.println(put2);
        
        System.out.println(map);
        return put;
    }
    
    /*************************************************************
     * for循环的i++ 是在 方法体内 执行的
     * for (int j = 0; j < 10; j++) {
     *             int i = 0;
     *             for (; i < 10; ) {
     *                 System.out.println(i);
     *             }
     *             i++;
     *             hashMap.put(i, i);
     *         }
     *  这种i++写在外面是不对的, 会无限循环下去
     * @author: wg
     * @time: 2020/6/15 14:51
     *************************************************************/
    @RequestMapping("loopTest")
    @ResponseBody
    private HashMap loopTest() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        
        for (int j = 0; j < 10; j++) {
            int i = 0;
            for (; i < 10; ) {
                System.out.println(i);
                i++;
            }
            hashMap.put(i, i);
        }
        return hashMap;
    }
    
    public void forPP() {
        int l = 10;
        for (int i = 0; i < l; i++) {
            System.out.print(l + " ");
        }
        
        System.out.println();
        for (int j = 0; j < l; ++j) {
            System.out.print(l + " ");
        }
    }
    
    /*************************************************************
     * file
     * @author: wg
     * @time: 2020/6/15 14:59
     *************************************************************/
    @RequestMapping(value = "/getFileInfo")
    @ResponseBody
    public void getFileInfo() {
        try {
            File file = new File("E:\\dir\\流水\\线下调取农行流水\\农行司法查控系统个人活期交易明细查询结果_20200526164539\\E_03_6228270021221339476_1_b9c2d06b-8397-4073-9e03-fce59e7f5554.dat");
            
            String fileName = file.getName();
            String absolutePath = file.getAbsolutePath();
            String canonicalPath = file.getCanonicalPath();
            String parent = file.getParent();
            String path = file.getPath();
            
            
            System.out.println(fileName);
            System.out.println(absolutePath);
            System.out.println(canonicalPath);
            System.out.println(parent);
            System.out.println(path);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*************************************************************
     * 数组 Array
     * @author: wg
     * @time: 2020/6/15 15:16
     *************************************************************/
    @RequestMapping(value = "/arrayTest1")
    @ResponseBody
    public void arrayTest1() {
        String s = "123123.dat";
        String[] split = s.split("\\.");
        System.out.println(split.length);
        
        String substring = s.substring(s.length() - 4);
        if (".dat".equals(substring)) {
            System.out.println("√√√√√√√√√√√√√√√√√");
        } else {
            System.out.println("×××××××××××");
        }
        
        int i = s.lastIndexOf("23");
        
        System.out.println(i + "..............");
        
        
        if (".dat".equals(s.substring(s.lastIndexOf(".")))) {
            System.out.println("nnnnnnnnnnbbbbbbb");
        }
        
    }
    
    /****************************
     * array
     * @Author wg
     * @Date 2020/9/18 14:10
     ****************************/
    @RequestMapping(value = "arrayTest2")
    @ResponseBody
    public wg.application.vo.Result arrayTest2() {
        
        String[] s = {"1", "2", "123"};
        for (String s1 : s) {
            if ("23".contains(s1)) {
                System.out.println(23);
                return wg.application.vo.Result.ok("23");
            }
        }

//        for (String s1 : s) {
//            if ("23".contains(s1)){
//
//            }else {
//                System.out.println(false);
//                return wg.application.vo.Result.ok(false);
//            }
//        }
        
        
        return wg.application.vo.Result.ok(true);
    }
    
    /****************************************************************
     * 有try catch 和 无try catch 的差别
     * 有: 下方打印语句会执行
     * 无: 下方打印语句不会执行
     * @author: wg
     * @time: 2020/6/22 15:17
     ****************************************************************/
    @RequestMapping(value = "/exceptionTest")
    @ResponseBody
    public void exceptionTest() {
        try {
            if (0 == 0) {
                throw new TheException("sha cha");
            }
        } catch (TheException we) {
            
            we.printStackTrace();
        }
        System.out.println("->>>>>>>><<<<<<<<-");
        
    }
    
    /******************************************************************
     * continue
     * @author: wg
     * @time: 2020/6/17 11:03
     ******************************************************************/
    @RequestMapping(value = "/continueTest")
    @ResponseBody
    public void continueTest() {
        int s = 0;
        
        for (int i = 0; i < 10; i++) {
            if (i == 2) {
                
                s = s - 10;
                continue;
            }
            
            
            if (i == 3) {
                System.out.println("true");
                continue;
            }
            
            s = s + 10;
        }
        
        System.out.println(s);
        
        
    }
    
    /******************************************************************
     * lamda
     * @author: wg
     * @time: 2020/6/17 14:50
     ******************************************************************/
    @RequestMapping(value = "/testLamda")
    @ResponseBody
    public void testLamda() {
        String[] array = {"aaaa", "bbbb", "cccc"};
        List<String> list = Arrays.asList(array);
        
        //Java 7
        for (String s : list) {
            System.out.println(s);
        }
        
        //Java 8
        list.forEach(System.out::println);
    }
    
    
    @RequestMapping(value = "/test12")
    @ResponseBody
    public String[] test12(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        String s = "本方账号,本人账号,付款方帐号,付款方账号,付款人账号,付款帐号,付款账号,付款支付帐号,核算账号,交易卡号,交易帐户,交易账号,交易账户,交易主体账号,客户帐号,客户账号,客户账户,内部结算账号,内部帐户,涉案账号,帐号,账 号,账号,转出卡号,转出卡号/账号,转出账户";
        String[] split = s.split(",");
        
        String[] a = split;
        return a;
    }
    
    /****************************************************************
     * 读取json文件
     * @author: wg
     * @time: 2020/6/17 18:16
     ****************************************************************/
    @RequestMapping(value = "getJsonFile")
    @ResponseBody
    public String[] getJsonFile(String path) {
        path = "static/json/nongHangTitles.json";
        ClassPathResource resource = new ClassPathResource(path);
        String[] strings = null;
        try {
            if (resource.exists()) {
                File file = resource.getFile();
                String s = FileUtils.readFileToString(file);
                
                System.out.println(s);
                
                JSONArray jsonArray = JSON.parseArray(s);
                strings = new String[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    strings[i] = (String) jsonArray.get(i);
                }
                
                return strings;
            } else {
                throw new RuntimeException("在 " + path + " 路径下找不到json文件");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
    
    
    /****************************************************************
     * json 文件路径问题 classpath 应该 是项目 配置的 , 我的这个 只能在 resources下,
     * 如要修改,应该是在application.properties 文件中修改,但是 我配置了 不管用
     * @author: wg
     * @time: 2020/6/18 9:28
     ****************************************************************/
    @RequestMapping(value = "/readJson")
    @ResponseBody
    private String readJson(String jsonSrc) {
        //jsonSrc = "classpath:wgjson/nongHangTitles.json";
        jsonSrc = "classpath:static/json/nongHangTitles.json";
        String json = "";
        try {
            //File jsonFile = ResourceUtils.getFile(jsonSrc);
            //json = FileUtils.re.readFileToString(jsonFile);
            //换个写法，解决springboot读取jar包中文件的问题
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(jsonSrc.replace("classpath:", ""));
            
            InputStreamReader reader = new InputStreamReader(resourceAsStream, "utf-8");
            
            int content = 0;
            
            StringBuffer stringBuffer = new StringBuffer();
            while ((content = reader.read()) != -1) {
                stringBuffer.append((char) content);
                
            }
            
            reader.close();
            resourceAsStream.close();
            
            json = stringBuffer.toString();
            System.out.println(json);
            return json;
            
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return json;
    }
    
    /****************************************************************
     * @description: 根据json文件位置读取json  空指针异常, classpath路径里没有wgjson这个文件夹
     * @author: wg
     * @time: 2021/1/28 15:22
     ****************************************************************/
    @RequestMapping(value = "/json/{filename}", method = RequestMethod.GET)
    public String getJsonData(@PathVariable String filename) {
        String jsonpath = "classpath:wg/application/wgjson/" + filename + ".json";
        return getJson(jsonpath);
    }
    
    public String getJson(String jsonSrc) {
        String json = "";
        try {
            String jsonPath = jsonSrc.replace("classpath:", "");
            System.out.println("jsonPath -> " + jsonPath);
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(jsonSrc.replace("classpath:", ""));
            json = IOUtils.toString(resourceAsStream, "UTF-8");
            
            return json;
            
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        
        return json;
    }
    
    
    @Autowired
    TransformTitle transformTitle;
    
    /****************************************************************
     * 关于spring注入的 一些测试
     * @author: wg
     * @time: 2020/6/18 14:38
     ****************************************************************/
    @RequestMapping(value = "/springIOCTest")
    @ResponseBody
    public void springIOCTest() {
        
        //TransformTitle transformTitle = new TransformTitle();
        
        this.transformTitle.test();
        System.out.println(".............");
        
        //////////////////////////////////////////////
        //  现有一个类a 它并没有@component 注解,但其中 有全局定义的 由ioc注入的 变量 b, 例 @Autowired B b;
        //  另一个类 c 引用a的这个方法, 则不能 通过 new a(); 的 方式调用 a中的方法
        //////////////////////////////////////////////
        
        DecipherPhone decipherPhone = new DecipherPhone();
        decipherPhone.test();
        System.out.println(">>>>>>>");
        
    }
    
    
    /****************************************************************
     * 浮点数的等值 判断
     * Java中的解决方法，是通过设立一个阈值来消除计算机计算所带来的误差引起的误差
     * @author: wg
     * @time: 2020/6/18 15:17
     ****************************************************************/
    @RequestMapping(value = "/floatJudge")
    @ResponseBody
    public void floatJudge() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        
        System.out.println(a);
        System.out.println(b);
        
        if (a == b) {
            System.out.println("a == b");
        } else {
            System.out.println("a != b");
        }
        
        final double e = 1E-14;
        BigDecimal bigDecimal = new BigDecimal(e);
        System.out.println("bigDecimal -> " + bigDecimal);
        
        float abs = Math.abs(a - b);
        System.out.println("abs -> " + abs);
        
        
        final double e2 = 1E-7;
        
        if (Math.abs(a - b) < e2) {
            System.out.println("a 终于 == b");
        } else {
            System.out.println("a 还是 != b");
        }
        
        
        BigDecimal a_bigdecimal = new BigDecimal(a);
        BigDecimal b_bigdecimal = new BigDecimal(b);
        
        System.out.println("a_bigdecimal -> " + a_bigdecimal);
        System.out.println("b_bigdecimal -> " + b_bigdecimal);
        
        int i = a_bigdecimal.compareTo(b_bigdecimal);
        
        boolean kk = a_bigdecimal.equals(b_bigdecimal) ? true : false;
        
        System.out.println("i -> " + i);
        
        System.out.println("kk -> " + kk);
        
        
    }
    
    /****************************************************************
     * 除法
     * @author: wg
     * @time: 2020/6/18 15:58
     ****************************************************************/
    @ResponseBody
    @RequestMapping(value = "test23")
    public void test23() {
        long round = Math.round((2 + 3) / 2);
        System.out.println(round);  // 2  为什么是2 因为 "/" 是 取商 操作
        
        long l = Math.round(2.5);
        System.out.println(l); // 3
        
        double ceil = Math.ceil(2.5);
        System.out.println("ceil : " + ceil); // 3
        
        double b = (2d + 3d) / 2d;
        System.out.println("b : " + b);  // 2.5
        
    }
    
    /************************************************************************
     * @author: wg
     * @description: 本机ip
     * @params:
     * @return:
     * @createTime: 10:46  2022/5/5
     * @updateTime: 10:46  2022/5/5
     ************************************************************************/
    @RequestMapping(value = "/ip_test")
    @ResponseBody
    public String ipTest(HttpServletRequest request) {
        
        // String ipAddress = IPUtil.getIpAddress(request);
        // System.out.println("ipAddress: " + ipAddress);
        //
        // System.out.println();
        // String outIPV4 = IPUtil.getOutIPV4();
        // System.out.println("outIPV4: " + outIPV4);
        //
        // String interIP1 = null;
        // String interIP2 = null;
        // try {
        //     interIP1 = IPUtil.getInterIP1();
        //     interIP2 = IPUtil.getInterIP2();
        // } catch (Exception e) {
        //     throw new RuntimeException(e);
        // }
        // System.out.println("interIP1: " + interIP1);
        // System.out.println("interIP2: " + interIP2);
        
        IpUtil.getOutIp();
        
        return "";
    }
    
    @RequestMapping(value = "/computer_test")
    @ResponseBody
    public String computerTest(HttpServletRequest request) {
        ComputerUtil.getHardware();
        
        return "";
    }
    
    /************ ->接口测试 开始 ************/
    /****************************************************************
     * 可以看出 autowired 和  resource 两个注入 的区别
     * autowired 是 先类别 后 按名称
     * resource 是 按 名称
     * @author: wg
     * @time: 2020/6/22 14:33
     ****************************************************************/
    
    @Qualifier(value = "testImpl_2")
    @Autowired
    TestInterface testInterface;
    
    @Resource
    TestInterface testImpl_2;
    
    
    @RequestMapping(value = "/testInterface")
    @ResponseBody
    private String testInterface() {
        
        System.out.println(testInterface.getname());
        
        return testImpl_2.getname();
        
        
    }
    
    /************ -> 接口测试 结束 ************/
    
    
    /****************************************************************
     * ioc test
     * @author: wg
     * @time: 2020/6/22 15:05
     ****************************************************************/
    @ResponseBody
    @RequestMapping(value = "/IOCtest")
    public void IOCtest() {
        // 1.无参构造器 创建 AnnotationConfigApplicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        
        // 2. 设置 需要激活 的环境 允许 test dev 加载
        applicationContext.getEnvironment().setActiveProfiles("test", "dev");
        
        // 3. 开始 注册 配置类
        applicationContext.register(SpringIOCTest.class);
        
        // 4. 刷新容器
        applicationContext.refresh();
        
        // 获取所有bean 的名字
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        
        String[] beanNamesForType = applicationContext.getBeanNamesForType(SpringIOCTest.class);
        for (String s : beanNamesForType) {
            System.out.println("容器的beans名字-- " + s);
        }
        
        applicationContext.close();
        
        
    }
    
    /****************************************************************
     * springframework security 加密
     * @author: wg
     * @time: 2020/6/23 14:44
     ****************************************************************/
    // @RequestMapping(value = "/encode")
    // @ResponseBody
    // private void encode(String password) {
    //     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    //     String encode = encoder.encode("123456");
    //     String encode2 = encoder.encode("111");
    //
    //     System.out.println("encode -> " + encode);
    //     System.out.println(encode2);
    //
    //
    //     boolean matches = encoder.matches("111", "$2a$10$qc90QdnLWW0QHSUGvD95fuXh4.1VDqehenP4xTWtMVbhaymADEhhe");
    //     boolean matches2 = encoder.matches(password, "$2a$10$36OcV5ass16uhYBg0MWkiOZRNjIkAR0/APho.m9UoE4sZhUngsR8K");
    //
    //     System.out.println(matches);
    //     System.out.println("matches2  " + matches2);
    //
    //     if (matches2) {
    //         System.out.println("---------");
    //     }
    //
    //
    //     /************ matches() 源码分析 -> 开始 ************/
    //
    //     String a = "$2a$10$ib.SiGEAJPbl.11WhriGfuFYxwZzp.IlN0U3ttYXOboirSuOqChzy";
    //     String b = "$2a$10$qc90QdnLWW0QHSUGvD95fuXh4.1VDqehenP4xTWtMVbhaymADEhhe";
    //
    //     try {
    //         byte[] a_bytes = a.getBytes("utf-8");
    //         byte[] b_bytes = b.getBytes("utf-8");
    //
    //         if (a_bytes.length != b_bytes.length) {
    //             System.out.println(false);
    //         }
    //
    //         for (int i = 0; i < a_bytes.length; i++) {
    //             System.out.println("a_bytes[" + i + "]   -> " + a_bytes[i]);
    //
    //         }
    //         for (int i = 0; i < b.length(); i++) {
    //
    //             System.out.println("b_bytes[" + i + "]   -> " + b_bytes[i]);
    //         }
    //
    //         if (a_bytes == b_bytes) {
    //             System.out.println("a_bytes == b_bytes  " + true);
    //
    //         }
    //
    //         int result = 0;
    //         // time-constant comparison
    //         for (int i = 0; i < a_bytes.length; i++) {
    //             result |= a_bytes[i] ^ b_bytes[i];
    //         }
    //
    //         System.out.println("result  -> " + result);
    //
    //     } catch (UnsupportedEncodingException e) {
    //         e.printStackTrace();
    //     }
    //     /************ matches() 源码分析 -> 结束 ************/
    //
    //
    // }
    
    
    /****************************************************************
     * mybatisplus test
     * @author: wg
     * @time: 2020/6/23 16:19
     ****************************************************************/
    //@RequestMapping(value = "/mybatisplusTest")
    //@ResponseBody
    //public void mybatisplusTest() {
    //    //String newBeiZhu = "123";
    //    //String sql = "bei_zhu=" + "'" + newBeiZhu + "'";
    //
    //    QueryWrapper<LiuShui> queryWrapper = new QueryWrapper<>();
    //
    //    Integer integer = liuShuiMapper.selectCount(queryWrapper);
    //    System.out.println(integer);
    //
    //    LiuShui liushui = new LiuShui();
    //    liushui.setBeiZhu("<><><><><><><><><><><>");
    //
    //    UpdateWrapper<LiuShui> updateWrapper = new UpdateWrapper<>();
    //    updateWrapper.eq("id", 2).eq("card_no", "11457000000616141");
    //
    //
    //    int update = liuShuiMapper.update(liushui, updateWrapper);
    //
    //    System.out.println(update);
    //
    //}
    
    /****************************************************************
     * 乘法 除法 的移位
     * @author: wg
     * @time: 2020/6/24 10:36
     ****************************************************************/
    @RequestMapping(value = "/transpose")
    @ResponseBody
    public void transpose() {
        
        int a = 10;  // 二进制 1010
        
        int i = a << 1;
        System.out.println(i);  // 20 = a * 2的1次方
        System.out.println("20 的二进制表示 : " + Integer.toBinaryString(i));
        
        
        int i1 = a << 2;
        System.out.println(i1);  // 40 = a * 2的2次方
        
        
        int i2 = a << 3;
        System.out.println(i2);  // 80 = a * 2的3次方
        
        
        int i3 = a * (a ^ 3);  // ^  二进制的异或符
        System.out.println(i3);
        
        // a*3;
        int i4 = (a << 1) + a;
        System.out.println(i4);
        
        
        /* 除法 */
        int i5 = a >> 3;
        System.out.println(i5); // 1
        
        int i6 = (a >> 1) - a;
        System.out.println(i6);
        
        
        /* 与运算符用符号 "&" */
        int i7 = a & 2;
        int i9 = a % 4;
        System.out.println("i7 ->= " + i7 + "   ####  i9 ->= " + i9);  // 2
        
        int i8 = a & 1;
        int i10 = a % 2;
        System.out.println("i8 ->= " + i8 + "   ####  i10->  " + i10);  // 0
        System.out.println();
        
        int n = 1;
        int m = (n << 3) + 2;
        System.out.println("m - > " + m);
        System.out.println(Integer.toBinaryString(m));
        System.out.println();
        int l = Integer.parseInt("12", 3);
        System.out.println("表明 参数 是 3进制数 , 转成十进制后是:  -> " + l);  //
        int p = Integer.parseInt("1010", 2);
        System.out.println("表明 参数 是 2进制数 , 转成十进制后是:  -> " + p);  //
        
        // 负数的 左右 移位
        int k = -1;
        System.out.println("k 的二进制表示 : " + Integer.toBinaryString(k));
        int h = k << 1;
        System.out.println(h);
        System.out.println("h 的二进制表示 : " + Integer.toBinaryString(h));
        
    }
    
    /****************************************************************
     * 枚举测试
     * @author: wg
     * @time: 2020/6/28 9:48
     ****************************************************************/
    @RequestMapping(value = "/enumTest")
    @ResponseBody
    private void enumTest() {
        Title ip = Title.valueOf("TRANSACTION_SUBJECT");
        System.out.println(ip);
    }
    
    /****************************************************************
     * 运算符 测试
     * @author: wg
     * @time: 2020/6/28 11:52
     ****************************************************************/
    @RequestMapping(value = "/operatorTest")
    @ResponseBody
    public void operatorTest() {
        
        /* 与运算符 "&" */
        int a = 129;
        int b = 128;
        int i = a & b;
        System.out.println("a & b   " + i); // 128
        System.out.println("b & a   " + (b & a));  // 128
        
        int k = a | b;
        System.out.println("a | b   " + k);  // 129
        
        int kk = b | a;
        System.out.println("b | a    " + kk);   // 129
        
        
        
        
        /* 01 10  */
        int m = 1;
        int n = 2;
        System.out.println("1&2  -> " + (1 & 2));  // 0
        System.out.println("1|2  ->  " + (1 | 2));  // 3
        
        
    }
    
    
    @RequestMapping(value = "/byteTest")
    @ResponseBody
    public void byteTest() {
        try {
            String s = "我$2a$10$ib.SiGEAJPbl.11WhriGfuFYxwZzp.IlN0U3ttYXOboirSuOqChzy";
            
            
            byte[] utf8_bytes = s.getBytes("utf-8");
            byte[] iso88591_bytes = s.getBytes("ISO-8859-1");
            byte[] utf16_bytes = s.getBytes("utf-16");
            
            System.out.println("s.length() -> " + s.length());
            System.out.println(utf8_bytes.length + "  utf8_bytes -> " + Arrays.toString(utf8_bytes));
            System.out.println(iso88591_bytes.length + "  iso88591_bytes -> " + Arrays.toString(iso88591_bytes));
            System.out.println(utf16_bytes.length + "  utf16_bytes -> " + Arrays.toString(utf16_bytes));
            
            System.out.println();
            
            String name = "我";
            
            String isoName = new String(name.getBytes("utf-8"), "iso-8859-1");
            System.out.println("isoName -> " + isoName);
            
            
            String utf8Name = new String(isoName.getBytes("iso-8859-1"), "utf-8");
            System.out.println("utf8Name -> " + utf8Name);
            
            
            String iso = new String(name.getBytes(), "iso-8859-1");
            System.out.println("iso -> " + iso);
            
            String s1 = new String(iso.getBytes(), "utf-8");
            System.out.println("s1 -> " + s1);
            
            String s2 = new String(iso.getBytes("iso-8859-1"), "utf-8");
            System.out.println("s2 -> " + s2);
            
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    
    /****************************************************************
     * 三元
     * 断路 双的是断路
     * 按位与 按位或
     * @author: wg
     * @time: 2020/7/7 15:27
     ****************************************************************/
    @RequestMapping(value = "sanYuan")
    @ResponseBody
    public void sanYuan() {
        boolean b = 1 > 2 || 1 > 0 ? false : true;
        
        System.out.println(b); // false
        
        int i = 0;
        if (i++ == 0 | i++ == 1) {
            System.out.println(i + "  ---");
        }
        
        if (i++ == 3 | i++ == 3) {
            System.out.println(i + " 4 ]]]]");
        }
        
        if (i++ == 4 & i++ == 5) {
            System.out.println("===");
        }
        
        if (i++ == 6 & i++ == 7) {
            System.out.println("---");
        }
        
        
    }
    
    /****************************************************************
     * streamTest
     * peek接收一个Consumer，而map接收一个Function。
     * Consumer是没有返回值的，它只是对Stream中的元素进行某些操作，但是操作之后的数据并不返回到Stream中，所以Stream中的元素还是原来的元素。
     * 而Function是有返回值的，这意味着对于Stream的元素的所有操作都会作为新的结果返回到Stream中
     * @author: wg
     * @time: 2020/7/8 10:18
     ****************************************************************/
    @RequestMapping(value = "streamTest")
    @ResponseBody
    public void streamTest() {
        List<String> collect = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        
        System.out.println(collect);
        
        ArrayList<String> arrayList = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(String.valueOf(i));
            hashMap.put(String.valueOf(i), "String.valueOf " + i);
        }
        
        arrayList.stream().filter(item -> item.equals(String.valueOf(2))).forEach(item -> System.out.println(item));
        
        hashMap.forEach((key, value) -> {
            if ("5".equals(key)) {
                value = value.concat("123");
                arrayList.clear();
            }
            System.out.println("arrayList.size() -> " + arrayList.size());
            System.out.println(key + " -> " + value);
        });
    }
    
    /****************************************************************
     * local time date
     * @author: wg
     * @time: 2020/7/8 14:03
     ****************************************************************/
    public void dateTest() {
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        
        LocalDateTime of = LocalDateTime.of(localDate, localTime);
        System.out.println(of);
        
        DayOfWeek dayOfWeek = DayOfWeek.from(localDateTime);
        System.out.println(dayOfWeek);
        
        LocalDateTime atTime = localDate.atTime(14, 25, 8);
        System.out.println("atTime -> " + atTime);
        
        
        int dayOfYear = localDateTime.getDayOfYear();
        System.out.println(dayOfYear);
        
        int monthValue = localDateTime.getMonthValue();
        System.out.println(monthValue);
        
        Month month = localDateTime.getMonth();
        System.out.println(month);
        
        Date date = new Date();
        System.out.println(date);
        
        
        Instant instant = Instant.now();
        System.out.println("类似时间戳 instant -> " + instant);
        
        LocalDateTime atTime1 = localDate.atTime(8, 0, 0);
        Duration duration = Duration.between(atTime1, atTime);
        
        int nano = duration.getNano();
        System.out.println("nano -> " + nano);
        
        long l = duration.toNanos();
        System.out.println(l);
        
        Duration between = Duration.between(atTime, atTime1);
        int nano1 = between.getNano();
        System.out.println(nano1);
        
        long l1 = between.toNanos();
        System.out.println(l1);
        
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(availableZoneIds.size() + " : " + availableZoneIds);
        
        ZoneId americaPanamaZone = ZoneId.of("America/Panama");
        ZonedDateTime americaDateTime = ZonedDateTime.of(localDateTime, americaPanamaZone);
        System.out.println("americaDateTime -> " + americaDateTime);
        
    }
    
    @Autowired
    LiuShuiInterface liuShuiInterface;
    
    /****************************************************************
     * jpa 
     * @author: wg
     * @time: 2020/8/4 15:55
     ****************************************************************/
    @RequestMapping(value = "/getAllLiuShui")
    @ResponseBody
    public wg.application.vo.Result getAllLiuShui() {

//        LiuShui one = liuShuiInterface.getOne();
//
//        //System.out.println(one);
//
//        String s = JSON.toJSONString(one);
//        System.out.println(s);
        
        List<LiuShui> liuShuis = liuShuiInterface.getByJiaoYiJinErBetween(1d, 2d);
        
        
        return wg.application.vo.Result.ok(liuShuis);
    }
    
    /****************************************************************
     * 为true 后面就不走了
     * @author: wg
     * @time: 2020/8/25 10:05
     ****************************************************************/
    @RequestMapping(value = "/ifElseTest")
    @ResponseBody
    public String ifElseTest() {
        String s = "123";
        if (s.equals("")) {
            System.out.println(1);
        } else if (1 == 1) {
            System.out.println(2);
        } else if (true) {
            System.out.println(3);
        }
        
        return s;
    }
    
    /****************************************************************
     * 字符
     * @author: wg
     * @time: 2020/8/27 10:21
     ****************************************************************/
    public void zifu() {
        System.out.println(123 + "\u0020" + 123 + "\u3000" + 123);
        System.out.println("---------");
    }
    
    
    /*******************************************************************
     * 转数组
     * @Author wg
     * @Date 2020/10/12 17:46
     *******************************************************************/
    @RequestMapping(value = "parseToArray")
    @ResponseBody
    public void parseToArray() {
        
        String s = "备付金、京东支付、财付通、富友、天翼、汇潮、捷付睿、连连银通、九派天下、易宝、宝付、快钱、盛付通、传化、" +
                "融宝、资和信、润物、石基、一九付、银盛、网易宝、易付宝、网银在线、东方付通、拉卡拉、" +
                "迅付、快捷通、腾付通、美的、讯联智付、快付通、商物通、新浪、易通金服、银生宝、甬易、" +
                "亿付、随行付、雅酷时空、合众易宝、瑞银信、畅捷通、理房通、百联优力、爱农驿站、高汇通、" +
                "圣亚云鼎、国付宝、先锋、汇聚、银联商务、网上有名、汇付、商银信、易极付、广州银联、钱袋宝、" +
                "邦付宝、联付通、证联、汇元银通、市民卡、百付宝、中移电子、支付宝、瀚银、中付、金运通、" +
                "双乾、天下支付、电银、乐刷、杉德、银盈通、山东省电子、亚科、中金、投科信、杭州银行、敏付" +
                "、桂林银行、山西万卡德、山东高速信联、银视通、江苏省电子商务、平安银行、郑州建业至尊、" +
                "青岛百达通、江苏瑞祥商务、青岛百森通、北京商银、郑州建业至尊商务、安徽华夏、四川商通实业、" +
                "裕福支付、陕西煤炭交易中心、昆明卡互卡、北京数码视讯、浙江航天电子、新生、上海付费通、" +
                "上海东方汇融、安付宝、易生、易票联、易联、广东盛迪嘉电子、得仕、中钢银通信、唯品会、" +
                "顺丰恒通、广州合利宝、商盟商务服务、易智付、通联支付网络服务、上海银联电子、恒大万通、" +
                "钱宝、本元、平安付、智付、开联通、联动北京银联商务、平安付电子、现代金融控股、联通支付、" +
                "东方电子支付、摩宝、首采联合电子";
        
        String[] strings = s.split("、");
        
        String[] st = new String[strings.length];
        
        for (int i = 0; i < strings.length; i++) {
            st[i] = strings[i].trim();
        }
        
        System.out.println(Arrays.toString(strings));
        System.out.println(Arrays.toString(st));
        
        
    }
    
    /****************************************************************
     *
     * @author: wg
     * @time: 2020/10/14 10:48
     ****************************************************************/
    @RequestMapping(value = "/getPrescription")
    @ResponseBody
    public wg.application.vo.Result getPrescription() {
        double sqrt = Math.sqrt(25d);
        
        double sqrt1 = Math.sqrt(24d);
        System.out.println("24d 开平方 : " + sqrt1);
        
        return wg.application.vo.Result.ok(sqrt);
    }
    
    String mysqlDriver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/wg";
    String user = "root";
    String password = "123456";
    
    public void createDynamicTable() {
        
        HashMap<String, Object> hashMap = new HashMap<>();
        
        hashMap.put("tableName", "user");
        hashMap.put("id", new FieldMy("id", "int"));
        hashMap.put("userName", new FieldMy("user_name", "varchar2(200)"));
        
        String sql = "CREATE TABLE " + hashMap.get("tableName") + "  (\n" +
                ((FieldMy) hashMap.get("id")).getField() + "  " + ((FieldMy) hashMap.get("id")).getFieldType() + " not null, \n" +
                ((FieldMy) hashMap.get("userName")).getField() + "  " + ((FieldMy) hashMap.get("userName")).getFieldType() + " not null, \n" +
                "    `create_by` varchar(32) NULL DEFAULT NULL COMMENT '创建人',\n" +
                "    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "    `update_by` varchar(32) NULL DEFAULT NULL COMMENT '更新人',\n" +
                "    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',\n" +
                
                "        PRIMARY KEY (`id`) USING BTREE\n" +
                "        )";
        
        try {
            Class.forName(mysqlDriver);
            DriverManager.getConnection(url, user, password);
            Connection conn = null;
            Statement statement = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void getConnection() {
        try {
            Class.forName(mysqlDriver);
            DriverManager.getConnection(url, user, password);
            Connection conn = null;
            Statement statement = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @GetMapping(value = "jdbcexecute")
    @ResponseBody
    public wg.application.vo.Result<List<User>> jdbcQuery() {
        wg.application.vo.Result<List<User>> userResult = new wg.application.vo.Result<List<User>>();
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = JdbcUtil.jdbcQuery("select * from user");
            // ResultSet resultSet = JdbcUtil.jdbcQuery1("select * from user"); // java.sql.SQLException: Operation not allowed after ResultSet closed
            
            Assert.notNull(resultSet, "resultSet cannot be null");
            if (resultSet != null) {
                while (resultSet.next()) {
                    User user1 = new User();
                    String name = resultSet.getString("name");
                    user1.setName(name);
                    System.out.println("name : " + name);
                    Date birthday = resultSet.getTimestamp("birthday");
                    if (birthday != null) {
                        LocalDateTime localDateTime = DateUtil.toLocalDateTime(birthday);
                        user1.setBirthday(localDateTime);
                        System.out.println("localDateTime : " + localDateTime);
                        System.out.println("birthday : " + birthday);
                    }
                    users.add(user1);
                }
            }
            
            userResult.setData(users);
            return userResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                JdbcUtil.closeConn();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
    }
    
    /*****************************************************
     * @params:
     * @description:
     * @author: wg
     * @date: 2021/7/28 9:48
     *****************************************************/
    @RequestMapping(value = "/datavApiTest")
    @ResponseBody
    public ArrayList<HashMap<String, String>> datavApiTest(@RequestParam(name = "platformId") String platformId) {
        System.out.println(platformId);
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "平台" + i);
            map.put("field", "能耗" + i);
            map.put("data", "1000万" + i);
            list.add(i, map);
        }
        
        return list;
    }
    
    @PostMapping(value = "/aa")
    @ResponseBody
    public void aa(@RequestBody List<User> users) {
        System.out.println("user.getUsers().size() :  " + users);
    }
    
    // @Resource(name = "animal")
    // public Animal animal;
    
    /*****************************************************
     * @params:
     * @description: 测试 @Resource
     * @author: wg
     * @date: 2021/8/5 17:38
     *****************************************************/
    @RequestMapping(value = "/testResource")
    @ResponseBody
    public void testResource() {
        // System.out.println(animal.getGenus());
    }
    
    @GetMapping(value = "/houjian")
    @ResponseBody
    public ArrayList<HashMap<String, Object>> houjian() {
        ArrayList<HashMap<String, Object>> maps = new ArrayList<>();
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("platform", "CEPJ-WHPE注水" + i);
            map.put("val", a[random.nextInt(a.length)]);
            map.put("warn", "蓝色预警");
            map.put("message", "123");
            
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("platform", "CEPJ-WHPE注水" + i);
            map1.put("val", a[random.nextInt(a.length)]);
            map1.put("warn", "黄色预警");
            map1.put("message", "sdfsdf");
            
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("platform", "CEPJ-WHPE注水" + i);
            map2.put("val", a[random.nextInt(a.length)]);
            map2.put("warn", "红色预警");
            map2.put("message", "23123asdasd");
            
            maps.add(map);
            maps.add(map1);
            maps.add(map2);
        }
        return maps;
    }
    
    @PostMapping(value = "/test_fanxing")
    @ResponseBody
    public <T> void testFanXing(@ModelAttribute Class<T> user, User user1) {
        System.out.println("  ModelAttribute ");
        try {
            // Class<? extends Class> aClass = user.getClass();
            Field name = user.getDeclaredField("name");
            name.setAccessible(true);
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name.getName(), user);
            Method readMethod = propertyDescriptor.getReadMethod();
            String val = readMethod.invoke(user.cast(user1)).toString();
            System.out.println(val);
        } catch (NoSuchFieldException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    /************************************************************************
     * @description: 通过反射 set
     * @author: wg
     * @date: 13:32  2021/11/8
     * @params:
     * @return:
     ************************************************************************/
    public void setter(Object obj, String fieldName, Object attributeValue) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor p = new PropertyDescriptor(fieldName, obj.getClass());
            // for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            //     if (fieldName.equals(propertyDescriptor.getName())) p = propertyDescriptor;
            // }
            Method writeMethod = p.getWriteMethod();
            writeMethod.invoke(obj, attributeValue);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    @GetMapping(value = "/test_message_source")
    public void testMessageSource() {
        String message = MessageUtils.getMessage(10001);
        System.out.println(message);
        
        message = MessageUtils.getMessage(10001, "skdjhfs");
        System.out.println(message);
        
        // Locale.US 不能用 Locale.ENGLISH 替代, 因为配置文件的名字 不匹配
        String message1 = MessageUtils.getMessage(10002, null, Locale.US);
        System.out.println(message1);
        return;
    }
    
}
