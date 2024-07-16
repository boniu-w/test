package wg.application.util.minio;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件文档存储
 *
 * @author Seven ME info@7-me.net
 * @since v1.0.0 2022-09-14
 */
@Data
public class DocumentFileVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String tableName;

    private String columnName;

    private String fileName;

    private Long refId;

    private String hexHash;

    private String filePath;

    private String fileDesc;

    private String fileExt;

    private Boolean delFlag;

    private Date createTime;

    private Date updateTime;

    private Long fileSize;

    private Date lastedModified;

    private String previewUrl;

    @Override
    public String toString() {
        return "DocumentFileVO{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileDesc='" + fileDesc + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", refId=" + refId +
                ", hexHash='" + hexHash + '\'' +
                ", delFlag=" + delFlag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", fileSize=" + fileSize +
                ", lastedModified=" + lastedModified +
                ", previewUrl='" + previewUrl + '\'' +
                '}';
    }
}