package cn.hdj.common.enums;


import lombok.Getter;

/**
 * @Description:
 * @Author huangjiajian
 */
@Getter
public enum ResponseCodeEnum {

    //响应成功
    SUCCESS(200, "操作成功"),

    UNKNOWN(500, "系统内部错误，请联系管理员"),
    PATH_NOT_FOUND(404, "路径不存在，请检查路径"),
    NO_AUTH(403, "抱歉，您没有访问权限！"),


    // 系统业务状态码： 从600 开始
    LOGIN_FAIL(601, "登录失败"),
    CAPTCHA_WRONG(602, "验证码错误"),
    USERNAME_OR_PASSWORD_WRONG(603, "用户名或密码错误！"),
    LOCK_ACCOUNT(604, "账户被锁定或禁用,请稍后再试！");

    private Integer code;
    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
