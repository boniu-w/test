package wg.application.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wg.application.config.MyIdGenerator;
import wg.application.entity.Song;
import wg.application.entity.SongExample;
import wg.application.mapper.SongMapper;
import wg.application.util.FileUtil;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 10:40 2022/9/8
 * @updateTime: 10:40 2022/9/8
 ************************************************************************/
@Service
public class MyMusicHandlerServiceImpl {

    @Resource
    SongMapper songMapper;

    /************************************************************************
     * @author: wg
     * @description: 从多个文件夹获取歌曲, 保存到数据库
     * @params:
     * @return:
     * @createTime: 17:15  2022/9/8
     * @updateTime: 17:15  2022/9/8
     ************************************************************************/
    public void execute() throws Exception {
        String path = "H:\\copy-music-1";
        String path1 = "H:\\music";
        String path2 = "/home/wg/Music";

        List<String> pathList = new ArrayList<>();
        pathList.add(path);
        pathList.add(path1);
        pathList.add(path2);

        List<Song> handler = handler(pathList);
        List<Song> databaseSongs = songMapper.selectByExample(new SongExample());
        insertOrUpdate(handler, databaseSongs);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdate(List<Song> songList, List<Song> databaseSongs) {
        if (songList.size() > 0) {
            // 相同 部分, 用 databaseSongs 去比较
            // List<Song> listSame = databaseSongs.stream()
            //         .filter(databaseSong ->
            //                 songList.stream()
            //                         .map(Song::getHexHash)
            //                         .collect(Collectors.toList())
            //                         .contains(databaseSong.getHexHash())
            //         )
            //         .collect(Collectors.toList());

            List<Song> listSame = databaseSongs.stream()
                    .filter(databaseSong ->
                            songList.stream()
                                    .map(Song::getHexHash)
                                    .collect(Collectors.toList())
                                    .contains(databaseSong.getHexHash())
                                    &&
                                    songList.stream()
                                            .map(Song::getLocation)
                                            .collect(Collectors.toList())
                                            .contains(databaseSong.getLocation())
                    )
                    .collect(Collectors.toList());

            // 不同部分 用 songList 去比较
            // List<Song> listDiff = songList.stream()
            //         .filter(song ->
            //                 !databaseSongs.stream()
            //                         .map(Song::getHexHash)
            //                         .collect(Collectors.toList())
            //                         .contains(song.getHexHash())
            //         )
            //         .collect(Collectors.toList());

            List<Song> listDiff = songList.stream()
                    .filter(song ->
                            !databaseSongs.stream()
                                    .map(Song::getHexHash)
                                    .collect(Collectors.toList())
                                    .contains(song.getHexHash())
                                    ||
                                    !databaseSongs.stream()
                                            .map(Song::getLocation)
                                            .collect(Collectors.toList())
                                            .contains(song.getLocation())
                    )
                    .collect(Collectors.toList());

            int insertCount = 0;
            MyIdGenerator myIdGenerator = new MyIdGenerator();
            if (listDiff.size() > 0) {
                for (Song song : listDiff) {
                    // for (Song databaseSong : databaseSongs) {
                    //     if (song.getHexHash().equals(databaseSong.getHexHash())) {
                    //         databaseSong.setLocation(song.getLocation());
                    //     }
                    // }

                    song.setId(myIdGenerator.generate());
                    song.setCreateDatetime(LocalDateTime.now());
                    insertCount += songMapper.insert(song);
                }
            }

            // 相同部分可以不管
            int updateCount = 0;
            // if (listSame.size() > 0) {
            //     for (Song song : listSame) {
            //         song.setUpdateDatetime(LocalDateTime.now());
            //         updateCount += songMapper.updateByPrimaryKeySelective(song);
            //     }
            // }

            System.out.println("insertCount: " + insertCount);
            System.out.println("updateCount: " + updateCount);
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 获取某个文件夹下的所有歌曲, 并去重
     * @params:
     * @return:
     * @createTime: 16:34  2022/9/8
     * @updateTime: 16:34  2022/9/8
     ************************************************************************/
    public List<Song> handler(String path) throws Exception {
        List<Song> songList = new ArrayList<>();
        songList = handlerDirectory(path, songList);

        // 对象的某个属性 去重
        songList = songList.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(
                                        () -> new TreeSet<>(
                                                Comparator.comparing(t -> t.getHexHash())
                                        )
                                ), ArrayList::new)
                );
        return songList;
    }

    public List<Song> handler(List<String> pathList) throws Exception {
        List<Song> songList = new ArrayList<>();
        for (String path : pathList) {
            handlerDirectory(path, songList);
        }

        // 对象的某个属性 去重
        songList = songList.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(
                                        () -> new TreeSet<>(
                                                Comparator.comparing(t -> t.getHexHash())
                                        )
                                ), ArrayList::new)
                );
        return songList;
    }

    /************************************************************************
     * @author: wg
     * @description: 获取文件夹下的所有文件, 组成 list
     * @params:
     * @return:
     * @createTime: 16:34  2022/9/8
     * @updateTime: 16:34  2022/9/8
     ************************************************************************/
    public List<Song> handlerDirectory(String path, List<Song> songList) throws Exception {
        File file = new File(path);
        MyIdGenerator myIdGenerator = null;
        Song song = null;
        String name = "";

        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (null != files && files.length > 0) {
                    for (File file1 : files) {
                        BasicFileAttributes basicFileAttributes = Files.readAttributes(file1.toPath(), BasicFileAttributes.class);
                        if (basicFileAttributes.isRegularFile()) {
                            song = new Song();
                            song.setLocation(file.getPath());
                            song.setSize(basicFileAttributes.size());
                            song.setDelFlag(0);

                            name = file1.getName();

                            // 后缀
                            if (name.indexOf(".") > 0) {
                                song.setSuffix(name.substring(name.lastIndexOf(".") + 1));
                            }

                            if (name.contains("-") && name.indexOf(".") > 0) {
                                // 歌名
                                song.setName(name.substring(name.indexOf("-"), name.indexOf(".")).trim());
                                // 歌手名
                                song.setSinger(name.substring(0, name.indexOf("-")).trim());
                            } else {
                                song.setName(name.substring(0, name.lastIndexOf(".")));
                            }

                            if (null != song.getName() && song.getName().contains("-")) {
                                song.setName(song.getName().replace("-", "").trim());
                            }

                            song.setHexHash(FileUtil.getHexHash(file1));

                            songList.add(song);
                        } else if (basicFileAttributes.isDirectory()) {
                            handlerDirectory(file1.getPath(), songList);
                        }
                    }
                }
            }
        }
        return songList;
    }

}
