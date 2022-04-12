package wg.application.util;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:50 2022/3/14
 * @updateTime: 16:50 2022/3/14
 ************************************************************************/
public class FileUtil {
    /************************************************************************
     * @author: wg
     * @description: 把 map 写入 json 文件中
     * @params:
     * @return:
     * @createTime: 14:16  2022/3/14
     * @updateTime: 14:16  2022/3/14
     ************************************************************************/
    public static void writeToJson(Map map, ClassPathResource resource) throws IOException {
        String path = resource.getAbsolutePath();
        File file = new File(path);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, map);
    }

    /************************************************************************
     * @author: wg
     * @description: 读取 json 文件
     * @params:
     * @return:
     * @createTime: 14:12  2022/3/14
     * @updateTime: 14:12  2022/3/14
     ************************************************************************/
    public static JsonNode readJson(ClassPathResource resource) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        BufferedInputStream stream = new BufferedInputStream(resource.getStream());
        byte[] buff = new byte[1024];
        StringBuilder builder = new StringBuilder();

        int read;
        while ((read = stream.read(buff)) != -1) {
            builder.append(new String(buff, 0, read, StandardCharsets.US_ASCII));
        }
        stream.close();
        return mapper.readTree(builder.toString());
        //
        // StringBuilder originalFileContent = new StringBuilder();
        // BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
        // String s = null;
        // while ((s = br.readLine()) != null) {
        //     originalFileContent.append(s).append("\n");
        // }
        // br.close();

        // return mapper.readTree(originalFileContent.toString());
    }
}
