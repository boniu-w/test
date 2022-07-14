package wg.application.mytool;

import org.springframework.web.bind.annotation.*;
import wg.application.util.LongUtil;
import wg.application.util.StringUtil;

import java.time.LocalDateTime;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:51 2022/4/25
 * @updateTime: 10:51 2022/4/25
 ************************************************************************/
@RestController
@RequestMapping(value = "/mytoolscontroller")
public class MyToolsController {

    /**
     * 转 unicode
     */
    @GetMapping(value = "/to_unicode")
    public String toUnicode(@RequestParam Map<String, Object> params) {
        String text = StringUtil.toUnicode(params.get("beforeText").toString());
        return text;
    }

    /**
     * 把毫秒数转成 日期
     */
    @GetMapping(value = "/to_datetime")
    public LocalDateTime toDatetime(@RequestParam String m) {

        return LongUtil.toDatetime(Long.parseLong(m));
    }

    /**
     * 进制转换
     */
}
