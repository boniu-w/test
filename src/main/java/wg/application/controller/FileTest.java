package wg.application.controller;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import wg.application.entity.DutyEntity;
import wg.application.util.ExcelParams;
import wg.application.util.ExcelUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/********************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/6/16 10:09
 * @version
 * @Copyright
 ********************************************************/
@Controller
@RequestMapping(value = "/fileTest")
public class FileTest {


    @RequestMapping(value = "/getFileInfo")
    @ResponseBody
    public String getFileInfo(HttpServletRequest request) {

        String contextPath = request.getContextPath();
        System.out.println("contextPath -> " + contextPath);

        ServletContext servletContext = request.getSession().getServletContext();

        String realPath = servletContext.getRealPath("/");
        System.out.println("realPath -> " + realPath);

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(servletContext);

        DiskFileItemFactory fileItemFactory = commonsMultipartResolver.getFileItemFactory();
        File repository = fileItemFactory.getRepository();
        String absolutePath = repository.getAbsolutePath();

        System.out.println("absolutePath -> " + absolutePath);

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

            String pathInfo = multipartHttpServletRequest.getPathInfo();
            System.out.println(pathInfo);

            String pathTranslated = multipartHttpServletRequest.getPathTranslated();
            System.out.println(pathTranslated);

            Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
            if (commonsMultipartResolver.isMultipart(request)) {
                while (fileNames.hasNext()) {
                    List<MultipartFile> files = multipartHttpServletRequest.getFiles(fileNames.next());

                    if (files != null && files.size() > 0) {
                        for (MultipartFile file : files) {
                            String originalFilename = file.getOriginalFilename();
                            String fileName = file.getName();

                            // 获取文件名
                            System.out.println(originalFilename);

                            // 获取 form 表单 <input type=file name= > name 的值
                            System.out.println(fileName);

                            return "获取到上传的文件名: " + originalFilename;
                        }
                    }
                    return "shi";
                }
            }

        }


        return "fou";
    }

    /****************************************************************
     * 上传 导入
     * @author: wg
     * @time: 2020/10/27 19:11
     ****************************************************************/
    @RequestMapping(value = "/getUploadFiles")
    @ResponseBody
    public void getUploadFiles(HttpServletRequest request) {
        try {
            // 1. 获取上传的文件列表
            List<MultipartFile> uploadFiles = ExcelUtil.getUploadFiles(request);

            for (MultipartFile uploadFile : uploadFiles) {

                // 2. 先初始化 workbook 确定是那种excel(.xlsx | .xls)
                Workbook workbook = ExcelUtil.initWorkbook(uploadFile);

                // 3. 指定参数, 如 sheetIndex, titleIndex
                ExcelParams excelParams = new ExcelParams();
                excelParams.setSheetIndex(0);  // 指定在哪个sheet
                excelParams.setTitleIndex(1);  // 指定标题在哪一行
                excelParams.setContentIndex(2);  // 指定内容起始行

                // 4. 读取标题
                Class<?> aClass = Class.forName("wg.application.entity.DutyEntity");
                String[] strings = ExcelUtil.readExcelTitle(excelParams, (Class<T>) aClass);

                System.out.println(Arrays.toString(strings));

                // 5. 联合标题,读取内容 最后的map 标题 内容都有
                Map<Integer, Map<String, Object>> integerMapMap = ExcelUtil.readExcelContent(workbook, strings, excelParams);
                System.out.println(integerMapMap.size() + " --- " + integerMapMap);

                // 6. 写入数据库

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****************************************************************
     * 导出(下载)
     * @author: wg
     * @time: 2020/10/27 17:30
     ****************************************************************/
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) throws ClassNotFoundException {

        // 1. 设置 导出文件名
        ExcelParams excelParams = new ExcelParams();
        excelParams.setExportFileName("wg.xls");
        excelParams.setExportSheetName("wg-1");

        // 2. 导出
        Class<?> aClass = Class.forName("wg.application.entity.DutyEntity");
        ExcelUtil.export(null, response, (Class<T>) aClass, excelParams);
    }

}
