package wg.application.mytool;

import org.springframework.web.bind.annotation.*;
import wg.application.util.StringUtil;

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

    @GetMapping(value = "/to_unicode")
    public String toUnicode(@RequestParam Map<String, Object> params) {
        String text = StringUtil.toUnicode(params.get("beforeText").toString());
        return text;
    }
}
