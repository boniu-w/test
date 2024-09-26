package wg.application.enumeration;

/************************************************************************
 * author wg
 * description ThresholdGradeDto 
 * createTime 11:13 2024/9/26
 * updateTime 11:13 2024/9/26
 ************************************************************************/
public class ThresholdGradeDto {
    private Integer num;
    private String des;
    private String advice;

    public ThresholdGradeDto(Integer num, String des, String advice) {
        this.num = num;
        this.des = des;
        this.advice = advice;
    }

    // Getters and Setters
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
