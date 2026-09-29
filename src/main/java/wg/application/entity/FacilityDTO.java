package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 设施 DTO
 */
@Data
public class FacilityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 设施标识
    private String id;

    // 海外资产标识
    private String assetRegisterId;

    // 组织机构标识
    private String orgId;

    // 机构名称
    private String orgName;

    // 机构英文名称
    private String orgEnName;


    // 海外资产名称
    private String assetName;

    // 海外资产英文名称
    private String assetEnName;

    // 设施编码
    @NotNull(message = "设施编码不能为空")
    private String facilityCode;

    // 设施名称
    @NotNull(message = "设施名称不能为空")
    private String facilityName;

    // 设施类型
    @NotNull(message = "设施类型不能为空")
    private String xbdFacilityType;

    // 设施类型名称
    private String xbdFacilityTypeName;

    // 所属阶段
    @NotNull(message = "所属阶段不能为空")
    private String xeqBusinessPhase;

    // 合同年限[Year]
    private Integer contractLife;

    // 设计年限
    private Integer designLife;

    // 设计到期年月[YYYY-MM-DD]
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime designExpirationDate;

    // 设计到期年月
    private String[] designExpirationDateRange;

    // 进入时间[YYYY-MM-DD]
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryTime;

    // 进入时间
    private String[] entryTimeRange;

    // 制造时间[YYYY-MM-DD]
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime manufacturingTime;

    // 制造时间
    private String[] manufacturingTimeRange;

    // 退出时间[YYYY-MM-DD]
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;

    // 退出时间
    private String[] expirationDateRange;

    // 是否重要
    @NotNull(message = "是否重要不能为空")
    private Integer isImportant;

    // 备注
    private String remark;

    // 经度
    private BigDecimal longitude;

    // 纬度
    private BigDecimal latitude;

    // 起始设施id
    private String fromFacilityId;

    // 终止设施id
    private String toFacilityId;

    // 是否系统内置
    private Integer isSystem;

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

    private String locale;
    private String xeqBusinessPhaseId;
}