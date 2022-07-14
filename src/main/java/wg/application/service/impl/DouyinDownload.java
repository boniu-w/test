package wg.application.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.List;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 17:38 2022/4/29
 * @updateTime: 17:38 2022/4/29
 ************************************************************************/
public class DouyinDownload {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        try {
            // 这里填抖音分享链接
            String res = HttpUtil.get("https://v.douyin.com/FkfvwXc");
            System.out.println(res);
            List<String> split = StrUtil.split(res, "/video/");
            String itemId = StrUtil.subBefore(split.get(1), "/", false);
            String jsonInfoUrl = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
            String jsonInfo = HttpUtil.get(jsonInfoUrl + itemId);
            JSONObject obj = JSONUtil.parseObj(jsonInfo);
            String playwmUrl = obj.getJSONArray("item_list")
                    .getJSONObject(0)
                    .getJSONObject("video")
                    .getJSONObject("play_addr")
                    .getJSONArray("url_list")
                    .getStr(0);
            // 这个就是无水印视频url了
            String playUrl = playwmUrl.replace("/playwm/", "/play/");
            System.out.println(playUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
