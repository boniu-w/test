package wg.application.enumeration;

public enum NacosConstants {

    NAMESPACE("namespace") , USERNAME("username"), PASSWORD("password"),
    SERVERADDR("serverAddr");

    private final String value;

    NacosConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
