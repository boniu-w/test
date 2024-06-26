package wg.application.exception;


import wg.application.util.MessageUtils;

import java.text.MessageFormat;

/********************************************************
 * @Package wg.application.exception
 * @author wg
 * @date 2020/6/15 15:56
 * @version
 * @Copyright
 ********************************************************/
public class WgException extends RuntimeException {
    private int code;
    private String msg;

    public WgException() {
    }

    /**
     * @author: wg
     * @description: {0} 形式占位符 使用: MessageFormat.format(message, args);
     * %s 形式占位符 使用: String.format(message, args);
     * @params:
     * @return:
     * @createTime: 10:59  2024/5/21
     * @updateTime: 10:59  2024/5/21
     */
    public WgException(String message, Object... args) {
        super(MessageFormat.format(message, args));
        // super(String.format(message, args));
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = message;
    }

    public WgException(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public WgException(int code, String... params) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public WgException(int code, Throwable e) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public WgException(int code, Throwable e, String... params) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public WgException(String msg) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public WgException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public WgException(String msg, int value) {
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
