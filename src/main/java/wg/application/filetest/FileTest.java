package wg.application.filetest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wg.application.config.MyIdGenerator;
import wg.application.entity.FileMy;
import wg.application.exception.WgException;
import wg.application.service.impl.FileMyServiceImpl;
import wg.application.util.CollectionUtil;
import wg.application.util.FileUtil;
import wg.application.util.StringUtil;
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

    /************************************************************************
     * @author: wg
     * @description: "\" -> "&#92;"
     * 正则表达式中, \表示将下一字符标记为特殊字符。如\d表示数字字符匹配，等效于 [0-9]。
     * \\中的第一个\表示java的转义字符\由编译器解析，第二个\是正则表达式\由正则表达式引擎解析。
     * @params:
     * @return:
     * @createTime: 9:48  2023/5/17
     * @updateTime: 9:48  2023/5/17
     ************************************************************************/
    public static void fileTest() throws Exception {
        String path = "\\\\nas-wg\\video\\剧";
        ArrayList<File> files = new ArrayList<>();
        List<File> allFile = FileUtil.getAllFile(path, files);

        Map<String, List<File>> fileMap = allFile.stream()
                .collect(Collectors.groupingBy(e -> {
                    return e.getAbsolutePath().split("\\\\")[5];
                }));

        System.out.println(fileMap.size());
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

    @RequestMapping(value = "/save_or_update_nas_file")
    public Result<Object> saveOrUpdateFileTest() throws Exception {
        Result<Object> result = new Result<>();
        int update = 0;
        int insert = 0;
        try {
            List<FileMy> fileMyList = fileMyService.getAll();
            Map<String, List<FileMy>> datasourceFileGroup = fileMyList.stream()
                    .filter(e -> !StringUtil.isBlank(e.getAbsolutePath()))
                    .collect(Collectors.groupingBy(e -> {
                        if (e.getAbsolutePath().split("\\\\").length >= 6) {
                            return e.getAbsolutePath().split("\\\\")[5];
                        } else {
                            return e.getFileName();
                        }
                    }));

            String path = "\\\\nas-wg\\video\\剧";
            ArrayList<File> files = new ArrayList<>();
            List<File> allFile = FileUtil.getAllFile(path, files);
            System.out.println("allFile.size(): " + allFile.size());
            Map<String, List<File>> nasFileMap = allFile.stream()
                    .filter(e -> !StringUtil.isBlank(e.getAbsolutePath()))
                    .collect(Collectors.groupingBy(e -> {
                        if (e.getAbsolutePath().split("\\\\").length >= 6) {
                            return e.getAbsolutePath().split("\\\\")[5];
                        } else {
                            return e.getName();
                        }
                    }));

            for (Map.Entry<String, List<File>> entry : nasFileMap.entrySet()) {
                List<File> fileList = entry.getValue();
                if (CollectionUtil.isNotEmpty(fileList)) {
                    if (datasourceFileGroup.get(entry.getKey()) == null) {
                        // insert
                        for (File file : fileList) {
                            FileMy fileMy = new FileMy();
                            fileMy.setId(MyIdGenerator.idWorker1.nextId());
                            fileMy.setFileName(file.getName());
                            fileMy.setSuffix(file.getName().substring(file.getName().lastIndexOf(".") + 1));
                            fileMy.setAbsolutePath(file.getAbsolutePath());
                            fileMy.setCreateTime(LocalDateTime.now());
                            fileMy.setUpdateTime(LocalDateTime.now());
                            fileMy.setSha256(FileUtil.getSha256(file));

                            insert += fileMyService.save(fileMy);
                        }
                    } else {
                        // update
                        List<FileMy> mies = datasourceFileGroup.get(entry.getKey());
                        for (File file : fileList) {
                            for (FileMy my : mies) {
                                if (my.getFileName().equals(file.getName())) {
                                    // 更新 sha256
                                    if (StringUtil.isBlank(my.getSha256())) {
                                        String sha256 = FileUtil.getSha256(file);
                                        my.setSha256(sha256);
                                        my.setUpdateTime(LocalDateTime.now());
                                        update += fileMyService.update(my);
                                    }

                                    // sha256 不一样 的情况
                                    // else if (Objects.equals(my.getSha256(), sha256)) {
                                    //     my.setSha256(sha256);
                                    //     my.setUpdateTime(LocalDateTime.now());
                                    //     update += fileMyService.update(my);
                                    // }

                                    // 更新 length
                                    if (my.getLength() == null) {
                                        my.setLength(file.length());
                                        my.setUpdateTime(LocalDateTime.now());
                                        update += fileMyService.update(my);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            result.setData(update + insert);
            result.setSuccess(true);
            return result;
        } catch (WgException e) {
            return result.error();
        }
    }

    @GetMapping(value = "/getrepetitive")
    public Result<Object> getRepetitive() {
        Result<Object> result = new Result<>();
        try {
            Map<String, List<FileMy>> repetitive = fileMyService.getRepetitive();
            result.setData(repetitive);

            return result.success();
        } catch (Exception e) {
            return result.error();
        }
    }

    @DeleteMapping(value = "/deleterepetitive")
    public Result<Object> deleteRepetitive() {
        Result<Object> result = new Result<>();
        try {
            Map<String, Integer> map = fileMyService.deleteRepetitive();
            result.setData(map);

            return result.success();
        } catch (Exception e) {
            return result.error();
        }
    }

    @GetMapping(value = "/list")
    public Result<Object> list() {
        Result<Object> result = new Result<>();
        try {
            fileMyService.listOrder();

            return result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return result.error();
        }
    }
}
