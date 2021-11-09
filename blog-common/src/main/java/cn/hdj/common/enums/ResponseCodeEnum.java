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
    ERROR(400, "操作失败"),
    PATH_NOT_FOUND(404, "路径不存在，请检查路径"),
    NO_AUTH(401, "抱歉，你还没有认证！"),
    FORBIDDEN(403, "抱歉，你没有访问权限！"),


    // 系统业务状态码： 从600 开始
    LOGIN_FAIL(601, "登录失败"),
    CAPTCHA_WRONG(602, "验证码错误"),
    USERNAME_OR_PASSWORD_WRONG(603, "用户名或密码错误！"),
    LOCK_ACCOUNT(604, "账户被锁定或禁用,请稍后再试！"),
    ACCOUNT_NOT_EXIST(605, "账户不存在！"),
    RECORD_REPEAT(606, "数据重复！"),
    PARAM_INVALID(607, "参数非法异常！"),
    ACCOUNT_INVALID(608, "账号非法！"),
    TOKEN_INVALID(609, "Token非法！"),

    ;

    private Integer code;
    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
