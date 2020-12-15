package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import wg.application.util.Excel;

import java.util.Date;

/*************************************************************
 * @Package net.mingsoft.cms.entity
 * @author wg
 * @date 2020/10/27 15:12
 * @version
 * @Copyright
 *************************************************************/
@Data
public class DutyEntity {

    private String id;

    // 值班领导
    @Excel(name = "值班领导")
    private String dutyLeader;

    // 值守组班长
    @Excel(name = "值守组班长")
    private String onDutyMonitor;

    // 值守组 成员
    @Excel(name = "值守组成员")
    private String onDutyMember;

    // 机动组班长
    @Excel(name = "机动组班长")
    private String flexibleMonitor;

    // 机动组成员
    @Excel(name = "机动组成员")
    private String flexibleMember;

    // 所属组
    @Transient  // 非数据库字段 注解
    private String group;


    @Excel(name = "值班日期(格式 2020/1/1)")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dutyDate;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
