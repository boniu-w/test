package wg.application.enumeration;

import java.io.Serializable;
import java.util.Arrays;

/************************************************************************
 * author wg
 * description TheGrade 
 * createTime 9:33 2024/9/23
 * updateTime 9:33 2024/9/23
 ************************************************************************/
public enum ThresholdGrade implements Serializable {
    NO(-1, "无风险", ""),
    LOW(0, "低",""),
    MIDDLE(1, "中",""),
    HIGH(2, "高",""),
    YES(2, "有风险","");


    private final Integer num;
    private final String des;
    private String advice;

    ThresholdGrade(Integer num, String des, String advice) {
        this.num = num;
        this.des = des;
        this.advice = advice;
    }

    public Integer getNum() {
        return num;
    }

    public String getDes() {
        return des;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    // 将枚举对象转换为 JSON 对象
    public ThresholdGradeDto toDto() {
        return new ThresholdGradeDto(num, des, advice);
    }

    // 获取所有枚举常量的 DTO 列表
    public static ThresholdGradeDto[] toDtoArray() {
        return Arrays.stream(values())
                .map(ThresholdGrade::toDto)
                .toArray(ThresholdGradeDto[]::new);
    }

    @Override
    public String toString() {
        return "ThresholdGrade{" +
                "num=" + num +
                ", des='" + des + '\'' +
                ", advice='" + advice + '\'' +
                '}';
    }
}
