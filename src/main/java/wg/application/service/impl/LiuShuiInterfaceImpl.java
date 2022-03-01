package wg.application.service.impl;

import org.springframework.stereotype.Service;
import wg.application.entity.LiuShui;
import wg.application.service.LiuShuiInterface;

import java.util.List;

@Service
public class LiuShuiInterfaceImpl implements LiuShuiInterface {
    @Override
    public LiuShui getOne() {
        return null;
    }

    @Override
    public List<LiuShui> getByJiaoYiJinErBetween(double min, double max) {
        return null;
    }
}
