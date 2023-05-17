package wg.application.filetest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.entity.FileMy;
import wg.application.exception.WgException;
import wg.application.service.impl.FileMyServiceImpl;
import wg.application.util.FileUtil;
import wg.application.vo.Result;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "fileTestMy")
@RequestMapping(value = "/file_test")
@ResponseBody
public class FileTest {

    @Resource
    FileMyServiceImpl fileMyService;

    public static void main(String[] args) {
        try {
            fileTest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void fileTest() throws Exception {
        String path = "\\\\nas-wg\\video\\剧";
        ArrayList<File> files = new ArrayList<>();
        List<File> allFile = FileUtil.getAllFile(path, files);

        Map<String, List<File>> fileMap = allFile.stream()
                .collect(Collectors.groupingBy(e -> {
                    return e.getAbsolutePath().split("\\\\")[5];
                }));

        for (Map.Entry<String, List<File>> entry : fileMap.entrySet()) {
            String key = entry.getKey();
            List<File> fileList = entry.getValue();
            for (File file : fileList) {
                // String sha256Hex = FileUtil.getSha256(file); // 大文件 消耗时间很长
                // System.out.println(sha256Hex);
                // String md5 = FileUtil.getMD5(file); // 大文件 outofmemory
                // System.out.println(md5);
                System.out.println(file);
            }
        }
    }

    @RequestMapping(value = "/save_file_test")
    public Result<Object> saveFileTest() throws Exception {
        Result<Object> result = new Result<>();
        try {
            List<FileMy> fileMyList = fileMyService.getAll();

            String path = "\\\\nas-wg\\video\\剧";
            ArrayList<File> files = new ArrayList<>();
            List<File> allFile = FileUtil.getAllFile(path, files);

            Map<String, List<File>> fileMap = allFile.stream()
                    .collect(Collectors.groupingBy(e -> {
                        return e.getAbsolutePath().split("\\\\")[5];
                    }));

            for (FileMy my : fileMyList) {
                for (File file : allFile) {
                    if (file.getName().equals(my.getFileName())) {
                        my.setSuffix(file.getName().substring(file.getName().lastIndexOf(".") + 1));
                        fileMyService.update(my);
                    }
                }
            }

            int insert = 0;
            for (Map.Entry<String, List<File>> entry : fileMap.entrySet()) {
                String key = entry.getKey();
                List<File> fileList = entry.getValue();
                for (File file : fileList) {
                    // String sha256Hex = FileUtil.getSha256(file); // 大文件 消耗时间很长
                    // System.out.println(sha256Hex);
                    // String md5 = FileUtil.getMD5(file); // 大文件 outofmemory
                    // System.out.println(md5);
                    System.out.println(file);

                    // FileMy fileMy = new FileMy();
                    // fileMy.setId(MyIdGenerator.idWorker1.nextId());
                    // fileMy.setFileName(file.getName());
                    // fileMy.setAbsolutepath(file.getAbsolutePath());
                    // fileMy.setCreateTime(LocalDateTime.now());
                    // fileMy.setUpdateTime(LocalDateTime.now());
                    // fileMy.setSuffix(file.getName().substring(file.getName().lastIndexOf(".")+1));
                    //
                    // insert += fileMyService.save(fileMy);

                }
            }
            result.setData(insert);
            result.setSuccess(true);
            return result;
        } catch (WgException e) {
            return result.error();
        }
    }

    @RequestMapping(value = "/update_file_test")
    public Result<Object> updateFileTest() throws Exception {
        Result<Object> result = new Result<>();
        int i = 0;
        try {
            List<FileMy> fileMyList = fileMyService.getAll();
            Map<String, List<FileMy>> datasourceFileGroup = fileMyList.stream()
                    .collect(Collectors.groupingBy(e -> {
                        return e.getAbsolutePath().split("\\\\")[5];
                    }));

            String path = "\\\\nas-wg\\video\\剧";
            ArrayList<File> files = new ArrayList<>();
            List<File> allFile = FileUtil.getAllFile(path, files);

            Map<String, List<File>> fileMap = allFile.stream()
                    .collect(Collectors.groupingBy(e -> {
                        return e.getAbsolutePath().split("\\\\")[5];
                    }));

            for (Map.Entry<String, List<FileMy>> entry : datasourceFileGroup.entrySet()) {
                List<FileMy> value = entry.getValue();
                List<File> fileList = fileMap.get(entry.getKey());
                for (File file : fileList) {
                    for (FileMy my : value) {
                        if (file.getName().equals(my.getFileName())) {
                            my.setSuffix(file.getName().substring(file.getName().lastIndexOf(".") + 1));
                            my.setSha256(FileUtil.getSha256(file));
                            my.setUpdateTime(LocalDateTime.now());
                            i += fileMyService.update(my);
                        }
                    }
                }
            }

            result.setData(i);
            result.setSuccess(true);
            return result;
        } catch (WgException e) {
            return result.error();
        }
    }

}
