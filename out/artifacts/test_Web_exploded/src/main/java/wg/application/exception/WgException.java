package wg.application.exception;


/********************************************************
 * @Package wg.application.exception
 * @author wg
 * @date 2020/6/15 15:56
 * @version
 * @Copyright
 ********************************************************/
public class WgException extends RuntimeException {

    public WgException(String message) {
        super(message);
    }

    public WgException() {
    }

}
