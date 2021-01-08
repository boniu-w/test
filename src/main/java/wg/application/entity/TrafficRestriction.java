package wg.application.entity;


public class TrafficRestriction {

    public Integer id;
    public String mondayRestrictedNum;
    public String thursdayRestrictedNum;
    public String wednesdayRestrictedNum;
    public String tuesdayRestrictedNum;
    public String fridayRestrictedNum;
    public String saturdayRestrictedNum;
    public String sundayRestrictedNum;
    public String trafficRestrictionDate;

    public String createTime;

    public String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMondayRestrictedNum() {
        return mondayRestrictedNum;
    }

    public void setMondayRestrictedNum(String mondayRestrictedNum) {
        this.mondayRestrictedNum = mondayRestrictedNum;
    }

    public String getThursdayRestrictedNum() {
        return thursdayRestrictedNum;
    }

    public void setThursdayRestrictedNum(String thursdayRestrictedNum) {
        this.thursdayRestrictedNum = thursdayRestrictedNum;
    }

    public String getWednesdayRestrictedNum() {
        return wednesdayRestrictedNum;
    }

    public void setWednesdayRestrictedNum(String wednesdayRestrictedNum) {
        this.wednesdayRestrictedNum = wednesdayRestrictedNum;
    }

    public String getTuesdayRestrictedNum() {
        return tuesdayRestrictedNum;
    }

    public void setTuesdayRestrictedNum(String tuesdayRestrictedNum) {
        this.tuesdayRestrictedNum = tuesdayRestrictedNum;
    }

    public String getFridayRestrictedNum() {
        return fridayRestrictedNum;
    }

    public void setFridayRestrictedNum(String fridayRestrictedNum) {
        this.fridayRestrictedNum = fridayRestrictedNum;
    }

    public String getSaturdayRestrictedNum() {
        return saturdayRestrictedNum;
    }

    public void setSaturdayRestrictedNum(String saturdayRestrictedNum) {
        this.saturdayRestrictedNum = saturdayRestrictedNum;
    }

    public String getSundayRestrictedNum() {
        return sundayRestrictedNum;
    }

    public void setSundayRestrictedNum(String sundayRestrictedNum) {
        this.sundayRestrictedNum = sundayRestrictedNum;
    }

    public String getTrafficRestrictionDate() {
        return trafficRestrictionDate;
    }

    public void setTrafficRestrictionDate(String trafficRestrictionDate) {
        this.trafficRestrictionDate = trafficRestrictionDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
