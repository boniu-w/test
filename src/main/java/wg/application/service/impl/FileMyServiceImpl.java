package wg.application.service.impl;

// import org.mybatis.dynamic.sql.BasicColumn;
// import org.mybatis.dynamic.sql.SqlBuilder;
// import org.mybatis.dynamic.sql.render.RenderingStrategies;
// import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
// import org.mybatis.dynamic.sql.select.SelectModel;
// import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wg.application.entity.FileMy;
import wg.application.entity.FileMyExample;
// import wg.application.mapper.FileMyDynamicSqlSupport;
import wg.application.mapper.FileMyMapper;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

}
