package wg.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.entity.Song;
import wg.application.service.impl.MyMusicHandlerServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 11:05 2022/9/8
 * @updateTime: 11:05 2022/9/8
 ************************************************************************/
@RestController
@RequestMapping(value = "/my_music")
public class MyMusicController {

    @Resource
    MyMusicHandlerServiceImpl myMusicHandlerServiceImpl;

    @GetMapping(value = "/handler")
    public List<Song> handler() {
        try {
            myMusicHandlerServiceImpl.execute();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
