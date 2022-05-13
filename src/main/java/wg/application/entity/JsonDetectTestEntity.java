package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/************************************************************************
 * @author: wg
 * @description: 默认情况下，Jackson 只使用 public 的字段进行序列化和反序列化。没有 public 字段时，会使用 public 的 getters/setters。
 * 可以通过 @JsonAutoDetect 自定义这种行为，指定字段、方法的可见性规则。
 * @createTime: 15:50 2022/5/12
 * @updateTime: 15:50 2022/5/12
 ************************************************************************/
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JsonDetectTestEntity {
    private String id;
    private String name;

    public JsonDetectTestEntity() {
    }

    public JsonDetectTestEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "JsonDetectTestEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
