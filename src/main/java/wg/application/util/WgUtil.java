package wg.application.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WgUtil {
    private static final Logger logger = LoggerFactory.getLogger(WgUtil.class);

    /************************************************************************
     * @description: 把 Object 转成 target 类型
     * @author: wg
     * @date:  15:35  2021/11/8
     * @params:
     * @return:
     ************************************************************************/
    public static <T> T sourceToTarget(Object source, Class<T> target){
        if(source == null){
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetObject;
    }

    /************************************************************************
     * @description: 把集合的泛型 转成 target 类型
     * @author: wg
     * @date:  15:33  2021/11/8
     * @params:
     * @return:
     ************************************************************************/
    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }

        List<T> targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetList;
    }
}
