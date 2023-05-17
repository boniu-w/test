package wg.application.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wg.application.entity.FileMy;
import wg.application.entity.FileMyExample;
import wg.application.mapper.FileMyMapper;

import javax.annotation.Resource;
import java.util.List;

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

        List<FileMy> list = fileMyMapper.selectByExample(example);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(FileMy fileMy) {
        FileMyExample example = new FileMyExample();
        FileMyExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(fileMy.getId());
        int i = fileMyMapper.updateByExampleSelective(fileMy, example);
        return i;
    }
}
