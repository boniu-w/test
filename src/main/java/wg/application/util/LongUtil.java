package wg.application.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:18 2022/6/6
 * @updateTime: 15:18 2022/6/6
 ************************************************************************/
public class LongUtil {

    /**
     * 毫秒数 转 日期
     */
    public static LocalDateTime toDatetime(Long m) {
        return LocalDateTime.ofEpochSecond(m / 1000, 0, ZoneOffset.ofHours(8));
    }

}
