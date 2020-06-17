package wg.application.controller;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.List;

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
        System.out.println("contextPath -> "+contextPath);

        ServletContext servletContext = request.getSession().getServletContext();

        String realPath = servletContext.getRealPath("/");
        System.out.println("realPath -> "+realPath);

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(servletContext);

        DiskFileItemFactory fileItemFactory = commonsMultipartResolver.getFileItemFactory();
        File repository = fileItemFactory.getRepository();
        String absolutePath = repository.getAbsolutePath();

        System.out.println("absolutePath -> "+absolutePath);

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

                            System.out.println(originalFilename);
                            System.out.println(fileName);
                        }
                    }
                    return "shi";
                }
            }

        }


        return "fou";
    }


}
