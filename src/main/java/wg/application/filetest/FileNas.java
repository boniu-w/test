package wg.application.filetest;

import cn.hutool.http.server.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import wg.application.config.MyIdGenerator;
import wg.application.config.StartupMessage;
import wg.application.entity.FileMy;
import wg.application.exception.WgException;
import wg.application.service.impl.FileMyServiceImpl;
import wg.application.util.CollectionUtil;
import wg.application.util.FileUtil;
import wg.application.util.StringUtil;
import wg.application.vo.Result;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component(value = "fileTestMy")
@RequestMapping(value = "/file_nas")
@ResponseBody
public class FileNas {
    private static final Logger logger = LoggerFactory.getLogger(FileNas.class);

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

    /**********************************************************
     * @author: 公子求雨
     * @description: 新增
     * @params:
     * @return:
     * @date: 10:59 2023/5/28
     **********************************************************/
    @GetMapping(value = "/save_file_nas")
    public Result<Object> saveFileTest(String path) throws Exception {
        Result<Object> result = new Result<>();
        try {
            List<FileMy> fileMyList = fileMyService.getAll();

            ArrayList<File> files = new ArrayList<>();
            List<File> allFile = FileUtil.getAllFile(path, files);

            Map<String, List<File>> fileMap = allFile.stream()
                    .collect(Collectors.groupingBy(e -> {
                        if (e.getAbsolutePath().split("\\\\").length > 5) {
                            return e.getAbsolutePath().split("\\\\")[5];
                        }
                        return e.getName();
                    }));

            // 保存到数据库
            int insert = 0;
            for (Map.Entry<String, List<File>> entry : fileMap.entrySet()) {
                List<File> fileList = entry.getValue();
                for (File file : fileList) {
                    FileMy fileMy = new FileMy();
                    fileMy.setId(MyIdGenerator.idWorker1.nextId());
                    fileMy.setFileName(file.getName());
                    fileMy.setLength(file.length());
                    fileMy.setAbsolutePath(file.getAbsolutePath());
                    fileMy.setSha256(FileUtil.getSha256(file));
                    fileMy.setSuffix(file.getName().substring(file.getName().lastIndexOf(".") + 1));
                    fileMy.setCreateTime(LocalDateTime.now());
                    fileMy.setUpdateTime(LocalDateTime.now());

                    insert += fileMyService.save(fileMy);
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

    @GetMapping(value = "/save")
    public Result<Object> save(String nasAddr) {
        // String nasAddr = "\\\\nas-wg\\wg\\影";
        Result<Object> result = new Result<>();
        try {
            String[] types = {"jpg", "txt", "xlsx", "png"};
            List<FileMy> fileMyList = fileMyService.getAll();

            List<File> files = fileMyService.getFromNas(nasAddr);
            List<File> fileList = fileMyService.filterFile(files, types);

            // Map<String, List<File>> map = fileMyService.groupNasFile(fileList);
            // List<File> length5FileList = map.get("length5");
            // List<File> length4FileList = map.get("length4");

            // int i = fileMyService.saveBatch(length5FileList);
            // i += fileMyService.saveBatch(length4FileList);

            int i = fileMyService.saveBatch(fileList);
            result.setMessage("have insert: " + i);
            return result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return result.error();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/savenewfiles")
    public void saveNewFiles(@RequestBody Map<String, Object> params) {
        String nasAddr = (String) params.get("nasAddr");
        List<String> types = (List<String>) params.get("types");

        List<File> files = null;
        List<FileMy> all = fileMyService.getAll();
        try {
            files = fileMyService.getFromNas(nasAddr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Collection<File>> map = fileMyService._filterFile(files, types.toArray(new String[types.size()]));
        Collection<File> outFiles = map.get("outFiles");
        Collection<File> wantedFiles = map.get("wantedFiles");


        Map<String, FileMy> myMap = all.stream()
                .collect(Collectors.toMap(item -> item.getFileName() + item.getLength(), m -> m));
        Map<String, File> fileMap = wantedFiles.stream()
                .collect(Collectors.toMap(item -> item.getName() + item.length(), m -> m));

        HashMap<String, File> addMap = new HashMap<>();
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {

            if (myMap.get(entry.getKey()) == null) {
                addMap.put(entry.getKey(), entry.getValue());
            }
        }

        ArrayList<File> addFileList = new ArrayList<>();
        addMap.forEach((k, v) -> addFileList.add(v));
        List<String> addFilesName = addFileList.stream().map(File::getName).collect(Collectors.toList());
        try {
            int saveBatch = fileMyService.saveBatch(addFileList);
            logger.info("已新增{}个文件, 它们分别是: {}", saveBatch, addFilesName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 把不想要的文件从nas删除
        int i = 0;
        for (File file : outFiles) {
            i += fileMyService.deleteNas(file);
        }
        logger.info("已从nas删除{}个类型为{}的文件", i, types);
    }

    @GetMapping(value = "/deletenas")
    public void deleteNas(String key) {
        int i = fileMyService.deleteNas(new File(key));
        if (i == 1) {
            logger.info("已从nas删除 `{}` 这个文件", key);
        }
    }
}
