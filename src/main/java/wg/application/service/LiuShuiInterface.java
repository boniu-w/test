package wg.application.service;

import wg.application.entity.LiuShui;

import java.util.List;

/*************************************************************
 * @Package wg.application.service
 * @author wg
 * @date 2020/6/30 15:37
 * @version
 * @Copyright
 *************************************************************/
public interface LiuShuiInterface  {

    public LiuShui getOne();
    List<LiuShui> getByJiaoYiJinErBetween(double min, double max);
}
