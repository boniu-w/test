package wg.application.enumeration;

public enum OrgEnum {

    UGA("乌干达"), IRAQ("伊拉克"), NA("北美"), UK("英国");

    private final String cnName;

    OrgEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }
}
