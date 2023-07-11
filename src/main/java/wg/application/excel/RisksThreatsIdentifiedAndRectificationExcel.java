package wg.application.excel;

import lombok.Data;
import wg.application.excel.annotation.ExcelAnnotation;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 本月设备设施隐患整改情况统计
 *
 * @author Seven ME info@7-me.net
 * @since v1.0.0 2023-06-28
 */
@Data
public class RisksThreatsIdentifiedAndRectificationExcel {
    @ExcelAnnotation(name = "本月设备设施隐患整改情况统计id")
    private String id;
    @ExcelAnnotation(name = "月报id")
    private String monthlyReportId;
    @ExcelAnnotation(name = "设施标识")
    private String facilityId;
    @ExcelAnnotation(name = {"本月风险/隐患识别数量", "Monthly Identified Risks/Threats"})
    private Integer numberOfMonthlyIdentifiedRisksThreats;
    @ExcelAnnotation(name = {"本月风险/隐患整改数量", "Monthly Rectified Risks/Threats"})
    private Integer numberOfMonthlyRectifiedRisksThreats;
    @ExcelAnnotation(name = {"年度风险/隐患识别数量", "Annually Identified Risks/Threats"})
    private Integer numberOfAnnuallyIdentifiedRisksThreats;
    @ExcelAnnotation(name = {"年度风险/隐患整改数量", "Number of Annually Rectified Risks/Threats"})
    private Integer numberOfAnnuallyRectifiedRisksThreats;
    @ExcelAnnotation(name = "年度整改率")
    private BigDecimal annualRectificationRate;
    @ExcelAnnotation(name = "备注")
    private String remark;
    @ExcelAnnotation(name = "建立时间")
    private Date createTime;
    @ExcelAnnotation(name = "更新时间")
    private Date updateTime;
    @ExcelAnnotation(name = "创建人")
    private String createBy;
    @ExcelAnnotation(name = "更新人")
    private String updateBy;
    @ExcelAnnotation(name = "删除标志")
    private Integer delFlag;
    
}