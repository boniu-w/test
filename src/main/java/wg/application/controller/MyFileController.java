package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.service.impl.MyFileHandlerServiceImpl;
import wg.application.util.FileUtil;

import javax.annotation.Resource;
import java.io.IOException;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 17:30 2022/9/8
 * @updateTime: 17:30 2022/9/8
 ************************************************************************/
@RestController
@RequestMapping(value = "/my_file")
public class MyFileController {

    @Resource
    MyFileHandlerServiceImpl myFileHandlerService;

    @GetMapping(value = "/handler_my_file")
    public void handlerMyFile(){
        try {
            final String path = "H:\\test-copy";
            final String tempPath = "H:\\temp1";

            FileUtil.copyToTempDir(path, tempPath);
            // myFileHandlerService.distinctFile(path, tempPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/test_delete_file")
    public void testDeleteFileInput(){
        try {
            myFileHandlerService.testDeleteInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
