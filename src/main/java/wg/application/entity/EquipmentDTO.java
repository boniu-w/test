package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备总表
 */
@Data
public class EquipmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 设备标识
    private String id;

    // 设施标识
    @NotNull(message = "设施标识不能为空")
    private String facilityId;

    // 组织机构标识
    private String orgId;

    // 机构名称
    private String orgName;

    // 机构名称
    private String orgEnName;

    // 海外资产标识
    private String assetRegisterId;

    // 海外资产名称
    private String assetName;

    // 海外资产英文名称
    private String assetEnName;

    // 设施类型
    private String xbdFacilityType;

    // 设施类型名称
    // private String xbdFacilityTypeName;

    // 设施编码
    private String facilityCode;

    // 设施名称
    private String facilityName;

    // 是否系统内置
    private String isSystem;

    // 设备编码
    @NotNull(message = "设备编码不能为空")
    private String equipmentCode;

    // 设备名称
    @NotNull(message = "设备名称不能为空")
    private String equipmentName;

    // 设备类型
    @NotNull(message = "设备类型不能为空")
    private String xbdEquipmentType;

    // 设备类型名称
    // private String xbdEquipmentTypeName;

    // 所属阶段
    @NotNull(message = "所属阶段不能为空")
    private String xeqBusinessPhase;

    // 运行状态
    @NotNull(message = "运行状态不能为空")
    private String xeqRunningStatus;

    // 出厂日期[YYYY-MM-DD]
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime productionDate;

    // 出厂日期[YYYY-MM-DD]
    private String[] productionDateRange;

    // 生产厂家
    private String manufacturer;

    // 型号
    private String model;

    // 成橇厂家
    private String skidManufacturer;

    // 规格
    private String specification;

    // 是否关键
    @NotNull(message = "是否关键不能为空")
    private Integer isImportant;

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

    // 是否透平
    private String isTurbine;

    private String locale;
    private String facilityCodeName;

    private String xeqRunningStatusId;
    private String xeqBusinessPhaseId;
    private String xbdEquipmentTypeId;
}