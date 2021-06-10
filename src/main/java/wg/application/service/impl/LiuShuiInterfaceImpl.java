package wg.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wg.application.entity.LiuShui;
import wg.application.mapper.LiuShuiRepository;
import wg.application.service.LiuShuiInterface;

import java.util.List;

/*************************************************************
 * @Package wg.application.service.impl
 * @author wg
 * @date 2020/7/15 16:43
 * @version
 * @Copyright
 *************************************************************/
@Service
public class LiuShuiInterfaceImpl implements LiuShuiInterface {

    @Autowired(required = false)
    LiuShuiRepository liuShuiRepository;


    public LiuShui getOne(){
        //LiuShui one = liuShuiRepository.getOne(3l);
        LiuShui one = liuShuiRepository.findById(3l).get();

        return one;
    }

    public List<LiuShui> getByJiaoYiJinErBetween(double min, double max){

        return liuShuiRepository.getByJiaoYiJinErBetween(min, max);
    }
}
