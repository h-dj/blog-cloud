package cn.hdj.common.exception;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/29 下午9:58
 */
public class BaseException extends RuntimeException {
    /**
     * 业务状态码
     */
    private Integer code;

    public BaseException(String message, Integer code) {
        super(message, null, false, true);
        this.code = code;
    }

    public BaseException(String message, Integer code,boolean writableStackTrace) {
        super(message, null, false, writableStackTrace);
        this.code = code;
    }

    public BaseException(String message, Integer code, Throwable throwable) {
        super(message, throwable, false, true);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
