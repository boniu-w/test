package wg.application.vo;

public class ResponseMsg {
    private Integer code;
    private String msg;
    private Object obj;

    public ResponseMsg() {
    }

    public ResponseMsg(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }
}
