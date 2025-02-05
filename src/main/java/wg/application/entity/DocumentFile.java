package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Database Table Remarks:
 *   文件文档存储
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table document_file
 */
public class DocumentFile {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   关联表表名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.table_name
     *
     * @mbg.generated
     */
    private String tableName;

    /**
     * Database Column Remarks:
     *   文件名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.file_name
     *
     * @mbg.generated
     */
    private String fileName;

    /**
     * Database Column Remarks:
     *   文件存储路径
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.file_path
     *
     * @mbg.generated
     */
    private String filePath;

    /**
     * Database Column Remarks:
     *   文件描述
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.file_desc
     *
     * @mbg.generated
     */
    private String fileDesc;

    /**
     * Database Column Remarks:
     *   文件扩展名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.file_ext
     *
     * @mbg.generated
     */
    private String fileExt;

    /**
     * Database Column Remarks:
     *   文件相关联的表中的id逻辑fk
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.ref_id
     *
     * @mbg.generated
     */
    private Long refId;

    /**
     * Database Column Remarks:
     *   散列值
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.hex_hash
     *
     * @mbg.generated
     */
    private String hexHash;

    /**
     * Database Column Remarks:
     *   删除标志
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.del_flag
     *
     * @mbg.generated
     */
    private Boolean delFlag;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.create_time
     *
     * @mbg.generated
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   更新时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column document_file.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.id
     *
     * @return the value of document_file.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.id
     *
     * @param id the value for document_file.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.table_name
     *
     * @return the value of document_file.table_name
     *
     * @mbg.generated
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.table_name
     *
     * @param tableName the value for document_file.table_name
     *
     * @mbg.generated
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.file_name
     *
     * @return the value of document_file.file_name
     *
     * @mbg.generated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.file_name
     *
     * @param fileName the value for document_file.file_name
     *
     * @mbg.generated
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.file_path
     *
     * @return the value of document_file.file_path
     *
     * @mbg.generated
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.file_path
     *
     * @param filePath the value for document_file.file_path
     *
     * @mbg.generated
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.file_desc
     *
     * @return the value of document_file.file_desc
     *
     * @mbg.generated
     */
    public String getFileDesc() {
        return fileDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.file_desc
     *
     * @param fileDesc the value for document_file.file_desc
     *
     * @mbg.generated
     */
    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.file_ext
     *
     * @return the value of document_file.file_ext
     *
     * @mbg.generated
     */
    public String getFileExt() {
        return fileExt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.file_ext
     *
     * @param fileExt the value for document_file.file_ext
     *
     * @mbg.generated
     */
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.ref_id
     *
     * @return the value of document_file.ref_id
     *
     * @mbg.generated
     */
    public Long getRefId() {
        return refId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.ref_id
     *
     * @param refId the value for document_file.ref_id
     *
     * @mbg.generated
     */
    public void setRefId(Long refId) {
        this.refId = refId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.hex_hash
     *
     * @return the value of document_file.hex_hash
     *
     * @mbg.generated
     */
    public String getHexHash() {
        return hexHash;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.hex_hash
     *
     * @param hexHash the value for document_file.hex_hash
     *
     * @mbg.generated
     */
    public void setHexHash(String hexHash) {
        this.hexHash = hexHash;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.del_flag
     *
     * @return the value of document_file.del_flag
     *
     * @mbg.generated
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.del_flag
     *
     * @param delFlag the value for document_file.del_flag
     *
     * @mbg.generated
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.create_time
     *
     * @return the value of document_file.create_time
     *
     * @mbg.generated
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.create_time
     *
     * @param createTime the value for document_file.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column document_file.update_time
     *
     * @return the value of document_file.update_time
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column document_file.update_time
     *
     * @param updateTime the value for document_file.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table document_file
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tableName=").append(tableName);
        sb.append(", fileName=").append(fileName);
        sb.append(", filePath=").append(filePath);
        sb.append(", fileDesc=").append(fileDesc);
        sb.append(", fileExt=").append(fileExt);
        sb.append(", refId=").append(refId);
        sb.append(", hexHash=").append(hexHash);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}