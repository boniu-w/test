package wg.application.enumeration;

public enum CodeEnum {
    SUCCESS(200,"success"),FAIL(404, "fail");

    private final int code;
    private final String description;

    CodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
