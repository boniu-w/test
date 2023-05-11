package wg.application.util;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:50 2022/3/14
 * @updateTime: 16:50 2022/3/14
 ************************************************************************/
public class FileUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    
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
    
    public static String mapToJsonFile(Map<String, Object> map, String path) {
        // 创建一个 ObjectMapper 对象
        ObjectMapper mapper = new ObjectMapper();
        
        // 使用 ObjectMapper 的 writeValueAsString() 方法将 Map 转换成 JSON 字符串
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(map);
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            mapper.writeValue(file, jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }
    
    public static void jsonbject2jsonFile(JSONObject jsonObject, String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(jsonObject.toString());
            bw.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    /************************************************************************
     * @author: wg
     * @description: 把 object 写入 file 中
     * @params:
     * @return:
     * @createTime: 12:46  2023/5/11
     * @updateTime: 12:46  2023/5/11
     ************************************************************************/
    public static void object2file(Object obj, String path) {
        String jsonString = JSON.toJSONString(obj);
        int i = judgeThePath(path);
        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
            fileOutputStream.write(bytes);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }
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
    
    /**
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        
        File file = null;
        if (multipartFile.equals("") || multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            file = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, file);
            ins.close();
        }
        return file;
    }
    
    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /************************************************************************
     * @author: wg
     * @description: 获取文件名, 会去掉 类似这样的 `//12312/sdf.jpg` 之中的 斜杠, 只保留  `sdf.jpg`
     *   String path="12312/sdf.jpg";
     *   String s = FileUtil.reviseFileName(path);
     *   System.out.println(s); // sdf.jpg
     * @params:
     * @return:
     * @createTime: 10:20  2022/4/27
     * @updateTime: 10:20  2022/4/27
     ************************************************************************/
    public static String reviseFileName(String fileName) {
        //判断是否带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1) {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        //替换上传文件名字的特殊字符
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "")
                .replace("#", "").replace("“", "").replace("”", "");
        //替换上传文件名字中的空格
        fileName = fileName.replaceAll("\\s", "");
        return fileName;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 获取文件hash值
     * @params:
     * @return:
     * @createTime: 12:57  2022/9/8
     * @updateTime: 12:57  2022/9/8
     ************************************************************************/
    public static String getSha256Hex(MultipartFile multipartFile) throws Exception {
        // 对 multipartfile 的内容 生成 hash
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(multipartFile.getBytes());
        byte[] digest1 = messageDigest.digest();
        return new BigInteger(1, digest1).toString(16);
    }
    
    public static String getSha256Hex(File file) throws Exception {
        // 对 file 的内容 生成 hash
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(cn.hutool.core.io.FileUtil.readBytes(file));
        byte[] digest1 = messageDigest.digest();
        return new BigInteger(1, digest1).toString(16);
    }
    
    /************************************************************************
     * @author: wg
     * @description: 获取文件夹下所有文件
     * @params:
     * @return:
     * @createTime: 17:28  2022/9/8
     * @updateTime: 17:28  2022/9/8
     ************************************************************************/
    public static List<File> getAllFile(String path, List<File> fileList) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files && files.length > 0) {
                BasicFileAttributes basicFileAttributes = null;
                for (File file1 : files) {
                    basicFileAttributes = Files.readAttributes(file1.toPath(), BasicFileAttributes.class);
                    if (basicFileAttributes.isRegularFile()) {
                        fileList.add(file1);
                    } else if (basicFileAttributes.isDirectory()) {
                        getAllFile(file1.getPath(), fileList);
                    }
                }
            }
        }
        
        return fileList;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 清空文件夹下面的所有文件, 但保留这个文件夹
     * @params:
     * @return:
     * @createTime: 9:50  2022/9/9
     * @updateTime: 9:50  2022/9/9
     ************************************************************************/
    public static boolean clearDir(String path) {
        File file = new File(path);
        if (!file.exists()) {//判断是否待删除目录是否存在
            logger.info("The dir are not exists!");
            return false;
        }
        
        if (file.isFile()) {
            if (!file.delete()) {
                logger.error("文件删除失败");
                return false;
            }
        }
        
        String[] content = file.list();//取得当前目录下所有文件和文件夹
        if (content != null) {
            for (String name : content) {
                File temp = new File(path, name);
                if (temp.isDirectory()) {//判断是否是目录
                    clearDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                    temp.delete();//删除空目录
                } else {
                    //直接删除文件
                    if (!temp.delete()) {
                        logger.error("Failed to delete " + name);
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    /************************************************************************
     * @author: wg
     * @description: 读取一个文件夹下的所有文件, 去重后 保存 到 临时文件夹中,
     * @params: path: 源文件所在目录  anotherPath: 另一个目录
     * @return:
     * @createTime: 17:16  2022/9/8
     * @updateTime: 17:16  2022/9/8
     ************************************************************************/
    public static void copyToTempDir(String path, String anotherPath) throws Exception {
        // ↓↓******************* start <1. 读取文件夹下的所有文件, 然后去重> *******************↓↓
        List<File> fileList = new ArrayList<>();
        getAllFile(path, fileList);
        
        // 用 map 来去重
        HashMap<String, File> fileHashMap = new HashMap<>();
        String hexHash = "";
        for (File file : fileList) {
            hexHash = getSha256Hex(file);
            fileHashMap.put(hexHash, file);
        }
        // ↑↑******************* end  <code>  *******************↑↑
        
        // ↓↓******************* start <复制到另一个文件夹, 流中文件删不掉, 另一个程序正在使用此文件> *******************↓↓
        FileInputStream fileInputStream = null;
        HashMap<FileInputStream, String> streamStringHashMap = new HashMap<>();
        
        for (File file1 : fileHashMap.values()) {
            fileInputStream = new FileInputStream(file1);
            String name = file1.getName();
            streamStringHashMap.put(fileInputStream, name);
        }
        
        File tempDir = null;
        for (Map.Entry<FileInputStream, String> entry : streamStringHashMap.entrySet()) {
            FileInputStream inputStream = entry.getKey();
            String name = entry.getValue();
            tempDir = new File(anotherPath);
            if (!tempDir.exists()) tempDir.mkdir();
            cn.hutool.core.io.FileUtil.writeFromStream(inputStream, new File(anotherPath, name));
        }
        // ↑↑******************* end  <code>  *******************↑↑
    }
    
    public static int judgeThePath(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 1;
    }
}
