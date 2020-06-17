package wg.application.controller;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.annotation.Log;
import wg.application.entity.BankFlow;
import wg.application.entity.Result;
import wg.application.exception.WgException;
import wg.application.service.AspectService;
import wg.application.util.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


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
                fileName = new String(fileName.getBytes(), "ISO8859-1");
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

    @RequestMapping(value = "/exportUtilTest")
    public void exportUtilTest(HttpServletResponse response) {
        List<BankFlow> list = new ArrayList<>();
        String sheetName = "123";

        for (int i = 0; i < 10; i++) {
            BankFlow bankFlow = new BankFlow();
            bankFlow.setTick("conditionExcel" + i);
            bankFlow.setTransactionAmount(i + 100);
            list.add(bankFlow);
        }


        Class<BankFlow> bankFlowClass = BankFlow.class;
        ExcelUtil excelUtil = new ExcelUtil(bankFlowClass);
        excelUtil.exportExcel(list, sheetName, response);

    }


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
    public void kexuejishufa() {
        double d = 6.22848E+18;
        BigDecimal bigDecimal = new BigDecimal(d);
        System.out.println(bigDecimal);
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


        if (".dat".equals(s.substring(s.lastIndexOf(".")))) {
            System.out.println("nnnnnnnnnnbbbbbbb");
        }

    }

    @RequestMapping(value = "/exceptionTest")
    @ResponseBody
    public void exceptionTest() {

        if (0 == 0) {
            throw new WgException("sha cha");
        }

        System.out.println("--------");

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
}
