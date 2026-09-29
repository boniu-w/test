package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 海外资产注册表
 */
@Data
public class AssetRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 海外资产标识
    private String id;

    // 组织机构标识
    private String orgId;

    // 组织机构名称
    private String orgName;

    // 海外资产合同类型
    private String assetContractType;

    // 海外资产名称
    private String assetName;

    // 海外资产英文名称
    private String assetEnName;

    // 所属单元
    private String unit;

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