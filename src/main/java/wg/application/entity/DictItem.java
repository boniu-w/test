package wg.application.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class DictItem {
    private static final long serialVersionUID = 1L;

    private String id;
    private String dictId;
    private String dictType;
    @JsonProperty("value")
    private String value;
    private @NotBlank String cnLabel;
    private @NotBlank String enLabel;
    private String cnDescription;
    private String enDescription;
    private Integer sortOrder;
    private String createBy;
    private String updateBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;

    private String delFlag;
    private String parentId;

    public DictItem() {
    }
}
