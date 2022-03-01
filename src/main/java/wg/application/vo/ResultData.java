//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package wg.application.vo;

import java.util.HashMap;
import org.springframework.http.HttpStatus;

public class ResultData extends HashMap<String, Object> {
    public static final String CODE_KEY = "code";
    public static final String DATA_KEY = "data";
    public static final String MSG_KEY = "msg";
    public static final String RESULT_KEY = "result";

    public ResultData() {
    }

    public static ResultData build() {
        return new ResultData();
    }

    public ResultData code(HttpStatus code) {
        return this.add("result", code == HttpStatus.OK).add("code", code.value());
    }

    public ResultData msg(String msg) {
        return this.add("msg", msg);
    }

    public ResultData data(Object data) {
        return this.add("data", data);
    }

    public ResultData success() {
        return this.code(HttpStatus.OK);
    }

    public ResultData success(Object data) {
        return this.success().data(data);
    }

    public ResultData success(String msg, Object data) {
        return this.success().msg(msg).data(data);
    }

    public ResultData error() {
        return this.code(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResultData error(String msg) {
        return this.error().msg(msg);
    }

    public ResultData add(String key, Object value) {
        this.put(key, value);
        return this;
    }
}
