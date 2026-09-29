package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 组织机构
 */
@Data
public class OrgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 组织机构标识
    private String id;

    // 机构名称
    private String orgName;

    // 机构英文名称
    private String orgEnName;

    // 机构简称
    private String orgShortName;

    // 机构类型代码
    private String orgTypeCode;

    // 上级机构标识
    private String parentOrgId;

    // 是否有效
    private Integer isValid;

    // 备注
    private String remark;

    // 所属单元
    private String unit;

    // 排序
    private Integer sortOrder;

    // 建立时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 创建人
    private String createBy;

    // 更新人
    private String updateBy;

}