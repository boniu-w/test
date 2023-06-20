package wg.application.service.impl;

// import org.mybatis.dynamic.sql.BasicColumn;
// import org.mybatis.dynamic.sql.SqlBuilder;
// import org.mybatis.dynamic.sql.render.RenderingStrategies;
// import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
// import org.mybatis.dynamic.sql.select.SelectModel;
// import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wg.application.config.MyIdGenerator;
import wg.application.entity.FileMy;
import wg.application.entity.FileMyExample;
// import wg.application.mapper.FileMyDynamicSqlSupport;
import wg.application.mapper.FileMyMapper;
import wg.application.util.FileUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileMyServiceImpl {

    @Resource
    FileMyMapper fileMyMapper;

    @Transactional(rollbackFor = Exception.class)
    public int save(FileMy fileMy) {
        int insert = fileMyMapper.insertSelective(fileMy);
        return insert;
    }

    public List<FileMy> getAll() {
        FileMyExample example = new FileMyExample();
        FileMyExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagIsNotNull();

        List<FileMy> list = fileMyMapper.selectByExample(example);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(FileMy fileMy) {
        FileMyExample example = new FileMyExample();
        FileMyExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(fileMy.getId());
        criteria.andDelFlagIsNotNull();
        int i = fileMyMapper.updateByExampleSelective(fileMy, example);
        return i;
    }

    /**
     * 真删
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        FileMyExample example = new FileMyExample();
        FileMyExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andDelFlagIsNotNull();

        return fileMyMapper.deleteByExample(example);
    }

    /**
     * 获取重复的
     */
    public Map<String, List<FileMy>> getRepetitive() {
        List<FileMy> all = getAll();
        Map<String, List<FileMy>> hexMap = all.stream()
                .filter(e -> e.getSha256() != null)
                .collect(Collectors.groupingBy(FileMy::getSha256));

        HashMap<String, List<FileMy>> repetitiveMap = new HashMap<>();
        for (Map.Entry<String, List<FileMy>> hexEntry : hexMap.entrySet()) {
            List<FileMy> value = hexEntry.getValue();
            if (value.size() > 1) {
                repetitiveMap.put(hexEntry.getKey(), value);
            }
        }
        return repetitiveMap;
    }

    /**
     * 删除重复的
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Integer> deleteRepetitive() {
        int nasDeleted = 0;
        int datasourceDeleted = 0;
        int i = 0;
        Map<String, List<FileMy>> repetitive = getRepetitive();
        for (Map.Entry<String, List<FileMy>> entry : repetitive.entrySet()) {
            List<FileMy> value = entry.getValue();
            for (FileMy fileMy : value) {
                if (fileMy.getFileName().contains("(1)")) {
                    // 删除 数据库
                    FileMyExample example = new FileMyExample();
                    FileMyExample.Criteria criteria = example.createCriteria();
                    criteria.andIdEqualTo(fileMy.getId());
                    fileMy.setDelFlag(null);
                    fileMy.setUpdateTime(LocalDateTime.now());
                    datasourceDeleted += fileMyMapper.updateByExample(fileMy, example);

                    // 删除 数据库 成功后, 删除nas
                    if (datasourceDeleted == ++i) {
                        System.out.println();
                        System.out.println(datasourceDeleted);
                        System.out.println(i);
                        System.out.println();
                        String absolutePath = fileMy.getAbsolutePath();
                        File file = new File(absolutePath);
                        boolean delete = file.delete();
                        if (delete) nasDeleted++;
                    }
                }
            }
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put("nasDeleted", nasDeleted);
        map.put("datasourceDeleted", datasourceDeleted);
        return map;
    }

    /**
     * fileMy  排序
     */
    public void listOrder() {
        // List<FileMy> all = getAll();
        // List<FileMy> sortdList = all.stream()
        //         .sorted(Comparator.comparing(FileMy::getUpdateTime, (o1, o2) -> o2.compareTo(o1))) // 从大到小排序
        //         .collect(Collectors.toList());
        // sortdList.forEach(System.out::println);

        // FileMyExample example = new FileMyExample();
        // example.setOrderByClause("update_time desc");
        // example.createCriteria().andDelFlagIsNotNull();
        // List<FileMy> fileMyList = fileMyMapper.selectByExample(example);
        // fileMyList.forEach(System.out::println);

        // 插件 mybatis generator 1.4.2
        // SelectStatementProvider provider = SqlBuilder.select(FileMyDynamicSqlSupport.id, FileMyDynamicSqlSupport.fileName)
        //         .from(FileMyDynamicSqlSupport.fileMy)
        //         .build()
        //         .render(RenderingStrategies.MYBATIS3);
        // List<FileMy> fileMyList = fileMyMapper.selectMany(provider);
        //
        // SelectStatementProvider selectStatementProvider = SqlBuilder.select(BasicColumn.columnList())
        //         .from(FileMyDynamicSqlSupport.fileMy)
        //         .build()
        //         .render(RenderingStrategies.MYBATIS3);
        // List<FileMy> list = fileMyMapper.selectMany(selectStatementProvider);

    }

    /**********************************************************
     * @author: 公子求雨
     * @description: 从nas处获取文件list
     * @params:
     * @return:
     * @date: 22:39 2023/6/20
     **********************************************************/
    public List<File> getFromNas(String nasAddr) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        List<File> allFile = FileUtil.getAllFile(nasAddr, files);
        return allFile;
    }

    public Map<String, List<File>> groupNasFile(List<File> list) {
        Map<String, List<File>> map = new HashMap<>();
        ArrayList<File> length5 = new ArrayList<>();
        ArrayList<File> length4 = new ArrayList<>();
        for (File file : list) {
            String[] split = file.getPath().split("\\\\");
            if (split.length == 5) {
                length5.add(file);
            }
            if (split.length == 4) {
                length4.add(file);
            }
        }

        map.put("length5", length5);
        map.put("length4", length4);

        return map;
    }

    public int saveBatch(List<File> list) throws Exception {
        // 保存到数据库
        int insert = 0;
        for (File file : list) {
            FileMy fileMy = new FileMy();
            fileMy.setId(MyIdGenerator.idWorker1.nextId());
            fileMy.setFileName(file.getName());
            fileMy.setLength(file.length());
            fileMy.setAbsolutePath(file.getAbsolutePath());
            // fileMy.setSha256(FileUtil.getSha256(file));
            fileMy.setSuffix(file.getName().substring(file.getName().lastIndexOf(".") + 1));
            fileMy.setCreateTime(LocalDateTime.now());
            fileMy.setUpdateTime(LocalDateTime.now());

            insert += save(fileMy);
        }
        return insert;
    }

    /**********************************************************
     * @author: 公子求雨
     * @description: 过滤掉不想要的文件
     * @params: files: 所有文件
     * @params: outTypes: 不想要的文件类型
     * @return:
     * @date: 23:37 2023/6/20
     **********************************************************/
    public List<File> filterFile(List<File> files, String[] outTypes) {
        ArrayList<File> outFiles = new ArrayList<>();
        Set<File> wantedFiles = new HashSet<>();
        for (File file : files) {
            if (hasExcludedExtension(file, Arrays.asList(outTypes))) {
                outFiles.add(file);
            } else {
                wantedFiles.add(file);
            }
        }

        System.out.println("files.size() = " + files.size());
        System.out.println("outFiles.size() = " + outFiles.size());
        System.out.println("wantedFiles.size() = " + wantedFiles.size());
        return new ArrayList<>(wantedFiles);
    }

    public static boolean hasExcludedExtension(File file, List<String> excludedExtensions) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return excludedExtensions.contains(fileExtension.toLowerCase());
    }

    // public static void main(String[] args) throws IOException {
    //     String nasAddr = "\\\\nas-wg\\wg\\影";
    //     List<File> files = getFromNas(nasAddr);
    //
    //     File file = files.get(0);
    //     System.out.println(file.getName());
    //     System.out.println(file.getPath());
    //     System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
    //     System.out.println(file.getTotalSpace());
    //     System.out.println(file.getFreeSpace());
    //     System.out.println(file.getUsableSpace());
    //     System.out.println(file.isDirectory());
    //     System.out.println(file.isFile());
    //     System.out.println(file.isHidden());
    //
    //     File parentFile = file.getParentFile();
    //     System.out.println(parentFile.getName());
    //
    //     Map<String, List<File>> map = groupNasFile(files);
    //     List<File> length5FileList = map.get("length5");
    //     List<File> length4FileList = map.get("length4");
    // }
}
