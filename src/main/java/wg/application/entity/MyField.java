package wg.application.entity;

import lombok.Data;

/****************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2021/4/30 13:29
 * @decription
 * @version
 * @Copyright
 ****************************************************/
@Data
public class MyField {

    private String field;
    private String fieldType;

    public MyField(String field, String fieldType) {
        this.field = field;
        this.fieldType = fieldType;
    }
}
