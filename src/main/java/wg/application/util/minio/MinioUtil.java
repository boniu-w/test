// package wg.application.util.minio;
//
// import io.minio.*;
// import io.minio.errors.*;
// import io.minio.http.Method;
// import io.minio.messages.Item;
// import lombok.SneakyThrows;
// import org.apache.commons.lang3.StringUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.lang.Nullable;
// import org.springframework.stereotype.Component;
// import org.springframework.util.FastByteArrayOutputStream;
// import org.springframework.web.multipart.MultipartFile;
// import wg.application.exception.WgException;
// import wg.application.util.FileUtil;
//
// import javax.annotation.PostConstruct;
// import javax.annotation.Resource;
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletResponse;
// import java.io.ByteArrayInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.security.InvalidKeyException;
// import java.security.NoSuchAlgorithmException;
// import java.sql.Date;
// import java.util.*;
// import java.util.stream.Collectors;
//
// /**
//  * @description: minio工具类
//  * @author: lzq
//  * @create: 2024-03-18 15:17
//  **/
// @Component
// public class MinioUtil {
//     private static final Logger logger = LoggerFactory.getLogger(MinioUtil.class);
//
//     private static MinioUtil minioUtil;
//
//     @Resource
//     private MinioProperties minioProperties;
//
//     @Resource
//     MinioClient minioClient;
//
//     @PostConstruct
//     private void init() {
//         System.out.println("minioUtil = ");
//         minioUtil = this;
//     }
//
//     /**
//      * @param multipartFile request
//      * @param tableName     表名
//      * @param refId         id
//      * @param colName       哪个
//      * @param folderName    文件夹名 可为""
//      * @param description   用户信息 可为""
//      * @return 返回桶名预览地址
//      * @author wg
//      * @description 上传文件
//      * @createTime 11:30  2024/7/15
//      * @updateTime 11:30  2024/7/15
//      */
//     public static Map<String, String> uploadReportContainInfo(MultipartFile multipartFile, String tableName, String refId, String colName, String folderName, String description) {
//         if (StringUtils.isBlank(colName)) {
//             colName = "";
//         }
//         if (StringUtils.isBlank(folderName)) {
//             folderName = "";
//         }
//         if (StringUtils.isBlank(description)) {
//             description = "";
//         }
//         Map<String, String> map = new HashMap<String, String>();
//         String fileUrl = "";
//         String newBucket = minioUtil.minioProperties.getBucketName();
//         try {
//             // 检查存储桶是否已经存在
//             if (!minioUtil.minioClient.bucketExists(BucketExistsArgs.builder().bucket(newBucket).build())) {
//                 // 创建一个名为ota的存储桶
//                 minioUtil.minioClient.makeBucket(MakeBucketArgs.builder().bucket(newBucket).build());
//                 logger.info("create a new bucket.");
//             }
//
//             InputStream stream = multipartFile.getInputStream();
//             // 获取文件名
//             String orgName = multipartFile.getOriginalFilename();
//             if ("".equals(orgName)) {
//                 orgName = multipartFile.getName();
//             }
//             orgName = FileUtil.reviseFileName(orgName);
//
//             String objectName = orgName;
//             // 使用putObject上传一个本地文件到存储桶中。
//             if (objectName.startsWith("/")) {
//                 objectName = objectName.substring(1);
//             }
//
//             // objectName = tableName + "/" + hexHash + "/" + orgName;
//             if (StringUtils.isNotBlank(folderName)) {
//                 objectName = tableName + "/" + colName + "/" + refId + "/" + folderName + "/" + orgName;
//             } else {
//                 objectName = tableName + "/" + colName + "/" + refId + "/" + orgName;
//             }
//             fileUrl = "/" + newBucket + "/" + objectName;
//             map.put("fileUrl", fileUrl);
//             map.put("previewURL", objectName);
//
//             // 编码中文描述为 Base64
//             String encodedDescription = Base64.getEncoder().encodeToString(description.getBytes("UTF-8"));
//
//             Map<String, String> userMetadataMap = new HashMap<>();
//             userMetadataMap.put("userInfo", encodedDescription);
//             PutObjectArgs objectArgs = PutObjectArgs.builder()
//                     .object(objectName)
//                     .bucket(newBucket)
//                     .contentType(getFileContentType(orgName))
//                     .userMetadata(userMetadataMap)
//                     .stream(stream, stream.available(), -1)
//                     .build();
//             minioUtil.minioClient.putObject(objectArgs);
//
//             stream.close();
//         } catch (Exception e) {
//             logger.error(e.getMessage(), e);
//         }
//         return map;
//     }
//
//     /************************************************************************
//      * @author: wg
//      * @description: 预览地址
//      * @params:
//      * @return:
//      * @createTime: 18:30  2022/4/7
//      * @updateTime: 18:30  2022/4/7
//      ************************************************************************/
//     public static String getPreviewURL(@Nullable String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//         objectName = objectName.trim();
//         if (StringUtils.isBlank(bucketName)) {
//             bucketName = minioUtil.minioProperties.getBucketName();
//         }
//         Map<String, String> reqParams = new HashMap<String, String>();
//         reqParams.put("response-content-type", getFileContentType(objectName));
//         GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
//                 .method(Method.GET)
//                 .bucket(bucketName)
//                 .object(objectName)
//                 .extraQueryParams(reqParams)
//                 .build();
//         String url = minioUtil.minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
//         url = url.replace(minioUtil.minioProperties.getEndpoint(), "/file");
//         return url;
//     }
//
//     /**
//      * 根据文件后缀, 确定 PutObjectArgs.builder().contentType
//      */
//     private static String getFileContentType(String fileName) {
//         if (fileName.contains(".")) {
//             String returnFileName = fileName.substring(fileName.lastIndexOf("."));
//             switch (returnFileName) {
//                 case ".jpeg":
//                 case ".png":
//                 case ".jpg":
//                 case ".webp":
//                     return "image/jpeg";
//                 case ".mp4":
//                     return "video/mp4";
//                 case ".html":
//                     return "text/html";
//                 case ".css":
//                     return "text/css";
//                 case ".js":
//                     return "application/javascript";
//                 case ".pdf":
//                     return "application/pdf";
//                 default:
//                     return "application/octet-stream";
//             }
//         } else {
//             return "application/octet-stream";
//         }
//     }
//
//     /**
//      * @param
//      * @return
//      * @author wg
//      * @description 下载文件
//      * @createTime 11:18  2024/7/15
//      * @updateTime 11:18  2024/7/15
//      */
//     public static void download(String objectName, String fileName, HttpServletResponse res) {
//         GetObjectArgs objectArgs = GetObjectArgs.builder()
//                 .bucket(minioUtil.minioProperties.getBucketName())
//                 .object(objectName)
//                 .build();
//         try (GetObjectResponse response = minioUtil.minioClient.getObject(objectArgs)) {
//             byte[] buf = new byte[1024];
//             int len;
//             try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
//                 while ((len = response.read(buf)) != -1) {
//                     os.write(buf, 0, len);
//                 }
//                 os.flush();
//                 byte[] bytes = os.toByteArray();
//                 res.setCharacterEncoding("utf-8");
//                 //设置强制下载不打开
//                 res.setContentType("application/force-download");
//                 res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
//                 try (ServletOutputStream stream = res.getOutputStream()) {
//                     stream.write(bytes);
//                     stream.flush();
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
//
//     /************************************************************************
//      * @author: wg
//      * @description: 删除文件
//      * @params:
//      * @return:
//      * @createTime: 14:55  2022/4/21
//      * @updateTime: 14:55  2022/4/21
//      ************************************************************************/
//     public static void deleteObject(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//         if (StringUtils.isNotBlank(objectName)) {
//             RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
//                     .bucket(minioUtil.minioProperties.getBucketName())
//                     .object(objectName)
//                     .build();
//             minioUtil.minioClient.removeObject(removeObjectArgs);
//             logger.info("删除文件: {}", objectName);
//         }
//     }
//
//     //递归删除文件夹及下文件
//     public static void deleteObjectgroup(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//         if (StringUtils.isNotBlank(objectName)) {
//
//             Iterable<Result<Item>> results = minioUtil.minioClient.listObjects(
//                     ListObjectsArgs.builder()
//                             .bucket(minioUtil.minioProperties.getBucketName())
//                             .prefix(objectName)
//                             .recursive(false)
//                             .build()
//             );
//             for (Result<Item> result : results) {
//                 Item item = result.get();
//                 if (item.isDir()) {
//                     deleteObjectgroup(item.objectName());
//
//                     continue;
//                 }
//                 minioUtil.minioClient.removeObject(
//                         RemoveObjectArgs.builder()
//                                 .bucket(minioUtil.minioProperties.getBucketName())
//                                 .object(item.objectName())
//                                 .build()
//                 );
//             }
//             logger.info("删除文件: {}", objectName);
//         }
//     }
//
//     /************************************************************************
//      * @author: wg
//      * @description: 回收站
//      * @params:
//      * @return:
//      * @createTime: 11:03  2022/12/5
//      * @updateTime: 11:03  2022/12/5
//      ************************************************************************/
//     public static DocumentFileVO deleteLogic(DocumentFileVO docForDelete, String columName) {
//         String bucketName = minioUtil.minioProperties.getBucketName();
//         String pattern = new StringBuilder().append("/").append(bucketName).toString();
//         String objectNameSource = docForDelete.getFilePath().split(pattern)[1];
//         String objectNameTarget = "";
//         String filePathTarget = "";
//         Long refId = docForDelete.getRefId();
//         String tableName = docForDelete.getTableName();
//         String hexHash = docForDelete.getHexHash();
//         String fileName = docForDelete.getFileName();
//         String deletedDirFor = "deleted";
//
//         GetObjectArgs getObjectArgs = GetObjectArgs.builder()
//                 .bucket(minioUtil.minioProperties.getBucketName())
//                 .object(objectNameSource)
//                 .build();
//
//         try (GetObjectResponse response = minioUtil.minioClient.getObject(getObjectArgs)) {
//
//             byte[] buf = new byte[1024];
//             int len;
//             try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
//                 while ((len = response.read(buf)) != -1) {
//                     os.write(buf, 0, len);
//                 }
//                 os.flush();
//                 byte[] bytes = os.toByteArray();
//
//                 ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//
//                 objectNameTarget = new StringBuilder()
//                         .append("/")
//                         .append(deletedDirFor)
//                         .append("/")
//                         .append(tableName)
//                         .append("/")
//                         .append(columName)
//                         .append("/")
//                         .append(refId)
//                         .append("/")
//                         .append(fileName)
//                         .toString();
//                 filePathTarget = bucketName + objectNameTarget;
//                 docForDelete.setFilePath(filePathTarget);
//
//                 PutObjectArgs objectArgs = PutObjectArgs.builder()
//                         .object(objectNameTarget)
//                         .bucket(bucketName)
//                         .contentType(getFileContentType(objectNameTarget))
//                         .stream(byteArrayInputStream, byteArrayInputStream.available(), -1)
//                         .build();
//                 minioUtil.minioClient.putObject(objectArgs);
//
//                 byteArrayInputStream.close();
//                 os.close();
//                 response.close();
//             }
//
//             deleteObject(objectNameSource);
//         } catch (Exception e) {
//             e.printStackTrace();
//             logger.error("删除文件出错");
//             WgException sevenmeException = new WgException(500);
//             sevenmeException.setMsg("删除文件出错");
//             throw sevenmeException;
//         }
//
//         return docForDelete;
//     }
//
//     /************************************************************************
//      * @author: wg
//      * @description: 从临时路径中取出文件(temp 文件夹的某个位置), 然后保存到 正式路径中
//      * @params:
//      * @return:
//      * @createTime: 15:20  2022/9/26
//      * @updateTime: 15:20  2022/9/26
//      ************************************************************************/
//     @SneakyThrows
//     public static DocumentFileVO saveFile(DocumentFileVO documentFile, String columName) {
//         String bucketName = minioUtil.minioProperties.getBucketName();
//         String pattern = new StringBuilder().append("/").append(bucketName).toString();
//         String tempObjectName = documentFile.getFilePath().split(pattern)[1];
//         String realObjectName = "";
//         String realFilePath = "";
//         Long refId = documentFile.getRefId();
//         String tableName = documentFile.getTableName();
//         String hexHash = documentFile.getHexHash();
//         String fileName = documentFile.getFileName();
//
//         GetObjectArgs getObjectArgs = GetObjectArgs.builder()
//                 .bucket(minioUtil.minioProperties.getBucketName())
//                 .object(tempObjectName)
//                 .build();
//
//         StatObjectArgs statObjectArgs = StatObjectArgs.builder()
//                 .bucket(minioUtil.minioProperties.getBucketName())
//                 .object(tempObjectName)
//                 .build();
//         StatObjectResponse statObjectResponse = minioUtil.minioClient.statObject(statObjectArgs);
//
//
//         try (GetObjectResponse response = minioUtil.minioClient.getObject(getObjectArgs)) {
//
//             byte[] buf = new byte[1024];
//             int len;
//             try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
//                 while ((len = response.read(buf)) != -1) {
//                     os.write(buf, 0, len);
//                 }
//                 os.flush();
//                 byte[] bytes = os.toByteArray();
//
//                 ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//
//                 realObjectName = "/" + tableName + "/" + columName + "/" + refId + "/" + fileName;
//                 realFilePath = bucketName + realObjectName;
//                 documentFile.setFilePath(realFilePath);
//
//                 Map<String, String> stringStringMap = statObjectResponse.userMetadata();
//
//
//                 PutObjectArgs objectArgs = PutObjectArgs.builder()
//                         .object(realObjectName)
//                         .bucket(bucketName)
//                         .contentType(getFileContentType(realObjectName))
//                         .userMetadata(stringStringMap)
//                         .stream(byteArrayInputStream, byteArrayInputStream.available(), -1)
//                         .build();
//                 minioUtil.minioClient.putObject(objectArgs);
//
//                 byteArrayInputStream.close();
//                 os.close();
//                 response.close();
//             }
//             deleteObject(tempObjectName);
//         } catch (Exception e) {
//             e.printStackTrace();
//             logger.error("保存文件出错");
//             WgException sevenmeException = new WgException(500);
//             sevenmeException.setMsg("保存文件出错");
//             throw sevenmeException;
//         }
//
//         return documentFile;
//     }
//
//     /************************************************************************
//      * @author: wg
//      * @description: 获取文件夹下 所有文件 关联 refId
//      * /${bucketName}/history_inspection/site_pic/1572821177901928450/README.md
//      * /${bucketName}/${tableName}/${colName}/${id}/${fileName}
//      * @params:
//      * @return:
//      * @createTime: 14:11  2022/9/27
//      * @updateTime: 14:11  2022/9/27
//      ************************************************************************/
//     public static List<DocumentFileVO> getAllFile(String prefix, Long id) {
//         List<DocumentFileVO> fileList = new ArrayList<>();
//         MinioUtil.getAllFile(prefix, fileList);
//
//         fileList = fileList.stream()
//                 .filter(file -> {
//                     if (file.getFilePath().split("/").length == 6) {
//                         return Objects.equals(id.toString(), file.getFilePath().split("/")[4]);
//                     }
//                     return false;
//                 })
//                 .collect(Collectors.toList());
//
//         return fileList;
//     }
//
//     private static List<DocumentFileVO> getAllFile(String prefix, List<DocumentFileVO> fileList) {
//         try {
//             Iterable<Result<Item>> results = minioUtil.minioClient.listObjects(
//                     ListObjectsArgs.builder()
//                             .bucket(minioUtil.minioProperties.getBucketName())
//                             .prefix(prefix)
//                             .recursive(false)
//                             .build());
//             Item item = null;
//             String objectName = "";
//             DocumentFileVO documentFileVO = null;
//
//             if (results != null && results.iterator().hasNext()) {
//                 for (Result<Item> result : results) {
//                     item = result.get();
//                     objectName = item.objectName(); // history_inspection/site_pic/1572821177901928450/common.sql
//                     if (!item.isDir()) {
//                         documentFileVO = new DocumentFileVO();
//
//                         String[] split = objectName.split("/");
//                         String tableName = split[0];
//                         String columName = split[split.length - 3];
//                         String refId = split[split.length - 2];
//                         String fileName = split[split.length - 1];
//
//                         StatObjectResponse stat = getObjInfo(objectName);
//                         String previewURL = getPreviewURL(null, objectName);
//
//                         documentFileVO.setFileName(fileName);
//                         documentFileVO.setLastedModified(Date.from(item.lastModified().toInstant()));
//                         documentFileVO.setFilePath("/" + minioUtil.minioProperties.getBucketName() + "/" + objectName);
//                         documentFileVO.setFileSize(stat.size());
//                         documentFileVO.setRefId(Long.parseLong(refId));
//                         documentFileVO.setTableName(tableName);
//                         documentFileVO.setPreviewUrl(previewURL);
//                         documentFileVO.setColumnName(columName);
//
//                         Map<String, String> userMetadataMap = item.userMetadata();
//                         if (userMetadataMap != null) {
//                             String metadata = userMetadataMap.getOrDefault("userInfo", "");
//                             // 解码 Base64 编码的字符串为字节数组
//                             byte[] decodedBytes = Base64.getDecoder().decode(metadata);
//                             // 将字节数组转换为字符串，指定字符集为 UTF-8
//                             String chineseDescription = new String(decodedBytes, "UTF-8");
//                             // documentFileEntity.setUserInfo(chineseDescription);
//                         }
//
//                         String[] pa = fileName.split("\\.");
//
//                         if (pa.length > 1) {
//                             documentFileVO.setFileExt(pa[1]);
//                         }
//
//                         fileList.add(documentFileVO);
//                     } else {
//                         getAllFile(objectName, fileList);
//                     }
//                 }
//             }
//         } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
//                  InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
//                  XmlParserException e) {
//             logger.error("minioutil error: {0}", e);
//             return new ArrayList<>();
//         }
//
//         return fileList;
//     }
//
//     public static StatObjectResponse getObjInfo(String originalName) {
//         StatObjectResponse stat = null;
//         try {
//             stat = minioUtil.minioClient.statObject(
//                     StatObjectArgs.builder()
//                             .bucket(minioUtil.minioProperties.getBucketName())
//                             .object(originalName)
//                             .build());
//             return stat;
//
//         } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException |
//                  IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
//             throw new RuntimeException(e);
//         }
//     }
// }