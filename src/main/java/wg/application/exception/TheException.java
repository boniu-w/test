package wg.application.exception;


import wg.application.util.MessageUtils;

/********************************************************
 * @Package wg.application.exception
 * @author wg
 * @date 2020/6/15 15:56
 * @version
 * @Copyright
 ********************************************************/
public class TheException extends RuntimeException {
    private int code;
    private String msg;

    public TheException() {
    }

    public TheException(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public TheException(int code, String... params) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public TheException(int code, Throwable e) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public TheException(int code, Throwable e, String... params) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public TheException(String msg) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public TheException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public TheException(String msg, int value) {
        super(msg);
        this.msg = msg;
        this.code = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
