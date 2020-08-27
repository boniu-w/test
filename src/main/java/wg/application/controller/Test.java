package wg.application.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wg.application.component.DecipherPhone;
import wg.application.component.TransformTitle;
import wg.application.config.SpringIOCTest;
import wg.application.entity.BankFlow;
import wg.application.entity.LiuShui;
import wg.application.entity.Result;
import wg.application.enumeration.Title;
import wg.application.exception.WgException;
import wg.application.service.AspectService;
import wg.application.service.LiuShuiInterface;
import wg.application.service.MovieInterface;
import wg.application.service.TestInterface;
import wg.application.util.IPUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
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
@Slf4j
public class Test {

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

        return arrayList;
    }


    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {

        System.out.println(result);

        result = test2();
        String name = result.getName();


        result = new Result();
        System.out.println(result);

        System.out.println(name);

        return name;
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
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

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
            }
            i++;
            hashMap.put(i, i);
        }

        return hashMap;

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
                throw new WgException("sha cha");
            }
        } catch (WgException we) {

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
            log.error(e.getMessage(), e);
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

    @RequestMapping(value = "/ipTest")
    @ResponseBody
    public String ipTest(HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);

        return ipAddr;

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

    @Autowired
    MovieInterface movieInterface;

    @RequestMapping(value = "/testMovieNeo4j")
    @ResponseBody
    private String testMovieNeo4j(HttpServletRequest request, HttpServletResponse response) {

        movieInterface.testNeo4j(request, response);

        return "123";
    }


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
    @RequestMapping(value = "/encode")
    @ResponseBody
    private void encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        String encode2 = encoder.encode("111");

        System.out.println("encode -> " + encode);
        System.out.println(encode2);


        boolean matches = encoder.matches("111", "$2a$10$qc90QdnLWW0QHSUGvD95fuXh4.1VDqehenP4xTWtMVbhaymADEhhe");
        boolean matches2 = encoder.matches(password, "$2a$10$36OcV5ass16uhYBg0MWkiOZRNjIkAR0/APho.m9UoE4sZhUngsR8K");

        System.out.println(matches);
        System.out.println("matches2  " + matches2);

        if (matches2) {
            System.out.println("---------");
        }


        /************ matches() 源码分析 -> 开始 ************/

        String a = "$2a$10$ib.SiGEAJPbl.11WhriGfuFYxwZzp.IlN0U3ttYXOboirSuOqChzy";
        String b = "$2a$10$qc90QdnLWW0QHSUGvD95fuXh4.1VDqehenP4xTWtMVbhaymADEhhe";

        try {
            byte[] a_bytes = a.getBytes("utf-8");
            byte[] b_bytes = b.getBytes("utf-8");

            if (a_bytes.length != b_bytes.length) {
                System.out.println(false);
            }

            for (int i = 0; i < a_bytes.length; i++) {
                System.out.println("a_bytes[" + i + "]   -> " + a_bytes[i]);

            }
            for (int i = 0; i < b.length(); i++) {

                System.out.println("b_bytes[" + i + "]   -> " + b_bytes[i]);
            }

            if (a_bytes == b_bytes) {
                System.out.println("a_bytes == b_bytes  " + true);

            }

            int result = 0;
            // time-constant comparison
            for (int i = 0; i < a_bytes.length; i++) {
                result |= a_bytes[i] ^ b_bytes[i];
            }

            System.out.println("result  -> " + result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /************ matches() 源码分析 -> 结束 ************/


    }


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
        int l = Integer.parseInt("10", 3);
        System.out.println("三进制数 l 转成 10进制 -> " + l);  //
        int p = Integer.parseInt("1010", 2);
        System.out.println("三进制数 l 转成 10进制 -> " + p);  //

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
     * @author: wg
     * @time: 2020/7/7 15:27
     ****************************************************************/
    @RequestMapping(value = "sanYuan")
    @ResponseBody
    public void sanYuan() {
        boolean b = 1 > 2 || 1 > 0 ? false : true;

        System.out.println(b); // false

    }

    /****************************************************************
     * streamTest
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
    public LiuShui getAllLiuShui() {

        LiuShui one = liuShuiInterface.getOne();

        //System.out.println(one);

        String s = JSON.toJSONString(one);
        System.out.println(s);

        return one;
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

}
