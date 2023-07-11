package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import wg.application.excel.annotation.Excel;

import java.util.Arrays;
import java.util.Date;

/*************************************************************
 * @Package net.mingsoft.cms.entity
 * @author wg
 * @date 2020/10/27 15:12
 * @version
 * @Copyright
 *************************************************************/
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dutyDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Transient
    private Date[] dutyDateArray;

    public Date[] getDutyDateArray() {
        return dutyDateArray;
    }

    public void setDutyDateArray(Date[] dutyDateArray) {
        this.dutyDateArray = dutyDateArray;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDutyLeader() {
        return dutyLeader;
    }

    public void setDutyLeader(String dutyLeader) {
        this.dutyLeader = dutyLeader;
    }

    public String getOnDutyMonitor() {
        return onDutyMonitor;
    }

    public void setOnDutyMonitor(String onDutyMonitor) {
        this.onDutyMonitor = onDutyMonitor;
    }

    public String getOnDutyMember() {
        return onDutyMember;
    }

    public void setOnDutyMember(String onDutyMember) {
        this.onDutyMember = onDutyMember;
    }

    public String getFlexibleMonitor() {
        return flexibleMonitor;
    }

    public void setFlexibleMonitor(String flexibleMonitor) {
        this.flexibleMonitor = flexibleMonitor;
    }

    public String getFlexibleMember() {
        return flexibleMember;
    }

    public void setFlexibleMember(String flexibleMember) {
        this.flexibleMember = flexibleMember;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(Date dutyDate) {
        this.dutyDate = dutyDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DutyEntity{" +
                "id='" + id + '\'' +
                ", dutyLeader='" + dutyLeader + '\'' +
                ", onDutyMonitor='" + onDutyMonitor + '\'' +
                ", onDutyMember='" + onDutyMember + '\'' +
                ", flexibleMonitor='" + flexibleMonitor + '\'' +
                ", flexibleMember='" + flexibleMember + '\'' +
                ", group='" + group + '\'' +
                ", dutyDate=" + dutyDate +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", dutyDateArray=" + Arrays.toString(dutyDateArray) +
                '}';
    }
}
