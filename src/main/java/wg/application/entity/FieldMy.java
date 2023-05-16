package wg.application.entity;


/****************************************************
 * @Package wg.application.entity
 * @author wg
 * @date 2021/4/30 13:29
 * @decription
 * @version
 * @Copyright
 ****************************************************/
public class FieldMy {

    private String field;

    private String fieldType;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public FieldMy(String field, String fieldType) {
        this.field = field;
        this.fieldType = fieldType;
    }
}
